package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.MoimDto;
import com.example.demo.moim.entity.Moim;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MoimMapper extends GenericMapper<MoimDto, Moim> {
}
