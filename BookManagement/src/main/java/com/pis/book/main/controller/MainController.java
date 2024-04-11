package com.pis.book.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pis.book.main.model.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {


	private final MainService service;
	
	@RequestMapping("/")
	
	public String mainPage() {
		return "common/main";
	}
	
	

}
