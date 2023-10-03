package com.example.demo.security.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface JwtService {
    ResponseEntity<Map<String, Object>> refresh(String refreshToken);
    String generateAccessToken(String email);

    String generateRefreshToken(String email);
}
