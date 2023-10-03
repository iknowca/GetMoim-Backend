package com.example.demo.user.controller;

import com.example.demo.user.controller.form.UserDto;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final private UserService userService;
    @DeleteMapping("/sign-out")
    public ResponseEntity userSignout(@RequestHeader HttpHeaders headers, @CookieValue("refreshToken") String refreshToken) {
        log.info("signout()");
        return userService.signOut(headers, refreshToken);
    }
    @GetMapping
    public ResponseEntity getUserInfo() {
        return userService.getUserInfo();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getOtherUserInfo(@PathVariable Long userId) {
        return userService.getOtherUserInfo(userId);
    }

    @PostMapping("/{userId}/block")
    public ResponseEntity<Map<String, Object>> blockUser(@PathVariable Long userId){
        return userService.blockUser(userId);
    }
    @DeleteMapping("/{userId}/block")
    public ResponseEntity<Map<String, Object>> cancelBlockUser(@PathVariable Long userId) {
        return userService.cancelBlockUser(userId);
    }

    @PostMapping("/{userId}/follow")
    public ResponseEntity<Map<String, Object>> followUser(@PathVariable Long userId){
        return userService.followUser(userId);
    }
    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<Map<String, Object>> cancelFollowUser(@PathVariable Long userId) {
        return userService.cancelFollowUser(userId);
    }
    @GetMapping("/follow/info/list")
    public ResponseEntity<List<UserDto>> getFollowList() {
        return userService.getFolloweeList();
    }
    @GetMapping("/block/info/list")
    public ResponseEntity<List<UserDto>> getBlockList() {
        return userService.getBLockUserList();
    }
    @PostMapping("/profile")
    public ResponseEntity<Map<String, Object>> setProfile(@RequestParam Long group, @RequestParam Long number){
        return userService.setProfile(group, number);
    }
    @GetMapping("/follow/list")
    public ResponseEntity<List<Long>> getFollowUsers() {
        return userService.getFollowUsers();
    }
    @GetMapping("/block/list")
    public ResponseEntity<List<Long>> getBlockUsers() {
        return userService.getBlockUsers();
    }
    @PutMapping("/nickname")
    public ResponseEntity<Map> modifyNickname(@RequestBody Map requestMap) {
        return userService.modifyNickname((String)(requestMap.get("nickname")));
    }
}
