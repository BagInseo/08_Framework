package com.kh.test.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.test.board.model.dto.Board;
import com.kh.test.board.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper mapper;
	
	@Override
	public List<Board> selectBoard(String boardTitle) {
		

		List<Board> boardList = mapper.selectBoard(boardTitle);
		return boardList ;
	}

}
