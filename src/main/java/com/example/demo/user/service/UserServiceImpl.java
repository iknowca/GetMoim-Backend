package com.example.demo.user.service;

import com.example.demo.security.costomUser.CustomUserDetails;
import com.example.demo.security.service.RedisService;
import com.example.demo.security.utils.JwtUtil;
import com.example.demo.user.entity.*;
import com.example.demo.user.controller.form.UserInfoResForm;
import com.example.demo.user.controller.form.UserSignUpForm;
import com.example.demo.user.repository.BlockUserRepository;
import com.example.demo.user.repository.RoleRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private BCryptPasswordEncoder passwordEncoder;
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private UserRoleRepository userRoleRepository;
    final private RedisService redisService;
    final private BlockUserRepository blockUserRepository;
    final private JwtUtil jwtUtil;
    @Override
    public boolean signUp(UserSignUpForm userSignUpForm) {
        final Optional<User> maybeUser = userRepository.findByEmail(userSignUpForm.getEmail());
        if (maybeUser.isPresent()) {
            log.debug("이미 등록된 회원이라 가입할 수 없습니다.");
            return false;
        }
        final User user = userSignUpForm.toUser(passwordEncoder.encode(userSignUpForm.getPassword()));
        userRepository.save(user);
        final Role role = roleRepository.findByRoleType(userSignUpForm.getRoleType()).get();
        final UserRole userRole = new UserRole(user, role);
        userRoleRepository.save(userRole);
        return true;
    }

    @Override
    public ResponseEntity signOut(HttpHeaders headers, String refreshToken) {
        String accessToken = Objects.requireNonNull(headers.get("Authorization")).toString().substring(7);

        redisService.deleteByKey(accessToken);
        redisService.deleteByKey(refreshToken);
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @Override
    public ResponseEntity getUserInfo() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        UserInfoResForm userInfoResForm = UserInfoResForm.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .roleType(user.getRole())
                .build();
        return ResponseEntity.ok()
                .body(userInfoResForm);
    }

    @Override
    public Boolean checkNickname(String nickname) {
        final Optional<User> presentNickname = userRepository.findByNickname(nickname);
        if (presentNickname.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkEmail(String email) {
        final Optional<User> presentEmail = userRepository.findByEmail(email);
        if (presentEmail.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<Map<String, Object>> blockUser(Long userId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Optional<User> maybeUser = userRepository.findById(userId);
        if(maybeUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        User blockedUser  = maybeUser.get();
        BlockUser blockUser = BlockUser.builder()
                .user(user)
                .blockedUser(blockedUser)
                .build();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> cancelBlockUser(Long userId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        BlockUser blockUser = blockUserRepository.findByUserAndBlockUserId(user, userId);
        blockUserRepository.delete(blockUser);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @Override
    public ResponseEntity<Map<String, Object>> followUser(Long userId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        FollowUser followUser = FollowUser.builder()
                .follower(user)
                .followee(userRepository.findById(userId).get())
                .build();
        return ResponseEntity.ok(Map.of("success", true));
    }
}
