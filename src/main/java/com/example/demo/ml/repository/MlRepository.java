package com.example.demo.ml.repository;

import com.example.demo.moim.entity.Moim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MlRepository extends JpaRepository<Moim, Long> {
    @Query("select m from Moim m join fetch m.moimPaymentInfo join fetch m.destination join fetch m.contents where m.state.state='TAXXING'")
    List<Moim> findAllTaxxingMoim();

    @Query("select m from Moim m join fetch m.moimPaymentInfo where m.state.state='TAXXING'")
    List<Moim> findAllTaxxingMoimWithPaymentInfo();

    @Query("select m from Moim m where m.id in :idList")
    List<Moim> findAllByIdList(@Param("idList") List<Long> idList);

}
