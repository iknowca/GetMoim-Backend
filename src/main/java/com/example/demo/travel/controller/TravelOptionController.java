package com.example.demo.travel.controller;

import com.example.demo.travel.controller.dto.TravelDto;
import com.example.demo.travel.controller.dto.TravelOptionDto;
import com.example.demo.travel.entity.TravelOptionCategory;
import com.example.demo.travel.service.TravelOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel/option")
@RequiredArgsConstructor
public class TravelOptionController {
    final TravelOptionService travelOptionService;
    @GetMapping(value = "/list", params = {"country", "city"})
    public ResponseEntity<List<TravelOptionDto>> getOptions(@RequestParam String country, @RequestParam String city, @RequestParam String airport) {
        return travelOptionService.getOptions(country, city, airport);
    }
    @GetMapping(value="/category/list")
    public ResponseEntity<List<TravelOptionCategory>> getOptionCategory() {
        return travelOptionService.getOptionCategory();
    }
    @PostMapping
    public ResponseEntity<TravelDto> saveOption(@RequestBody TravelDto travelDto) {
        return travelOptionService.saveOption(travelDto);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<TravelDto> deleteOption(@PathVariable Long optionId) {
        return travelOptionService.deleteOption(optionId);
    }
}
