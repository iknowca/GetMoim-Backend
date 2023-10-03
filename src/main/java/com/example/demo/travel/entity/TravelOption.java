package com.example.demo.travel.entity;

import com.example.demo.travel.controller.dto.TravelOptionDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TravelOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionName;
    private Integer optionPrice;
    private String imgPath;
    private Boolean isDeprecated;
    @Enumerated(EnumType.STRING)
    private TravelOptionCategory category;
    private String info;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Travel travel;

    public TravelOption(TravelOptionDto reqForm, Travel travel) {
        this.optionName = reqForm.getOptionName();
        this.optionPrice = reqForm.getOptionPrice();
        this.travel = travel;
        this.isDeprecated = false;
    }
}
