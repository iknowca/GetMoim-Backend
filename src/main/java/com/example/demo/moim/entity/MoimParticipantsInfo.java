package com.example.demo.moim.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MoimParticipantsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer maxNumOfUsers;
    private Integer minNumOfUsers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "moimParticipants")
    private List<Participant> participants;
    @Formula("(select count(1) from participant p where p.moim_participants_id = id)")
    private Integer currentParticipantsNumber;
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Moim moim;

}
