package com.example.demo.moim.controller;

import com.example.demo.moim.controller.form.dto.MoimDto;
import com.example.demo.moim.controller.form.dto.MoimOptionDto;
import com.example.demo.moim.service.MoimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moim")
@RequiredArgsConstructor
@Slf4j
public class MoimController {
    final MoimService moimService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMoim(@RequestBody MoimDto moimDto) {
        log.info("createMoim()");
        return moimService.createMoim(moimDto);
    }

    @GetMapping("/{moimId}")
    public ResponseEntity<MoimDto> getMoim(@PathVariable Long moimId) {
        return moimService.requestMoim(moimId);
    }

    @PostMapping("/{moimId}/user")
    public ResponseEntity<Map<String, Object>> JoinMoim(@PathVariable Long moimId) {
        return moimService.joinMoim(moimId);
    }

    @DeleteMapping("/{moimId}/user")
    public ResponseEntity<Map<String, Object>> withdrawMoim(@PathVariable Long moimId) {
        return moimService.withdrawMoim(moimId);
    }

    @GetMapping(value = "/list", params = {"page", "size"})
    public ResponseEntity<List<MoimDto>> getRecentMoimList(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("getRecentMoimList");
        return moimService.getRecentMoimList(page, size);
    }

    @GetMapping("/{id}/joinable")
    public ResponseEntity<Map<String, Object>> getJoinable(@PathVariable Long id) {
        log.info("getJoinable()");
        return moimService.getJoinable(id);
    }

    @GetMapping(value = "/list/advanced-search")
    public ResponseEntity<Page<MoimDto>> getAdvancedList(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String departureAirport,
            @RequestParam(required = false) Integer[] rangeTotalPrice,
            @RequestParam(required = false) Integer[] rangeNumOfInstallment,
            @RequestParam(required = false) Integer[] rangeInstallment,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime[] travelDates) {
        return moimService.getAdvanceSerchedList(page, size, country, city, departureAirport, rangeTotalPrice, rangeNumOfInstallment, rangeInstallment, travelDates);
    }

    @GetMapping(value="/user")
    public ResponseEntity<Page<MoimDto>> getMyMoimList(@RequestParam Integer page, @RequestParam Integer size) {
        return moimService.getMyMoimList(page, size);
    }
    @GetMapping("/list/user/{userId}")
    public ResponseEntity<Page<MoimDto>> getUserMoimLIst(@PathVariable Long userId, @RequestParam Integer page, @RequestParam Integer size) {
        return moimService.getUserMoimList(userId, page, size);
    }
    @GetMapping("/list/city/{cityName}")
    public ResponseEntity<Page<MoimDto>> getCityMoimList(@PathVariable String cityName, @RequestParam Integer page, @RequestParam Integer size) {
        return moimService.getCityMoimList(cityName, page, size);
    }
    @GetMapping("/keyword")
    public ResponseEntity<List<String>> getKeyword() {
        return moimService.getKeyword();
    }
    @GetMapping("/list/installment")
    public ResponseEntity<Page<MoimDto>> getPriceMoimList(@RequestParam Integer min, @RequestParam Integer max, @RequestParam Integer page, @RequestParam Integer size){
        return moimService.getPriceMoimList(min, max, page, size);
    }
    @GetMapping("/list/option")
    public ResponseEntity<Page<MoimDto>> getOptionMoimList(@RequestParam String option, @RequestParam Integer page, @RequestParam Integer size) {
        return moimService.getOptionMoimList(option, page, size);
    }
    @GetMapping("/{moimId}/destination/option/list")
    public ResponseEntity<List<MoimOptionDto>> getMoimOptionList(@PathVariable Long moimId) {
        return moimService.getMoimOptionList(moimId);
    }
}
