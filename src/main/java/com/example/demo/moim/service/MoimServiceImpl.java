package com.example.demo.moim.service;

import com.example.demo.moim.controller.form.dto.*;
import com.example.demo.moim.entity.*;
import com.example.demo.moim.repository.MoimRepository;
import com.example.demo.moim.repository.ParticipantRepository;
import com.example.demo.payment.service.PaymentService;
import com.example.demo.security.costomUser.CustomUserDetails;
import com.example.demo.travel.entity.Airport;
import com.example.demo.travel.repository.TravelRepository;
import com.example.demo.user.entity.User;
import com.example.demo.util.mapStruct.moim.*;
import com.example.demo.util.time.StateDateComparer;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoimServiceImpl implements MoimService {
    final MoimRepository moimRepository;
    final ParticipantRepository participantRepository;
    final TravelRepository travelRepository;
    final PaymentService paymentService;

    final MoimMapper moimMapper;
    final MoimContentsMapper moimContentsMapper;
    final StateMapper stateMapper;
    final MoimOptionMapper moimOptionMapper;
    final MoimDestinationMapper moimDestinationMapper;
    final MoimPaymentInfoMapper moimPaymentInfoMapper;
    final MoimParticipantsInfoMapper moimParticipantsInfoMapper;


    @Override
    public ResponseEntity<Map<String, Object>> createMoim(MoimDto moimDto) {
        Moim moim = new Moim();
        moimRepository.save(moim);

        MoimContents moimContents = moimContentsMapper.toEntity(moimDto.getContents());

        State state = stateMapper.toEntity(moimDto.getState());
        state.setState(StateType.TAXXING);
        state.setMoim(moim);

        List<MoimOption> options = moimDto.getDestination().getMoimOptions().stream().map(o -> {
            MoimOption moimOption = moimOptionMapper.toEntity(o);
            moimOption.setId(null);
            return moimOption;
        }).toList();

        MoimDestination moimDestination = moimDestinationMapper.toEntity(moimDto.getDestination());
        moimDestination.setMoimOptions(options);
        moimDestination.setMoim(moim);
        options.stream().forEach(o -> o.setMoimDestination(moimDestination));

        MoimPaymentInfo moimPaymentInfo = moimPaymentInfoMapper.toEntity(moimDto.getMoimPaymentInfo());
        moimPaymentInfo.setMoim(moim);
        moimPaymentInfo.setNumInstallments(state.getRunwayPeriod());
        moimPaymentInfo.setAmountInstallment(moimPaymentInfo.getTotalPrice() / moimPaymentInfo.getNumInstallments());

        MoimParticipantsInfo participantsInfo = moimParticipantsInfoMapper.toEntity(moimDto.getParticipantsInfo());
        participantsInfo.setMoim(moim);
        participantsInfo.setParticipants(List.of());

        moim.setState(state);
        moim.setDestination(moimDestination);
        moim.setMoimPaymentInfo(moimPaymentInfo);
        moim.setParticipantsInfo(participantsInfo);
        moim.setContents(moimContents);
        moim.setBoards(List.of());

        moimRepository.save(moim);

        joinMoim(moim.getId());

        return ResponseEntity.ok(Map.of("success", true, "moimId", moim.getId()));
    }

    @Override
    @Transactional
    public ResponseEntity<MoimDto> requestMoim(Long id) {
        Optional<Moim> maybeMoim = moimRepository.findById(id);
        if (maybeMoim.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            Moim moim = maybeMoim.get();

            List<MoimOption> moimOptions = moimRepository.findOptionListByMoim(moim);
            moim.getDestination().setMoimOptions(moimOptions);

            List<Participant> participants = moimRepository.findParticipantListByMoim(moim);
            moim.getParticipantsInfo().setParticipants(participants);

            MoimDto moimDto = moimMapper.toDto(moim);

            return ResponseEntity.ok(moimDto);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> joinMoim(Long id) {
        Optional<Moim> savedMoim = moimRepository.findById(id);
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (savedMoim.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            Moim moim = savedMoim.get();
            Participant participant = Participant.builder()
                    .user(user)
                    .moimParticipants(moim.getParticipantsInfo())
                    .build();

            MoimParticipantsInfo participantsInfo = moim.getParticipantsInfo();
            participantsInfo.getParticipants().add(participant);
            moimRepository.save(moim);
            return ResponseEntity.ok(Map.of("success", true, "moimId", moim.getId()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<MoimDto>> getRecentMoimList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        List<Moim> moimList = moimRepository.findRecentPageableMoim(pageable);
        List<MoimDto> responseList = moimList.stream()
                .map((m) -> {
                            return moimMapper.toDto(m);
                        }
                ).toList();
        return ResponseEntity.ok(responseList);
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, Object>> getJoinable(Long id) {
        Optional<Moim> maybeMoim = moimRepository.findById(id);
        if (maybeMoim.isEmpty()) {
            return ResponseEntity.noContent()
                    .build();
        }
        Moim moim = maybeMoim.get();
        Map<String, Object> responseMap;
        if (moim.getParticipantsInfo().getCurrentParticipantsNumber() < moim.getParticipantsInfo().getMaxNumOfUsers()) {
            responseMap = Map.of("joinable", true);
        } else {
            responseMap = Map.of("joinable", false);
        }
        return ResponseEntity.ok()
                .body(responseMap);
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, Object>> withdrawMoim(Long moimId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Optional<Moim> maybeMoim = moimRepository.findById(moimId);
        if (maybeMoim.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Moim moim = maybeMoim.get();
        Participant participant = participantRepository.findByUserAndMoimId(user, moimId);

        // payment withdraw procedure
        paymentService.withdraw(participant, moim);

        List<Participant> participantsList = moim.getParticipantsInfo().getParticipants();
        participantsList.remove(participant);
        moimRepository.save(moim);

        participantRepository.delete(participant);

        return ResponseEntity.ok(Map.of("success", true));
    }

    @Override
    public ResponseEntity<Page<MoimDto>> getAdvanceSerchedList(Integer page, Integer size, String country, String city, String departureAirport, Integer[] rangeTotalPrice, Integer[] rangeNumOfInstallment, Integer[] rangeInstallment, LocalDateTime[] travelDates) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        log.info(country);
        Specification<Moim> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (country != null) {
                predicates.add(criteriaBuilder.equal(root.get("destination").get("country"), country));
            }
            if (city != null) {
                predicates.add(criteriaBuilder.equal(root.get("destination").get("city"), city));
            }
            if (departureAirport != null) {
                predicates.add(criteriaBuilder.equal(root.get("destination").get("departureAirport"), Airport.valueOf(departureAirport)));
            }
            if (rangeTotalPrice != null) {
                predicates.add(criteriaBuilder.between(root.get("moimPaymentInfo").get("totalPrice"), rangeTotalPrice[0], rangeTotalPrice[1]));
            }
            if (rangeNumOfInstallment != null) {
                predicates.add(criteriaBuilder.between(root.get("moimPaymentInfo").get("numInstallments"), rangeNumOfInstallment[0], rangeNumOfInstallment[1]));
            }
            if (rangeTotalPrice != null) {
                predicates.add(criteriaBuilder.between(root.get("moimPaymentInfo").get("amountInstallment"), rangeInstallment[0], rangeInstallment[1]));
            }
            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
        Page<Moim> moimPage = moimRepository.findAll(spec, pageable);
        Page<MoimDto> moimDtoPage = new PageImpl<>(moimPage.stream().map(m -> moimMapper.toDto(m)).collect(Collectors.toList()));
        return ResponseEntity.ok(moimDtoPage);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<MoimDto>> getMyMoimList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Page<Moim> moimPage = moimRepository.findMyMoim(user, pageable);
        Page<MoimDto> moimDtoPage = new PageImpl<>(moimPage.stream().map(m -> moimMapper.toDto(m)).collect(Collectors.toList()));
        return ResponseEntity.ok(moimDtoPage);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<MoimDto>> getUserMoimList(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Moim> moimPage = moimRepository.findAllByUserId(userId, pageable);
        Page<MoimDto> moimDtoPage = new PageImpl<>(moimPage.stream().map(m -> moimMapper.toDto(m)).collect(Collectors.toList()));
        return ResponseEntity.ok(moimDtoPage);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<MoimDto>> getCityMoimList(String city, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Moim> moimPage = moimRepository.findAllByCity(city, pageable);
        Page<MoimDto> moimDtoPage = new PageImpl<>(moimPage.stream().map(m -> moimMapper.toDto(m)).collect(Collectors.toList()));
        return ResponseEntity.ok(moimDtoPage);
    }

    @Override
    public ResponseEntity<List<String>> getKeyword() {
        List<String> countryList = moimRepository.findAllCountyList();
        List<String> cityList = moimRepository.findAllCityList();
        countryList.addAll(cityList);
        return ResponseEntity.ok(countryList);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<MoimDto>> getPriceMoimList(Integer min, Integer max, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Moim> moimPage = moimRepository.findAllByPrice(max, min, pageable);
        Page<MoimDto> moimDtoPage = new PageImpl<>(moimPage.stream().map(m -> moimMapper.toDto(m)).collect(Collectors.toList()));
        return ResponseEntity.ok(moimDtoPage);
    }

    @Override
    @Transactional
    public ResponseEntity<Page<MoimDto>> getOptionMoimList(String option, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Moim> moimPage = moimRepository.findAllByOption(option.toUpperCase(), pageable);
        Page<MoimDto> moimDtoPage = new PageImpl<>(moimPage.stream().map(m -> moimMapper.toDto(m)).collect(Collectors.toList()));
        return ResponseEntity.ok(moimDtoPage);
    }

    @Override
    public void moimUpdate() {
        taxxingMoimUpdate();
        runWayMoimUpdate();
        takeoffMoimUpdate();
        flightMoimUpdates();
    }

    @Override
    public ResponseEntity<List<MoimOptionDto>> getMoimOptionList(Long moimId) {
        Moim moim = moimRepository.findOptionListByMoimId(moimId);
        List<MoimOption> optionList = moim.getDestination().getMoimOptions();
        List<MoimOptionDto> optionDtoList = optionList.stream().map(o -> moimOptionMapper.toDto(o)).toList();
        return ResponseEntity.ok(optionDtoList);
    }

    private void flightMoimUpdates() {
        LocalDateTime now = LocalDateTime.now();
        List<Moim> flightMoimList = moimRepository.findAllByState(StateType.FLIGHT);
        flightMoimList.stream().forEach(m -> {
            if (StateDateComparer.isSameDay(now, m.getState().getReturnDate())) {
                m.getState().setState(StateType.GROUND);
            }
        });
        moimRepository.saveAll(flightMoimList);
    }

    private void takeoffMoimUpdate() {
        LocalDateTime now = LocalDateTime.now();
        List<Moim> takeoffMoimList = moimRepository.findAllByState(StateType.TAKEOFF);
        takeoffMoimList.stream().forEach(m -> {
            if (StateDateComparer.isSameDay(now, m.getState().getDepartureDate())) {
                m.getState().setState(StateType.FLIGHT);
            }
        });
        moimRepository.saveAll(takeoffMoimList);
    }

    private void runWayMoimUpdate() {
        LocalDateTime now = LocalDateTime.now();
        List<Moim> runwayMoimList = moimRepository.findAllByState(StateType.RUNWAY);
        runwayMoimList.stream().forEach(m -> {
            if (StateDateComparer.isSameDay(now, m.getState().getTakeoffStartDate())) {
                m.getState().setState(StateType.TAKEOFF);
            }
        });
        moimRepository.saveAll(runwayMoimList);
    }

    private void taxxingMoimUpdate() {
        LocalDateTime now = LocalDateTime.now();
        List<Moim> taxxingMoimList = moimRepository.findAllByState(StateType.TAXXING);
        taxxingMoimList.stream().forEach(m -> {
            if (StateDateComparer.isSameDay(now, m.getState().getRunwayStartDate())) {
                if (m.getParticipantsInfo().getCurrentParticipantsNumber() > m.getParticipantsInfo().getMinNumOfUsers()) {
                    m.getState().setState(StateType.RUNWAY);
                }
            }
        });
        moimRepository.saveAll(taxxingMoimList);
    }
}
