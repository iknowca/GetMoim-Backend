package com.example.demo.moim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoimSceduler {
    final MoimService moimService;
    @Scheduled(cron = "0 0 1 * * *")
    public void printData() {
        moimService.moimUpdate();
    }
}
