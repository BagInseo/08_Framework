package edu.kh.project.main.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.project.main.model.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
	
	private final MainMapper mapper;
	private final BCryptPasswordEncoder bcrypt;
	
	
	//비밀버호 초기화
	@Override
	public int resetPw(int inputNo) {
		String pw="pass01!";
		
		String encPw=bcrypt.encode(pw);
		
		Map<String, Object> map= new HashMap<>();
		map.put("inputNo", inputNo);
		map.put("encPw", encPw);
		
		return mapper.resetPw(map);
	}
	
	
	
	
	//탈퇴 복구
	@Override
	public int restorationSecession(int inputNo) {
		return mapper.restorationSecession(inputNo);
	}

}
