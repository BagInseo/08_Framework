package edu.kh.project.myPage.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;

public interface MyPageService {

	/** 회원 정보 수정
	 * @param inputMember
	 * @param memberAddress
	 * @return result
	 */
	int updateInfo(Member inputMember, String[] memberAddress);


	/** 비밀번호 수정
	 * @param newPw
	 * @param currentPw
	 * @param newPwConfirm 
	 * @param memberNo
	 * @return
	 */
	int changePw(String newPw, String currentPw,String newPwConfirm, int memberNo);


	/** 회원 탈퇴
	 * @param memberPw
	 * @param memberNo
	 * @return result
	 */
	int changeSecession(String memberPw, int memberNo);


	/** 파일 업로드 테스트1
	 * @param uploadFile
	 * @return path
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException;



}
