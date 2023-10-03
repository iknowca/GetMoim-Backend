package com.example.demo.moim.entity;

import com.example.demo.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MoimPaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Moim moim;

    private Long totalPrice;
    private Long amountInstallment;
    private Integer numInstallments;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentInfo")
    private List<Payment> paymentList;
}
