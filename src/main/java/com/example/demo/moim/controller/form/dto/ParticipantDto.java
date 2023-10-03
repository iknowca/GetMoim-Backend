package com.example.demo.moim.controller.form.dto;

import com.example.demo.moim.entity.MoimParticipantsInfo;
import com.example.demo.payment.entity.Payment;
import com.example.demo.user.controller.form.UserDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ParticipantDto {
    private Long id;
    private UserDto user;
}
