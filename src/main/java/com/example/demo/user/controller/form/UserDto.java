package com.example.demo.user.controller.form;

import com.example.demo.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private UserRole userRole;
    private ProfileDto profile;
}
