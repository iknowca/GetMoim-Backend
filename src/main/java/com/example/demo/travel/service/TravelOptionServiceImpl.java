package com.example.demo.travel.service;

import com.example.demo.travel.controller.dto.TravelDto;
import com.example.demo.travel.controller.dto.TravelOptionDto;
import com.example.demo.travel.entity.Airport;
import com.example.demo.travel.entity.Travel;
import com.example.demo.travel.entity.TravelOption;
import com.example.demo.travel.entity.TravelOptionCategory;
import com.example.demo.travel.repository.TravelOptionRepository;
import com.example.demo.travel.repository.TravelRepository;
import com.example.demo.util.mapStruct.TravelMapper;
import com.example.demo.util.mapStruct.TravelOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelOptionServiceImpl implements TravelOptionService {
    final TravelOptionRepository travelOptionRepository;
    final TravelOptionMapper travelOptionMapper;
    final TravelMapper travelMapper;
    final TravelRepository travelRepository;

    @Override
    public ResponseEntity<List<TravelOptionDto>> getOptions(String country, String city, String airport) {
        List<TravelOption> travelOptionList = travelOptionRepository.findOptionsByCountryAndCityAndAirport(country, city, Airport.valueOf(airport));
        List<TravelOptionDto> responseList = travelOptionList.stream().map((to) -> travelOptionMapper.toDto(to)
        ).toList();
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<List<TravelOptionCategory>> getOptionCategory() {
        return ResponseEntity.ok(List.of(TravelOptionCategory.values()));
    }

    @Override
    public ResponseEntity<TravelDto> saveOption(TravelDto travelDto) {
        Travel travel = travelMapper.toEntity(travelDto);
        List<TravelOption> travelOptionList = travelDto.getTravelOptionDtos().stream()
                .map(toDto -> travelOptionMapper.toEntity(toDto)).toList();

        travelOptionList.stream().forEach(to->to.setTravel(travel));
        travel.setTravelOptions(travelOptionList);
        Travel savedTravel = travelRepository.save(travel);

        TravelDto responseDto = travelMapper.toDto(savedTravel);
        responseDto.setTravelOptionDtos(savedTravel.getTravelOptions().stream().map(to->travelOptionMapper.toDto(to)).toList());
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<TravelDto> deleteOption(Long optionId) {
        TravelOption travelOption = travelOptionRepository.findById(optionId).get();
        Travel travel = travelRepository.findByTravelOption(travelOption);
        travelOptionRepository.delete(travelOption);
        TravelDto travelDto = travelMapper.toDto(travel);
        List<TravelOption> toList = travel.getTravelOptions();
        List<TravelOptionDto> toDtos = toList.stream().map(to->travelOptionMapper.toDto(to)).toList();
        travelDto.setTravelOptionDtos(toDtos);
        return ResponseEntity.ok(travelDto);
    }
}
