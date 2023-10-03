package com.example.demo.board.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Getter
@DiscriminatorValue(value="review")
public class ReviewBoard extends Board{
    private String imageKey;
}
