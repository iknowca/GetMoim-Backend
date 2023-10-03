package com.example.demo.moim.controller.form.dto;

import com.example.demo.moim.entity.Moim;
import com.example.demo.moim.entity.StateType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StateDto {
    private Long id;
    private StateType state;
    private LocalDateTime startDate;
    private LocalDateTime runwayStartDate;
    private LocalDateTime takeoffStartDate;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private Integer taxxingPeriod;
    private Integer runwayPeriod;
    private Integer takeoffPeriod;
}
