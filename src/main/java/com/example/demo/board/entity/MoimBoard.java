package com.example.demo.board.entity;

import com.example.demo.moim.entity.Moim;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue(value="moim")
public class MoimBoard extends Board{
    @ManyToOne(fetch = FetchType.LAZY)
    private Moim moim;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "board")
    private List<Comment> commentList;
}
