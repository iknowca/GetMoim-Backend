package com.example.demo.board.service;

import com.example.demo.board.controller.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BoardSerivce {
    ResponseEntity<Map<String, Object>> post(Long moimId, BoardDto req);

    ResponseEntity<List<BoardDto>> getMoimBoardList(Long moimId, Integer page, Integer size);

    ResponseEntity<BoardDto> getBoard(Long boardId);

    ResponseEntity<Map<String, Object>> modifyBoard(Long boardId, BoardDto req);

    ResponseEntity<Map<String, Object>> postBoard(String category, BoardDto req);

    ResponseEntity<List<BoardDto>> getBoardList(String category, Integer page, Integer size);

    ResponseEntity<Map<String, Object>> deleteBoard(Long boardId);

    ResponseEntity<List<BoardDto>> getEventBanners(String category);

    ResponseEntity<List<String>> getEventCategory();

    ResponseEntity<Page<BoardDto>> getReviewBoardPage(Integer page, Integer size);
}
