package com.example.demo.payment.repository;

import com.example.demo.moim.entity.Moim;
import com.example.demo.moim.entity.Participant;
import com.example.demo.payment.entity.Installment;
import com.example.demo.payment.entity.Payment;
import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from Payment p where p.paymentInfo.moim.id = :moimId")
    Payment findByMoimId(Long moimId);

    @Query("select p from Payment p where p.customerUid = :impUid")
    Payment findByImpUid(String impUid);

    @Query("select p from Payment p " +
            "join fetch p.installments " +
            "where p.participant =:participant and p.participant.moimParticipants.moim =:moim")
    Payment findByParticipantAndMoim(Participant participant, Moim moim);

    @Query("select p from Payment p where p.paymentInfo.moim = :moim")
    List<Payment> findAllByMoim(Moim moim);
    @Query("select p from Payment p  join fetch p.installments join fetch p.participant.moimParticipants.moim " +
            "join fetch p.participant.moimParticipants " +
            "join fetch p.participant " +
            "where p.participant.user=:user")
    List<Payment> findByUser(User user);
}
