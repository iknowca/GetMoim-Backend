package com.example.demo.util.mapStruct;

import com.example.demo.user.controller.form.UserDto;
import com.example.demo.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDto, User>{

}
