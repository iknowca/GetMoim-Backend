package com.example.demo.travel.controller;

import com.example.demo.travel.controller.dto.TravelDto;
import com.example.demo.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class TravelController {
    final TravelService travelService;
    @PostMapping
    public ResponseEntity<TravelDto> createTravel(@RequestBody TravelDto reqForm) {
        return travelService.createTravel(reqForm);
    }
    @GetMapping
    public ResponseEntity<TravelDto> getTravel(@RequestParam String country,
                                               @RequestParam String city,
                                               @RequestParam String departureAirport) {
        return travelService.getTravel(country, city, departureAirport);
    }
    @GetMapping("/country/list")
    public ResponseEntity<List<String>> getCountries() {
        return travelService.getCountries();
    }
    @GetMapping(value = "/city/list", params = {"country"})
    public ResponseEntity<List<String>> getCities(@RequestParam String country) {
        return travelService.getCities(country);
    }

    @GetMapping("/airport/list")
    public ResponseEntity<List<String>> getAirports() {
        return travelService.getAirports();
    }
    @GetMapping(value = "/airport/list", params = {"country", "city"})
    public ResponseEntity<List<String>> getAirports(@RequestParam String country, @RequestParam String city) {
        return travelService.getAirports(country, city);
    }

    @GetMapping("/image")
    public ResponseEntity<String> getImagePath(@RequestParam String country, @RequestParam String city, @RequestParam String airport) {
        return travelService.getImagePath(country, city, airport);
    }
    @PutMapping("/{trvelId}")
    public ResponseEntity<Map<String, Object>> modifyTravel(@RequestBody TravelDto travelDto) {
        return travelService.modifyTravel(travelDto);
    }
}
