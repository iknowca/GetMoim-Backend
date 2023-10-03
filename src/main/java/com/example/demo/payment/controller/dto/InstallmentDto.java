package com.example.demo.payment.controller.dto;

import com.example.demo.payment.entity.Payment;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class InstallmentDto {
    private Long id;

    private Long amount;
    private String merchantUid;
    private String impUid;
    private String receipt_url;
    private LocalDateTime createdDate;
    @Setter
    private Map<String, Object> additionalInfo;
}
