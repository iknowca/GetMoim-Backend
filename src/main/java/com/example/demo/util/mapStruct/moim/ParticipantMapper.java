package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.ParticipantDto;
import com.example.demo.moim.entity.Participant;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParticipantMapper extends GenericMapper<ParticipantDto, Participant> {
}
