package com.example.demo.moim.service;

import com.example.demo.moim.controller.form.dto.MoimDto;
import com.example.demo.moim.controller.form.dto.MoimOptionDto;
import com.example.demo.moim.entity.Moim;
import com.example.demo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MoimService {
    ResponseEntity<Map<String, Object>> createMoim(MoimDto moimDto);
    ResponseEntity<MoimDto> requestMoim(Long id);
    ResponseEntity<Map<String, Object>> joinMoim(Long id);

    ResponseEntity<List<MoimDto>> getRecentMoimList(Integer page, Integer size);

    ResponseEntity<Map<String, Object>> getJoinable(Long id);

    ResponseEntity<Map<String, Object>> withdrawMoim(Long moimId);

    ResponseEntity<Page<MoimDto>> getAdvanceSerchedList(Integer page, Integer size, String country, String city, String departureAirport, Integer[] rangeTotalPrice, Integer[] rangeNumOfInstallment, Integer[] rangeInstallment, LocalDateTime[] travelDates);

    ResponseEntity<Page<MoimDto>> getMyMoimList(Integer page, Integer size);

    ResponseEntity<Page<MoimDto>> getUserMoimList(Long userId, Integer page, Integer size);

    ResponseEntity<Page<MoimDto>> getCityMoimList(String city, Integer page, Integer size);

    ResponseEntity<List<String>> getKeyword();

    ResponseEntity<Page<MoimDto>> getPriceMoimList(Integer min, Integer max, Integer page, Integer size);

    ResponseEntity<Page<MoimDto>> getOptionMoimList(String option, Integer page, Integer size);

    void moimUpdate();

    ResponseEntity<List<MoimOptionDto>> getMoimOptionList(Long moimId);
}
