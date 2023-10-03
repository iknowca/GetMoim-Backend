package com.example.demo.board.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@DiscriminatorValue(value="notice")
@AllArgsConstructor
@Entity
public class NoticeBoard extends Board{
}
