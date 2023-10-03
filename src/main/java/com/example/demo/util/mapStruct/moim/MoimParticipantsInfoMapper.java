package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.MoimParticipantsInfoDto;
import com.example.demo.moim.entity.MoimParticipantsInfo;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoimParticipantsInfoMapper extends GenericMapper<MoimParticipantsInfoDto, MoimParticipantsInfo> {
}
