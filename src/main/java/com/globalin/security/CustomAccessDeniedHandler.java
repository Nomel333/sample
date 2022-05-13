package com.globalin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

// 이 클래스가 접근 제한 설정을 자세하게 하고 싶을 때
// AccessDeniedHandler 인터페이스를 구현해서 작성
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 여기서 상세하게 설정하고 싶은 것들
		// 접근 제한이 되었을 때 쿠키나 세션에 어떤 작업을 한다거나
		// 헤더 정보를 추가하거나
		
		// 리다이렉트 방식으로 페이지를 이동하도록 변경
		response.sendRedirect("/accessError");
	}

}
