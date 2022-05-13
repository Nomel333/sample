package com.globalin.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml" })
public class MemberTest {

	// member를 만들어서 테이블에 삽입

	// member를 만들때 패스워드 암호화 필요
	@Autowired
	private PasswordEncoder encoder;

	// db 연결 객체 필요
	@Autowired
	private DataSource ds;

	@Test
	public void testInsertAuth() {

		String sql = "insert into tbl_member_auth (userid, auth) values(?,?)";

		for (int i = 0; i < 100; i++) {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);

				// 번호가 80 밑이면 일반 사용자
				if (i < 80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(2, "ROLE_USER");
				} else if (i < 90) {
					// 번호가 81~90이면 운영자
					pstmt.setString(1, "manager" + i);
					pstmt.setString(2, "ROLE_MEMBER");
				} else {
					// 90이상이면 관리자
					pstmt.setString(1, "admin" + i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} // for 끝

	} // test 끝
}
