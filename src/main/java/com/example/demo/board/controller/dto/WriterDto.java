package com.example.demo.board.controller.dto;

import com.example.demo.user.controller.form.ProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class WriterDto {
    private Long id;
    private String nickName;
    private ProfileDto profile;
}
