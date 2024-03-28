package edu.kh.project.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.project.member.model.service.MemberService;

@Controller  //bean 등록
public class MainController {
	
	@RequestMapping("/")  // " / "요청 매핑(method 가리지 않음 )
	public String mainPage() {
		return "common/main";
	}
	

}
