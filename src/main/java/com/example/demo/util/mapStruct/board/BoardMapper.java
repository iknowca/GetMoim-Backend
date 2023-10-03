package com.example.demo.util.mapStruct.board;

import com.example.demo.board.controller.dto.BoardDto;
import com.example.demo.board.entity.Board;
import com.example.demo.moim.controller.form.dto.MoimContentsDto;
import com.example.demo.moim.entity.MoimContents;
import com.example.demo.util.mapStruct.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardMapper extends GenericMapper<BoardDto, Board> {
}