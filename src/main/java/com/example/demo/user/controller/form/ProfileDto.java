package com.example.demo.user.controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProfileDto {
    private Long groupNumber;
    private Long number;
}
