package com.example.demo.travel.service;

import com.example.demo.travel.controller.dto.TravelDto;
import com.example.demo.travel.controller.dto.TravelOptionDto;
import com.example.demo.travel.entity.Airport;
import com.example.demo.travel.entity.Travel;
import com.example.demo.travel.entity.TravelOption;
import com.example.demo.travel.repository.TravelOptionRepository;
import com.example.demo.travel.repository.TravelRepository;
import com.example.demo.util.mapStruct.TravelMapper;
import com.example.demo.util.mapStruct.TravelOptionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService{
    final TravelRepository travelRepository;
    final TravelOptionRepository travelOptionRepository;
    final TravelOptionMapper travelOptionMapper;
    final TravelMapper travelMapper;

    @Override
    @Transactional
    public ResponseEntity<TravelDto> createTravel(TravelDto reqForm) {
        Optional<Travel> maybeTravel = travelRepository.findByCityAndAirPort(reqForm.getCity(), Airport.valueOf(reqForm.getDepartureAirport()));
        Travel travel;
        if(maybeTravel.isEmpty()) {
            travel = Travel.builder()
                    .city(reqForm.getCity())
                    .country(reqForm.getCountry())
                    .departureAirport(Airport.valueOf(reqForm.getDepartureAirport()))
                    .imgPath(reqForm.getImgPath())
                    .build();
            List<TravelOptionDto> travelOptionDtoList = reqForm.getTravelOptionDtos();
            List<TravelOption> travelOptionList = travelOptionDtoList.stream().map((tod)->new TravelOption(tod, travel)).toList();
            travel.setTravelOptions(travelOptionList);
            travelRepository.save(travel);
        } else {
            travel = maybeTravel.get();
            if(reqForm.getImgPath()!=null) {
                travel.setImgPath(reqForm.getImgPath());
            }
            List<TravelOption> travelOptionList = travel.getTravelOptions();
            List<Long> oldTravelOptionIds = reqForm.getTravelOptionDtos().stream().filter((to)->to.getId()!=null).map(TravelOptionDto::getId).toList();
            List<TravelOption> newTravelOptions = reqForm.getTravelOptionDtos().stream().filter((to)->to.getId()==null).map(tod-> travelOptionMapper.toEntity(tod)).toList();
            List<TravelOption> removalTravelOptions = travel.getTravelOptions().stream().filter(to-> !oldTravelOptionIds.contains(to.getId())).toList();

            if(!removalTravelOptions.isEmpty()) {
                travel.getTravelOptions().removeAll(removalTravelOptions);
                travelRepository.save(travel);
            }

            travel.getTravelOptions().addAll(newTravelOptions);
            travelOptionRepository.saveAll(newTravelOptions);
            travelRepository.save(travel);
        }
        TravelDto travelDto = TravelDto.builder()
                .id(travel.getId())
                .city(travel.getCity())
                .country(travel.getCountry())
                .travelOptionDtos(travel.getTravelOptions().stream().map(to->TravelOptionDto.builder()
                        .optionName(to.getOptionName())
                        .optionPrice(to.getOptionPrice())
                        .isDeprecated(to.getIsDeprecated())
                        .id(to.getId())
                        .build()).toList())
                .build();
        return ResponseEntity.ok(travelDto);
    }

    @Override
    public ResponseEntity<List<String>> getCountries() {
        List<String> responseList = travelRepository.findCountries();
        return ResponseEntity.ok()
                .body(responseList);
    }

    @Override
    public ResponseEntity<List<String>> getCities(String country) {
        List<String> responseList = travelRepository.findCitiesByCountry(country);
        return ResponseEntity.ok()
                .body(responseList);
    }

    @Override
    public ResponseEntity<List<String>> getAirports() {
        List<String> responseList = Arrays.stream(Airport.values()).map(Enum::toString).toList();
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<List<String>> getAirports(String country, String city) {
        List<String> responseList = travelRepository.findAirportsByCountryAndCity(country, city);
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<TravelDto> getTravel(String country, String city, String departureAirport) {
        Optional<Travel> maybeTravel = travelRepository.findByCountryCityAirPort(country, city, Airport.valueOf(departureAirport));
        Travel travel;
        if(maybeTravel.isEmpty()) {
            travel = createTravel(country, city, departureAirport);
        } else {
            travel = maybeTravel.get();
        }
        TravelDto travelDto = travelMapper.toDto(travel);
        List<TravelOption> toList  = travelOptionRepository.findAllByTravel(travel);
        List<TravelOptionDto> toDtos = toList.stream().map(to->travelOptionMapper.toDto(to)).toList();
        travelDto.setTravelOptionDtos(toDtos);
        return ResponseEntity.ok(travelDto);
    }

    @Override
    public ResponseEntity<TravelDto> modifyImg(TravelDto travelDto) {
        Travel travel = travelMapper.toEntity(travelDto);
        travelRepository.save(travel);
        return ResponseEntity.ok(travelDto);
    }

    @Override
    public ResponseEntity<String> getImagePath(String country, String city, String airport) {
        Optional<Travel> maybeTravel = travelRepository.findByCountryCityAirPort(country, city, Airport.valueOf(airport));
        if(maybeTravel.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(maybeTravel.get().getImgPath());
    }

    @Override
    public ResponseEntity<Map<String, Object>> modifyTravel(TravelDto travelDto) {
        Optional<Travel> maybeTravel = travelRepository.findById(travelDto.getId());
        if (maybeTravel.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Travel savedTravel = maybeTravel.get();

        travelMapper.updateFromDto(travelDto, savedTravel);

        travelRepository.save(savedTravel);
        return ResponseEntity.ok(Map.of("success", true));
    }

    private Travel createTravel(String country, String city, String departureAirport) {
        Travel travel = Travel.builder()
                .country(country)
                .city(city)
                .departureAirport(Airport.valueOf(departureAirport))
                .build();
        travelRepository.save(travel);
        return travel;
    }

}
