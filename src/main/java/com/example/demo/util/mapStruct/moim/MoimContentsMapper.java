package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.MoimContentsDto;
import com.example.demo.moim.entity.MoimContents;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoimContentsMapper extends GenericMapper<MoimContentsDto, MoimContents> {
}
