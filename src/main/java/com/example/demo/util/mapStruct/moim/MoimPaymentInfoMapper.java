package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.MoimPaymentInfoDto;
import com.example.demo.moim.entity.MoimPaymentInfo;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoimPaymentInfoMapper extends GenericMapper<MoimPaymentInfoDto, MoimPaymentInfo> {
}
