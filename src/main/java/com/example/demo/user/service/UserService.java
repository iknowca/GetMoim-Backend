package com.example.demo.user.service;

import com.example.demo.user.controller.form.UserSignUpForm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    boolean signUp(UserSignUpForm userSignUpForm);

    ResponseEntity signOut(HttpHeaders headers, String refreshToken);

    ResponseEntity getUserInfo();
    Boolean checkNickname(String nickname);
    Boolean checkEmail(String email);

    ResponseEntity<Map<String, Object>> blockUser(Long userId);

    ResponseEntity<Map<String, Object>> cancelBlockUser(Long userId);

    ResponseEntity<Map<String, Object>> followUser(Long userId);

    ResponseEntity<Map<String, Object>> cancelFollowUser(Long userId);
}
