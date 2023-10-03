package com.example.demo.board.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BoardDto {
    private String title;
    private Long id;
    private String category;
    private BoardContentsDto contents;
    private Boolean isPublic;
    @Setter
    private Map<String, Object> additionalInfo;
    private WriterDto writer;
    private Boolean isDeleted;
    private LocalDateTime createdDate;
}
