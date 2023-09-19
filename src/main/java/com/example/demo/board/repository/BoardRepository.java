package com.example.demo.board.repository;

import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import com.example.demo.board.entity.EventBannerBoard;
import com.example.demo.board.entity.MoimBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository<T extends Board> extends JpaRepository<T, Long> {
    @Query("select mb from MoimBoard mb join fetch mb.writer join fetch mb.contents where mb.moim.id=:moimId")
    List<MoimBoard> findAllMoimBoardWithPageable(Long moimId, Pageable pageable);

    @Query("select b from Board b join fetch b.writer " +
            "join fetch b.contents " +
            "join fetch b.writer " +
            "where b.id=:boardId")
    Board findByMoimId(Long boardId);

    @Query("select qb from QnaBoard qb join fetch qb.writer join fetch qb.contents")
    List<MoimBoard> findAllQnaBoardListWithPageable(Pageable pageable);

    @Query("select b from Board b join fetch b.writer where b.category=:boardCategory")
    List<Board> findAllByCategoryAndPageable(BoardCategory boardCategory, Pageable pageable);

    @Query("select e from EventBannerBoard e")
    List<EventBannerBoard> find5EventBanner(Pageable pageable);
}
