package com.example.demo.travel.service;

import com.example.demo.travel.controller.dto.TravelDto;
import com.example.demo.travel.controller.dto.TravelOptionDto;
import com.example.demo.travel.entity.TravelOptionCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TravelOptionService {
    ResponseEntity<List<TravelOptionDto>> getOptions(String country, String city, String airport);

    ResponseEntity<List<TravelOptionCategory>> getOptionCategory();

    ResponseEntity<TravelDto> saveOption(TravelDto travelDto);

    ResponseEntity<TravelDto> deleteOption(Long optionId);
}
