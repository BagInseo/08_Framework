package com.kh.test.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.test.board.model.dto.Board;
import com.kh.test.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService service;
	
	/** 제목으로 조회하기
	 * @return
	 */
	@PostMapping("selectBoard")
	public String selectBoard(
			@RequestParam("boardTitle") String boardTitle,
			Model model){
		
		List<Board> boardList = service.selectBoard(boardTitle);
		
		if(!boardList.isEmpty()) {
			model.addAttribute("boardList", boardList);
			return "searchSuccess";
		}
		
		
		return "searchFail";
	
	}

}
