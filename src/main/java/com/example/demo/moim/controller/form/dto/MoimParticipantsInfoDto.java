package com.example.demo.moim.controller.form.dto;

import com.example.demo.moim.entity.Moim;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class MoimParticipantsInfoDto {
    private Long id;
    @JsonProperty("maxParticipants")
    private Integer maxNumOfUsers;
    @JsonProperty("minParticipants")
    private Integer minNumOfUsers;
    private List<ParticipantDto> participants;
    private Integer currentParticipantsNumber;
}
