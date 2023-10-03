package com.example.demo.user.service;

import com.example.demo.user.controller.form.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity signOut(HttpHeaders headers, String refreshToken);

    ResponseEntity getUserInfo();
    Boolean checkNickname(String nickname);
    Boolean checkEmail(String email);

    ResponseEntity<Map<String, Object>> blockUser(Long userId);

    ResponseEntity<Map<String, Object>> cancelBlockUser(Long userId);

    ResponseEntity<Map<String, Object>> followUser(Long userId);

    ResponseEntity<Map<String, Object>> cancelFollowUser(Long userId);

    ResponseEntity<List<UserDto>> getFolloweeList();

    ResponseEntity<UserDto> getOtherUserInfo(Long userId);

    ResponseEntity<Map<String, Object>> setProfile(Long group, Long number);

    ResponseEntity<List<Long>> getFollowUsers();

    ResponseEntity<List<Long>> getBlockUsers();

    ResponseEntity<Map> modifyNickname(String nickname);

    ResponseEntity<List<UserDto>> getBLockUserList();
}
