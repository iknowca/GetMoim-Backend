package com.example.demo.util.mapStruct.payment;

import com.example.demo.payment.controller.dto.InstallmentDto;
import com.example.demo.payment.entity.Installment;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstallmentMapper extends GenericMapper<InstallmentDto, Installment> {
}
