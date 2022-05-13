package com.globalin.ex02;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class JDBCTest {

	@Test
	public void testConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "junjun", "12345");

			assertNotNull(conn);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			// fail 메소드가 실행되면 무조건 테스트 실패
			// fail(메세지)
//			fail(e.getMessage());
		} 
	}

}
