package com.example.demo.moim.entity;

import com.example.demo.travel.entity.Airport;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoimDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String city;
    private String imgPath;
    @OneToOne(fetch = FetchType.LAZY)
    @Setter
    private Moim moim;
    @Enumerated(value = EnumType.STRING)
    private Airport departureAirport;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "moimDestination")
    private List<MoimOption> moimOptions;
}
