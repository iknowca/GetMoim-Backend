package com.example.demo.security.controller;

import com.example.demo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
@Slf4j
public class JwtController {
    final JwtService jwtService;
    @GetMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(@CookieValue("refreshToken") String refreshToken) {
        return jwtService.refresh(refreshToken);
    }
}
