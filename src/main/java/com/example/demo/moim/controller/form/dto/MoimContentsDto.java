package com.example.demo.moim.controller.form.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MoimContentsDto {
    private Long id;
    private String title;
    private String content;

}
