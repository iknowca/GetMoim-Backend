package com.example.demo.ml.service;

import com.example.demo.moim.controller.form.dto.MoimDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MlService {
    ResponseEntity<List<Map<String, Object>>> getAllTaxxingMoimList();

    ResponseEntity<List<Map<String, Object>>> getAllTaxxingMoimListWithPriceInfo();

    ResponseEntity<List<MoimDto>> getMoimByIdList(List<Long> idList);
}
