package com.example.demo.travel.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TravelDto {
    private Long id;
    private String country;
    private String city;
    private String departureAirport;
    @JsonProperty(value = "imageKey")
    private String imgPath;

    @JsonProperty(value="additionalOptions")
    private List<TravelOptionDto> travelOptionDtos;
}
