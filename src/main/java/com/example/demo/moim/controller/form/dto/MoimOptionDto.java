package com.example.demo.moim.controller.form.dto;

import com.example.demo.moim.entity.MoimDestination;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MoimOptionDto {
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private Long price;
    @JsonProperty("imageKey")
    private String imgPath;
    private String info;
    private String category;
}
