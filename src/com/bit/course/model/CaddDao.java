package com.bit.course.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CaddDao {
	Connection conn;
	
	public CaddDao() throws SQLException {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://@127.0.0.1:3306/lms?serverTimezone=UTC";
		String user = "scott";
		String password = "tiger";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void insertOne(String ctitle, String cbegin, String cend, int croom, int profno, int salesno) throws SQLException {
		String sql = "insert into crs values(?,?,?,55,30,?,?,?)";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctitle);
			pstmt.setString(2, cbegin);
			pstmt.setString(3, cend);
			pstmt.setInt(4, croom);
			pstmt.setInt(5, profno);
			pstmt.setInt(6, salesno);
			pstmt.executeUpdate();
		}finally{
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}	
		
	}
}
