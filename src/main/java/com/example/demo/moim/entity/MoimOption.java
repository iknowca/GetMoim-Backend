package com.example.demo.moim.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoimOption{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long price;
    private String imgPath;
    private String info;
    private String category;
    @ManyToOne(fetch = FetchType.LAZY)
    private MoimDestination moimDestination;
}
