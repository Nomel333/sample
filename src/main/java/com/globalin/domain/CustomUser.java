package com.globalin.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

// 스프링 시큐리티가 사용자 정보를 UserDetails 타입으로 밖에 사용하지 않기 때문에
// 우리가 사용하는 클래스 타입을 스프링 시큐리티가 사용가능한 타입으로 변경해주는 작업이
// 필요하다

public class CustomUser extends User {

	public MemberVO getMember() {
		return member;
	}

	public void setMember(MemberVO member) {
		this.member = member;
	}

	private MemberVO member;

	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	// MemberVO를 스프링 시큐리티가 사용할 수 있도록 바꿔주는 기능
	public CustomUser(MemberVO vo) {
		super(vo.getUserid(), vo.getUserpw(), vo.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		// 1.8버전에 추가된 stream api + 화살표 함수
		// function(auth){...함수 몸체.. }
		// (auth => ...함수 몸체..)
		// AuthVO객체를 GrantedAuthority 객체로 변환
		this.member = vo;
	}

}
