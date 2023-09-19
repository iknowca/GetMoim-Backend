package com.example.demo.user.controller;

import com.example.demo.user.controller.form.UserDto;
import com.example.demo.user.controller.form.UserSignUpForm;
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
    @PostMapping("/sign-up")
    public boolean userSignUp(@RequestBody UserSignUpForm userSignUpForm){
        return userService.signUp(userSignUpForm);
    }
    @DeleteMapping("/sign-out")
    public ResponseEntity userSignout(@RequestHeader HttpHeaders headers, @CookieValue("refreshToken") String refreshToken) {
        log.info("signout()");
        return userService.signOut(headers, refreshToken);
    }
    @GetMapping
    public ResponseEntity getUserInfo() {
        return userService.getUserInfo();
    }

    @GetMapping("/check-nickname/{nickname}")
    public Boolean getNickname(@PathVariable("nickname") String nickname){
        return userService.checkNickname(nickname);
    }
    @GetMapping("/check-email/{email}")
    public Boolean getEmail(@PathVariable("email")  String email){
        return userService.checkEmail(email);
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
    @GetMapping("/list/follow")
    public ResponseEntity<List<UserDto>> getFollowList() {
        return userService.getFolloweeList();
    }
}
