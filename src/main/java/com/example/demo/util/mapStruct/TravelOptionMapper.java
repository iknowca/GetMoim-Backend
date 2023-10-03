package com.example.demo.util.mapStruct;

import com.example.demo.travel.controller.dto.TravelOptionDto;
import com.example.demo.travel.entity.TravelOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TravelOptionMapper extends GenericMapper<TravelOptionDto, TravelOption>{
}
