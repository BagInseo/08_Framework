package edu.kh.demo.controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.demo.model.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

//Bean : 스프링이 만들고 관리하는 객체
@Controller  //요청/응답 제어 력할 명시 + Bean 등록
@RequestMapping("param")  //param으로 시작하는 요청을 현재 컨트롤러로 매핑 
@Slf4j  //log를 이용한 메세지 출력 시 사용 
public class parameterController {

	@GetMapping("main") ///parma/main Get방식 요청 매핑
	public String paramMain() {
		return "param/param-main";
	}
	
	/* 1. HttpServletRequest.getParameter("key") 이용 */
	//HttpServletRequest : 
	//- 요청 클라이언트 정보, 제출된 파라미터 등을 저장한 객체
	//- 클라이언트 요청 시 발생
	
	/*
	 * Spring의  controller 메서드 작성 시
	 * 매개 변수에  원하는 객체를 작성하면
	 * 존재하는 객체를 바인딩 또는 없으면 생성해서 바인딩
	 * 
	 * --> ArgumentResolver (전달 인자 해결사)
	 * 
	 */
	@PostMapping("test1")   ///parma/test1 POST방식 요청 매핑
	public String paramTest1( HttpServletRequest req) {
		String inputName = req.getParameter("inputName");
		String inputAddress = req.getParameter("inputAddress");
		int inputAge =Integer.parseInt(req.getParameter("inputAge"));
		
		
		//debug : 코드 오류 해결
		//-> 코드 오류는 없는데 정상 수행이 안될 때
		// -> 값이 잘못된 경우 -> 값 추적
		log.debug("inputName : " + inputName  );
		log.debug("inputAddress : " + inputAddress  );
		log.debug("inputAge : " + inputAge  );
		
		/* Spring에서 Redirect(재요청) 하는 방법
		 * 
		 *  -Controller 메서드 반환 값에 
		 *  "redirect : 요청주소"; 작성
		 * */
		
		return "redirect:/param/main";
	}
	
	//400 Bad Request
	//-파라미터 불충분
	@PostMapping("test2")
	public String paramTest2(
			@RequestParam("title") String title,
			@RequestParam("writer") String writer,
			@RequestParam("price") int price,
			@RequestParam(value="publisher",required=false, defaultValue = "교보문고") String publisher
			
			) {
		log.debug("title : " + title);
		log.debug("writer : " + writer);
		log.debug("price : " + price);
		log.debug("publisher : " + publisher);
		
		
		return "redirect:/param/main";
		
	}
	
	/*3. RequstParam 어노테이션 이용- 여러 개(복수, 다수) 파라미터 */
	//String []
	//List<자료형>
	//Map<String,Object>
	
	//defalutValue 속성은 사용 불간
	@PostMapping("test3")
	public String paramTest3(
			@RequestParam(value="color", required = false )String[] colorArr,
			@RequestParam(value="fruit", required = false)List<String>fruitList,
			
			//@RequestParam Map<String,Object>
			//->제출된 모든 파라미터가 Map에 저장된다
			
			//->문제점 : key(name속성값)이 중복되면 덮어쓰기가 된다
			//-> 같은 name속성 파라미터가 String[], Lisr<>로 저장 X
			@RequestParam Map<String, Object> paramMap
			) {
		
		log.debug("colorArr:"+Arrays.toString(colorArr));
		log.debug("fruitList:"+fruitList);
		log.debug("paramMap:"+paramMap);
		return "redirect:/param/main";
	}
	
	
	@PostMapping("test4")
	public String paramTest4(/* @ModelAttribute */ MemberDTO inputMember) {
																					//커맨드 객체
		//lombok 테스트
		MemberDTO mem = new MemberDTO();
		mem.getMemberAge(); //getter
		mem.setMemberAge(0); //setter
		//안만들었는데 호출 가능!!
		
		log.debug("inputMember : "+inputMember.toString());
		
		return "redirect:/param/main";
	}
	
	
}
