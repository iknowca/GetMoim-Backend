package com.example.demo.travel.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TravelOptionDto {
    private Long id;
    @JsonProperty("name")
    private String optionName;
    @JsonProperty("price")
    private Integer optionPrice;
    private Boolean isDeprecated;
    private String category;
    private String info;
    @JsonProperty("imageKey")
    private String imgPath;

}
