package com.example.demo.util.mapStruct;

import com.example.demo.travel.controller.dto.TravelDto;
import com.example.demo.travel.entity.Travel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TravelMapper extends GenericMapper<TravelDto, Travel> {

}
