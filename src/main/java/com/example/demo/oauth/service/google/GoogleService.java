package com.example.demo.oauth.service.google;

import org.springframework.http.ResponseEntity;

public interface GoogleService {
    String gooleLoginAddress();
    ResponseEntity getJwt(String code);
}
