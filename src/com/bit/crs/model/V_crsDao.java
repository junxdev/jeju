package com.bit.crs.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class V_crsDao {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public V_crsDao() throws SQLException{
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://@127.0.0.1:3306/lms?serverTimezone=UTC";
		String user="scott";
		String password="tiger";
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, user, password);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<V_crsDto> selectAll() throws SQLException{
		ArrayList<V_crsDto> list=new ArrayList<V_crsDto>();
		String sql="select * from v_crs";
		try{
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new V_crsDto(
						rs.getInt("cno"),
						rs.getInt("cdays"),
						rs.getInt("dcnt"),
						rs.getInt("climit"),
						rs.getInt("profno"),
						rs.getInt("salesno"),
						rs.getInt("stdcnt"),
						rs.getInt("avg_gr1"),
						rs.getInt("avg_gr2"),
						rs.getInt("avg_gr3"),
						rs.getInt("avg_att"),
						rs.getString("ctitle"),
						rs.getString("croom"),
						rs.getString("prof"),
						rs.getDate("cbegin"),
						rs.getDate("cend")));
			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return list;
	}
	
	public ArrayList<V_crsDto> selectMine(int profno) throws SQLException{
		ArrayList<V_crsDto> list=new ArrayList<V_crsDto>();
		String sql="select * from v_crs where profno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, profno);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new V_crsDto(
						rs.getInt("cno"),
						rs.getInt("cdays"),
						rs.getInt("dcnt"),
						rs.getInt("climit"),
						rs.getInt("profno"),
						rs.getInt("salesno"),
						rs.getInt("stdcnt"),
						rs.getInt("avg_gr1"),
						rs.getInt("avg_gr2"),
						rs.getInt("avg_gr3"),
						rs.getInt("avg_att"),
						rs.getString("ctitle"),
						rs.getString("croom"),
						rs.getString("prof"),
						rs.getDate("cbegin"),
						rs.getDate("cend")));
			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return list;
	}
	
	public V_crsDto selectOne(int cno) throws SQLException{
		String sql="select * from v_crs where cno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			rs=pstmt.executeQuery();
			if(rs.next())return new V_crsDto(
					rs.getInt("cno"),
					rs.getInt("cdays"),
					rs.getInt("dcnt"),
					rs.getInt("climit"),
					rs.getInt("profno"),
					rs.getInt("salesno"),
					rs.getInt("stdcnt"),
					rs.getInt("avg_gr1"),
					rs.getInt("avg_gr2"),
					rs.getInt("avg_gr3"),
					rs.getInt("avg_att"),
					rs.getString("ctitle"),
					rs.getString("croom"),
					rs.getString("prof"),
					rs.getDate("cbegin"),
					rs.getDate("cend"));
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return null;
	}
}