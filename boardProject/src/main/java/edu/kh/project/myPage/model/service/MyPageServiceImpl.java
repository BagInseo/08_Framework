package edu.kh.project.myPage.model.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper;
	
	// @RequiredArgsConstructor 를 이용했을 때 자동 완성 되는 구문
//	@Autowired
//	public MyPageServiceImpl(MyPageMapper mapper) {
//		this.mapper = mapper;
//	}
	
	
	private final BCryptPasswordEncoder bcrypt;
	
	
	
	//회원 정보 수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {

		//입력된 주소가 있을 경우
		//memberAddress를 A^^^B^^^C 형태로 가공
		
		//주소 입력 X -> inputMember.getMemberAddress() ->", ,"
		if(inputMember.getMemberAddress().equals(",,")) {
			
			//주소에 null 대입
			inputMember.setMemberAddress(null);
		}else {
			////memberAddress를 A^^^B^^^C 형태로 가공
			String address= String.join("^^^", memberAddress);
			
			// 주소에 가공된 데이터를 대입
			inputMember.setMemberAddress(address);
		}
		
		//SQL 수행 후 결과 반환
		return mapper.updateInfo(inputMember);
	}
	
	

	@Override
	public int changePw(String newPw, String currentPw,String newPwConfirm, int memberNo) {
		//bcrypt 암호화된 비밀번호를 DB에서 조회(회원번호 필요)
		String encPw=mapper.selectPw(memberNo);
	
		
		//현재 비밀번호와 bcrypt 암호화된 비밀번호 비교
		//BcryptPasswordEncoder.matches(평문, 암호화된 비밀번호))
		
		if(bcrypt.matches(currentPw,encPw)&&newPw.equals(newPwConfirm)) {//- 같을 경우 -> 새 비밀번호를 암호화 진행
		
				String newEncPw=bcrypt.encode(newPw);
				
				// -> 새 비밀번호로 변경(UPDATE)하는 Mapper 호출
			    //회원 번호, 새 비밀번호 -> 하나로 묶음 (Member 또는 Map) 
			    //-> 결과 1 또는 0 반환
				Member inputMember = new Member();
				inputMember.setMemberPw(newEncPw);
				inputMember.setMemberNo(memberNo);
				
				return mapper.updatePw(inputMember);

		}
			return 0;
	}
	
	
	
	//회원 탈퇴
	@Override
	public int changeSecession(String memberPw, int memberNo) {

		// 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회
		String encPw = mapper.selectPw(memberNo);

		//입력된 비밀번호와 비교해서 같을 때 탈퇴 수행
		if(bcrypt.matches(memberPw,encPw)) {//입력된 비밀번호와 같은 경우
			
			return mapper.changeSecession(memberNo);
		}
		
		//아닐 때
		return 0;

	}
	
	
	
	//파일 업로드 테스트1
	@Override
	public String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException {
		
		//MultipartFile이 제공하는 메서드
		// - getSize() : 파일 크기
		// - isEmpty() : 업로드한 파일이 있을 경우 true
		// - getOriginalFileName() : 원본 파일 명
		// - transferTo(경로) : 메모리 또는 임시 저장 경로에 
		//						업로드된 파일을 원하는 경로에 전송(서버 어떤 폴더에 저장할지 지정)
		
		if(uploadFile.isEmpty()) { //업로드한 파일이 없을 경우
			return null;
		}
		
		// 업로드한 파일이 있을 경우 
		//C:\\uploadFiles\\test\\파일명  으로 서버에 저장
		uploadFile.transferTo(new File("C:\\uploadFiles\\test\\"+ uploadFile.getOriginalFilename()));
		
		//웬에서 해당 파일에 접근할 수 있는 경로를 반환
		
		// 서버 : C:\\uploadFiles\\test\\a.jpg
		// 웹 접근 주소 : /myPage/file/a.jpg 
		return "/myPage/file/" + uploadFile.getOriginalFilename();
		
		
		}
	
	
	
	
	
}