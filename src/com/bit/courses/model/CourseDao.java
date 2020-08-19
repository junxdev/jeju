package com.bit.courses.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDao {
	Connection conn;
	
	public CourseDao() throws SQLException {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "scott";
		String password = "tiger";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			
			System.out.println("db connection complete");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public CourseDto selectOne(int cno) throws SQLException {
		String sql ="select cno, ctitle, cbegin, cend, cdays, climit, croom, empl.name as prof, salesno from crs left join empl on empl.eno = crs.profno  where cno=?";
		CourseDto bean = new CourseDto();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {	
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bean.setCno(rs.getInt("cno"));
				bean.setCtitle(rs.getString("ctitle"));
				bean.setCbegin(rs.getDate("cbegin"));
				bean.setCend(rs.getDate("cend"));
				bean.setCdays(rs.getInt("cdays"));
				bean.setClimit(rs.getInt("climit"));
				bean.setCroom(rs.getInt("croom"));
				bean.setProf(rs.getString("prof"));
				bean.setSalesno(rs.getInt("salesno"));
			}
		} finally {
			if (rs != null)rs.close();
			if (pstmt != null)pstmt.close();
			if (conn != null)conn.close();
		}
		return bean;
	}
		
	public int deleteOne(int cno) throws SQLException {
		String sql = "delete from crs where cno=?";
		PreparedStatement pstmt = null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			int result = pstmt.executeUpdate();		
			System.out.println("del : " + cno + " result : " + result);	
			return result;
		}finally{
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
	
	public int updateOne(int cno, String ctitle, String cbegin, String cend, int croom, int profno, int salesno) throws SQLException {
		String sql = "update crs set ctitle=?, cbegin=?, cend=?, croom=?, profno=?, salesno=? where cno=?"; 
		int result = 0;
		PreparedStatement pstmt = null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, ctitle);
			pstmt.setString(2, cbegin);
			pstmt.setString(3, cend);
			pstmt.setInt(4, croom);
			pstmt.setInt(5, profno);
			pstmt.setInt(6, salesno);
			pstmt.setInt(7, cno);		
			result = pstmt.executeUpdate();
		}finally{
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return result;
	}

}



