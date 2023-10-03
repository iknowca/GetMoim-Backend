package com.example.demo.util.mapStruct.moim;

import com.example.demo.moim.controller.form.dto.StateDto;
import com.example.demo.moim.entity.State;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateMapper extends GenericMapper<StateDto, State> {
}
