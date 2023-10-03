package com.example.demo.ml.controller;

import com.example.demo.ml.service.MlService;
import com.example.demo.moim.controller.form.dto.MoimDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ml")
@RequiredArgsConstructor
public class MlController {
    final MlService mlService;
    @GetMapping("/moim/list")
    public ResponseEntity<List<Map<String, Object>>> getAllMoimList() {
        return mlService.getAllTaxxingMoimList();
    }
    @GetMapping("/moim/list/v2")
    public ResponseEntity<List<Map<String, Object>>> getTaxxingMoimList() {
        return mlService.getAllTaxxingMoimListWithPriceInfo();
    }
    @PostMapping("/moim/list/v2")
    public ResponseEntity<List<MoimDto>> getMoimByIdList(@RequestBody List<Long> token) {
        return mlService.getMoimByIdList(token);
    }
}
