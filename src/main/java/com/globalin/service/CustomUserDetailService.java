package com.globalin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.globalin.domain.CustomUser;
import com.globalin.domain.MemberVO;
import com.globalin.mapper.MemberMapper;

// 이 클래스는 스프링 시큐리티에 유저 정보를 (인증, 권한) 데이터 베이스에서 가져와주는 역할
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private MemberMapper mapper;

	private Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);

	// UserDetailsService는 loadUserByUserName()이라는 하나의 추상 메소드
	// 리턴 타입이 org.springframework.core.userdetails.UserDetaols
	// 스프링에서 취급하는 사용자 정보 객체는 오직 UserDetails타입
	// 우리가 만든 사용자 정보 객체는 MemberVO타입이다
	// 우리가 만든 사용자 정보 객체를 스프링 시큐리티가 사용할 수 있도록
	// MemberVO 타입을 UserDetails타입으로 변환하는 작업이 필요
	// 1) MemberVO클래스를 UserDetails 인터페이스를 구현하도록 하는 방법
	// 2) 제 3의 클래스를 이용해서 MemberVO 클래스를 수정하지 않고 하는 방법
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("Load User By UserName : " + username);

		// username으로 멤버 객체 가져오기
		MemberVO vo = mapper.read(username);

		log.warn("queried by mapper : " + vo);

		// 매퍼가 vo를 가져왔으면 MemberVO를 스프링 시큐리티가 사용하는 타입으로 ㅕㅂㄴ경
		// vo를 가져오지 못했으면 그대로 null을 리턴

		return vo == null ? null : new CustomUser(vo);
	}

}
