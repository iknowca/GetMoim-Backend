package com.example.demo.moim.controller.form.dto;

import com.example.demo.travel.entity.Airport;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoimDestinationDto {
    private Long id;
    private String country;
    private String city;
    @JsonProperty("imageKey")
    private String imgPath;
    private Airport departureAirport;
    @JsonProperty("optionsInfo")
    private List<MoimOptionDto> moimOptions;
}
