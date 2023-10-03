package com.example.demo.payment.service;

import com.example.demo.moim.entity.Moim;
import com.example.demo.moim.entity.Participant;
import com.example.demo.payment.controller.dto.InstallmentDto;
import com.example.demo.payment.controller.dto.PaymentReqForm;
import com.example.demo.payment.controller.dto.WebHookToken;
import com.example.demo.payment.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    ResponseEntity<Map<String, Object>> getPaymentInfo(Long moimId);
    ResponseEntity<Map<String, Object>> firstPay(Long moimId, PaymentReqForm reqForm);
    ResponseEntity<Map<String, Object>> webHook(@RequestBody WebHookToken token);

    Boolean withdraw(Participant participant, Moim moim);


    List<Payment> findPaymentByMoim(Moim moim);

    ResponseEntity<List<InstallmentDto>> getInstallmentList();
}
