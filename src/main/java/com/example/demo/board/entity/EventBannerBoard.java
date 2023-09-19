package com.example.demo.board.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue(value="eventBanner")
@NoArgsConstructor
@Getter
public class EventBannerBoard extends Board{
    private String mainImage;
}
