package com.example.demo.util.mapStruct.user;

import com.example.demo.user.controller.form.ProfileDto;
import com.example.demo.user.entity.Profile;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper extends GenericMapper<ProfileDto, Profile> {
}
