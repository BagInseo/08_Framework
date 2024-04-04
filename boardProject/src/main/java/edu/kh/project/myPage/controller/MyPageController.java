package edu.kh.project.myPage.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService service;
	
	
	/** 내 정보 조회/수정 화면으로 전환
	 * @param loginMember : 세션에 존재하는 loginMember를 얻어와 매개 변수에 대입
	 * @param model : 데이터 전달용 객체 (기본 request)
	 * @return myPage/myPage-info.html 요청 위임
	 */
	@GetMapping("info") //   /myPage/info (GET)
	public String info(@SessionAttribute("loginMember") Member loginMember,
			Model model){
		
		// 주소만 꺼내옴
		String memberAddress = loginMember.getMemberAddress();
		
		//주소가 있을 경우에만 동작
		if(memberAddress!=null) {
			//구분자 ^^^를 기준으로 
			//memberAddress 값을 쪼개어 String[]로 반환
			String[] arr = memberAddress.split("\\^\\^\\^");
			model.addAttribute("postcode",arr[0]);
			model.addAttribute("address",arr[1]);
			model.addAttribute("detailAddress",arr[2]);
		}
		
		// /templates/myPage/myPage-info.html로 forward
		return "myPage/myPage-info";
	}
	
	
	/** 프로필 이미지 변경 화면 이동
	 * @return
	 */
	@GetMapping("profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	
	/** 비밀번호 변경 화면 이동
	 * @return "myPage/myPage-changePw"
	 */
	@GetMapping("changePw")
	public String changePw() {
		return "myPage/myPage-changePw";	
	}
	
	
	@PostMapping("changePw")
	public String changePw(
			@RequestParam("currentPw") String currentPw,
			@RequestParam("newPw") String newPw,
			@RequestParam("newPwConfirm") String newPwConfirm,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) {
		
		int memberNo= loginMember.getMemberNo();
		int result=service.changePw(newPw,currentPw,newPwConfirm,memberNo);
		
		
		String message=null;
		String path=null;
		
		if(result > 0) {// 변경 성공 시
			 path="/myPage/info";
			 message="비밀번호가 변경되었습니다.";
		 
		 
			}else {//변경 실패
				path="/myPage/changePw";
				message="현재 비밀번호가 일치하지 않습니다.";

		}
		
		 ra.addFlashAttribute("message",message);
		 return "redirect:" + path;
		
	}
	
	
	/** 회원 탈퇴 화면 이동
	 * @return
	 */
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	
	/** 회원 탈퇴
	 * @param memberPw
	 * @param loginMember
	 * @param status
	 * @param ra
	 * @return
	 */
	//@SessionAttribute : Model에 세팅된 값 중 key가 일치하는 값을 
	//request -> session으로 변경
	
	//SessionStatus :
	//- SessionAttribute를 이용해서 올라간 데이터의 상태를 관리하는 객체
	// 해당 컨트롤러에 SessionAttribute({"key1", "key2"})가 작성되어 있는 경우
	// ()내 key1, key2의 상태를 관리
	@PostMapping("secession")
	public String secession(
			@RequestParam("memberPw") String memberPw, //입력된 비밀번호 얻어오기
			@SessionAttribute("loginMember") Member loginMember, //로그인한 회원 정보 얻어오기
			SessionStatus status, 
			RedirectAttributes ra) {
		
		int memberNo = loginMember.getMemberNo();
		int result = service.changeSecession(memberPw, memberNo);
		
		
		String message = null;
		String path= null;
		
		if(result>0) {
			message = "탈퇴 완료";
			status.setComplete(); //세션 만료시키기
			path="/";
		
		}else {
			message="탈퇴 실패";
			path="/myPage/secession";
		}
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path; 
	}
	
	/** 회원 정보 수정
	 * @param inputMember : 제출된 회원 닉네임, 전화번호, 주소
	 * @param loginMember : 로그인한 회원 정보(회원 번호 사용할 예정)
	 * @param memberAddress : 주소만 따로 받은 String[]
	 * @param ra : 리다이렉트 시 request scope로 데이터 전달
	 * @return
	 */
	@PostMapping("info")
	public String updateInfo(
			Member inputMember,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra){
		
		//inuptMember에 로그인한 회원번호 추가 
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		//회원 정보 수정 서비스 호출
		int result = service.updateInfo(inputMember, memberAddress);
		
		String message = null;
		
		 if(result > 0) {
			 message="회원 정보 수정 성공";
			 //loginMember는 세션에 저장된 로그인된 회원 정보가 
			 // 저장된 객체를 참조하고 있다.
			 // -> loginMember를 수정하면 
			 // 	세션에 저장된 로그인된 회원 정보가 수정된다
			 
			 //== 세션 데이터가 수정됨
			 loginMember.setMemberNickname(inputMember.getMemberNickname());
			 loginMember.setMemberTel(inputMember.getMemberTel());
			 loginMember.setMemberAddress(inputMember.getMemberAddress());
		 }else {
			 message="회원 정보 수정 실패";
		 }
		
		return "redirect:info";
	}	
	
	
	
	@ResponseBody
	@PutMapping("resetPw")
	public int resetPw(@RequestBody int inputNo) {
		
		return 0;
	}
	
	
	//--------------------------------------------------------------------------
	/* 파일 업로드 테스트 */
	
	@GetMapping("fileTest")
	public String fileTest() {
		return "myPage/myPage-fileTest";
	
	}
	
	
	
	
	/* Spring에서 파일 업로드를 처리하는 방법
	 * 
	 * - enctype="multipart/form-data"로 클라이언트 요청을 받으면
	 *   (문자, 숫자, 파일 등이 섞여있는 요청)
	 *   
	 *   이를 MultipartResolver를 이용해서 
	 *   섞여있는 파라미터를 분리
	 *   
	 *   문자열, 숫자	-> String
	 *   파일 		->MultipartFile
	 * 
	 */
	
	// 파일 업로드 테스트 1
	/**
	 * @param uploadFile : 업로드한 파일 + 설정 내용
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("file/test1")
	public String fileUpload1(
			@RequestParam("uploadFile") MultipartFile uploadFile,
			RedirectAttributes ra
			) throws IllegalStateException, IOException {
		
		String path = service.fileUpload1(uploadFile);
		
		//파일이 저장되어 웹에서 접근할 수 있는 경로가 반환 되었을 때
		if(path!=null) {
			ra.addFlashAttribute("path",path);
		}
		
		return "redirect:/myPage/fileTest";
	}
}
