package com.example.demo.user.service;

import com.example.demo.security.costomUser.CustomUserDetails;
import com.example.demo.security.service.RedisService;
import com.example.demo.security.utils.JwtUtil;
import com.example.demo.user.controller.form.UserDto;
import com.example.demo.user.entity.BlockUser;
import com.example.demo.user.entity.FollowUser;
import com.example.demo.user.entity.Profile;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.*;
import com.example.demo.util.mapStruct.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    final private FollowUserRepository followUserRepository;
    final private UserMapper userMapper;
    final private JwtUtil jwtUtil;

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
    @Transactional
    public ResponseEntity getUserInfo() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDto userDto = userMapper.toDto(user);

        return ResponseEntity.ok()
                .body(userDto);
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
        blockUserRepository.save(blockUser);
        return ResponseEntity.ok(Map.of("success", true));
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
        followUserRepository.save(followUser);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @Override
    public ResponseEntity<Map<String, Object>> cancelFollowUser(Long userId) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        FollowUser savedFollowUser = followUserRepository.findByFollowerAndFolloweeId(user, userId);
        followUserRepository.delete(savedFollowUser);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @Override
    @Transactional
    public ResponseEntity<List<UserDto>> getFolloweeList() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<FollowUser> followList = followUserRepository.findAllByFollower(user);
        List<UserDto> responseList = followList.stream()
                .map(f->f.getFollowee())
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> getOtherUserInfo(Long userId) {
        User user = userRepository.findById(userId).get();
        UserDto response = userMapper.toDto(user);
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, Object>> setProfile(Long group, Long number) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if(user.getProfile()==null) {
            user.setProfile(new Profile());
        }
        Profile profile = user.getProfile();

        profile.setGroupNumber(group);
        profile.setNumber(number);
        userRepository.save(user);

        UserDto userDto = userMapper.toDto(user);
        return ResponseEntity.ok(Map.of("success", true, "user", userDto));
    }

    @Override
    public ResponseEntity<List<Long>> getFollowUsers() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<Long> followUserIdList = followUserRepository.findByUserIdsByUser(user);
        return ResponseEntity.ok(followUserIdList);
    }

    @Override
    public ResponseEntity<List<Long>> getBlockUsers() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<Long> blockedUserIdList = blockUserRepository.findByUserIdsByUser(user);
        return ResponseEntity.ok(blockedUserIdList);
    }

    @Override
    public ResponseEntity<Map> modifyNickname(String nickname) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        user.setNickname(nickname);
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @Override
    public ResponseEntity<List<UserDto>> getBLockUserList() {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<BlockUser> blockList = blockUserRepository.findAllByUser(user);
        List<UserDto> responseList = blockList.stream()
                .map(b->b.getBlockedUser())
                .map(userMapper::toDto)
                .toList();
        return ResponseEntity.ok(responseList);
    }
}
