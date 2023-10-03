package com.example.demo.util.mapStruct.payment;

import com.example.demo.payment.controller.dto.PaymentDto;
import com.example.demo.payment.entity.Payment;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends GenericMapper<PaymentDto, Payment> {
}
