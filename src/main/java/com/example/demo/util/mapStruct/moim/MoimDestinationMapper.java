package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.MoimDestinationDto;
import com.example.demo.moim.entity.MoimDestination;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoimDestinationMapper extends GenericMapper<MoimDestinationDto, MoimDestination> {
    MoimDestinationDto toDto(MoimDestination e);
}
