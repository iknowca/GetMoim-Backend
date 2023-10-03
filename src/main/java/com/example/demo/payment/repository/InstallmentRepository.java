package com.example.demo.payment.repository;

import com.example.demo.payment.entity.Installment;
import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Long> {
    @Query("select i from Installment i where i.merchantUid=:merchantUid")
    Installment findByMerchantUid(String merchantUid);

    @Query("select i from Installment i join fetch i.payment " +
            "join fetch i.payment.paymentInfo " +
            "join fetch i.payment.paymentInfo.moim " +
            "where i.payment.participant.user=:user")
    List<Installment> findAllByUser(User user);
}
