package com.example.demo.ml.service;


import com.example.demo.ml.repository.MlRepository;
import com.example.demo.moim.controller.form.dto.MoimDto;
import com.example.demo.moim.entity.Moim;
import com.example.demo.util.mapStruct.moim.MoimMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MlServiceImpl implements MlService{
    final MlRepository mlRepository;
    final MoimMapper moimMapper;

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllTaxxingMoimList() {
        List<Moim> moimList = mlRepository.findAllTaxxingMoim();
        List<Map<String, Object>> responseMapList = moimList.stream().map(m->
                Map.of("id", (Object) m.getId(),
                        "destination", m.getDestination().getCity(),
                        "title", m.getContents().getTitle())
                ).toList();
        return ResponseEntity.ok(responseMapList);
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllTaxxingMoimListWithPriceInfo() {
        List<Moim> moimList = mlRepository.findAllTaxxingMoimWithPaymentInfo();
        List<Map<String, Object>> responseMapList = moimList.stream().map(m->
                Map.of("id", (Object) m.getId(),
                        "totalPrice", (Object) m.getMoimPaymentInfo().getTotalPrice(),
                        "numInstallment", (Object) m.getMoimPaymentInfo().getNumInstallments())
        ).toList();
        return ResponseEntity.ok(responseMapList);
    }

    @Override
    @Transactional
    public ResponseEntity<List<MoimDto>> getMoimByIdList(List<Long> idList) {
        List<Moim> moimList = mlRepository.findAllByIdList(idList);
        List<MoimDto> moimDtoList = moimList.stream().map(m->moimMapper.toDto(m)).toList();
        return ResponseEntity.ok(moimDtoList);
    }
}
