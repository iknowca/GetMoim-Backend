package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.MoimOptionDto;
import com.example.demo.moim.entity.MoimOption;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoimOptionMapper extends GenericMapper<MoimOptionDto, MoimOption> {
}
