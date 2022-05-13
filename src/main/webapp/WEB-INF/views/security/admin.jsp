<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>/security/admin page</h1>
	<!-- principal : 현재 사용자 -->
	<!-- hasRole(role) : 해당 권한이 잇으면 true -->
	<!-- hasAuthority(Authority) -->
	<!-- hasAnyRole(role, role2) : () 안에 있는 권한 중에 하나라도 가지고 있으면 true -->
	<!-- hasAnyAuthority(Auth1.Auth2) -->
	<!-- permiAll : 모든 사용자 허용 -->
	<!-- denyAll : 모든 사용자 거부 -->
	<!-- isAnonymous() : 익명의 사용자인 경우(로그인 하지 않은 경우도 포함) true -->
	<!-- isAuthenticated() : 인증된 사용자면 true -->
	<p>
		현재 사용자 정보 :
		<sec:authentication property="principal" />
	</p>
	<p>
		MemberVO :
		<sec:authentication property="principal.member" />
	</p>
	<p>
		사용자 이름 :
		<sec:authentication property="principal.member.userName" />
	</p>
	<p>
		사용자 아이디 :
		<sec:authentication property="principal.username" />
	</p>
	<p>
		사용자 권한 리스트 :
		<sec:authentication property="principal.member.authList" />
	</p>
	<sec:authorize access="isAuthenticated()">
		<a href="/customLogout">Logout</a>
	</sec:authorize>
	<sec:authorize access="isAnonymous()">
		<a href="/customLogin">Login</a>
	</sec:authorize>
</body>
</html>