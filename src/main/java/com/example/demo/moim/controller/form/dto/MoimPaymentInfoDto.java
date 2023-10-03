package com.example.demo.moim.controller.form.dto;

import com.example.demo.moim.entity.MoimParticipantsInfo;
import com.example.demo.payment.controller.dto.PaymentDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MoimPaymentInfoDto {
    private Long id;

    private Long totalPrice;
    private Long amountInstallment;
    private Integer numInstallments;
    private List<PaymentDto> paymentList;
}
