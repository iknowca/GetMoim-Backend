package com.example.demo.board.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue(value="qna")
public class QnaBoard extends Board{
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "board")
    private List<Comment> comments;
}
