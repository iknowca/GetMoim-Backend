package com.example.demo.moim.controller.form.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoimDto {
    private Long id;
    private LocalDateTime createdDate;
    @JsonProperty("stateInfo")
    private StateDto state;
    @JsonProperty("destinationInfo")
    private MoimDestinationDto destination;
    @JsonProperty("paymentInfo")
    private MoimPaymentInfoDto moimPaymentInfo;
    @JsonProperty("participantsInfo")
    private MoimParticipantsInfoDto participantsInfo;
    @JsonProperty("basicInfo")
    private MoimContentsDto contents;
}
