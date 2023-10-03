package com.example.demo.board.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue(value="faq")
public class FaqBoard extends Board{
    private String image;
}
