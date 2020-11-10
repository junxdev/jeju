package com.bit.emp.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bit.std.model.V_stdDto;

public class EmpDao {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public EmpDao() throws SQLException{
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://@127.0.0.1:3306/lms?serverTimezone=UTC";
		String user="scott";
		String password="tiger";
		try {
			Class.forName(driver);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn=DriverManager.getConnection(url, user, password);
	}
	
	public ArrayList<EmpDto> selectAll() throws SQLException{
		ArrayList<EmpDto> bean=new ArrayList<EmpDto>();
		String sql="select empl.*, dpt.dname from empl,dpt where empl.dno=dpt.dno";
		try{
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				bean.add(new EmpDto(
						rs.getInt("eno"),
						rs.getString("ename"),
						rs.getString("pw"),
						rs.getString("tel"),
						rs.getString("email"),
						rs.getString("answer"),
						rs.getString("dno"),
						rs.getString("dname"),
						rs.getDate("hdate")));
			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return bean;
	}
	
	public EmpDto selectOne(int eno) throws SQLException{
		String sql="select empl.*, dpt.dname from empl,dpt where empl.dno=dpt.dno and eno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, eno);
			rs=pstmt.executeQuery();
			if(rs.next()){return new EmpDto(
						rs.getInt("eno"),
						rs.getString("ename"),
						rs.getString("pw"),
						rs.getString("tel"),
						rs.getString("email"),
						rs.getString("answer"),
						rs.getString("dno"),
						rs.getString("dname"),
						rs.getDate("hdate"));
			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return null;
	}
	
	public EmpDto elogin(int eno, String pw){
		String sql="select empl.*, dpt.dname from empl,dpt where empl.dno=dpt.dno and eno=? and pw=?";
		EmpDto bean=new EmpDto();
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, eno);
			pstmt.setString(2, pw);
			rs=pstmt.executeQuery();
			if(rs.next()){
				bean.setEno(rs.getInt("eno"));
				bean.setPw(rs.getString("pw"));
				bean.setDname(rs.getString("dname"));
				bean.setName(rs.getString("ename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
	
}
