package com.example.demo.moim.repository;

import com.example.demo.moim.entity.Participant;
import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("select p from Participant p where p.user=:user and p.moimParticipants.moim.id=:moimId")
    Participant findByUserAndMoimId(User user, Long moimId);
}
