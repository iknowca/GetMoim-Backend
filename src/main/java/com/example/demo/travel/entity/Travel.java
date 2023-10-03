package com.example.demo.travel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String city;
    @Enumerated(EnumType.STRING)
    private Airport departureAirport;
    @Setter
    private String imgPath;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "travel")
    @Setter
    private List<TravelOption> travelOptions;
}
