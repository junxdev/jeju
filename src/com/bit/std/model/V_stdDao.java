package com.bit.std.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class V_stdDao {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public V_stdDao() throws SQLException{
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
	
	public ArrayList<V_stdDto> selectAll() throws SQLException{
		ArrayList<V_stdDto> list=new ArrayList<V_stdDto>();
		String sql="select * from v_std";
		try{
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new V_stdDto(
						rs.getInt("cno"),
						rs.getInt("cdays"),
						rs.getInt("sno"),
						rs.getInt("gr1"),
						rs.getInt("gr2"),
						rs.getInt("gr3"),
						rs.getInt("cnt"),
						rs.getInt("att_total"),
						rs.getInt("att_rate"),
						rs.getInt("late"),
						rs.getInt("leftearly"),
						rs.getInt("absent"),
						rs.getString("ctitle"),
						rs.getString("prof"),
						rs.getString("name"),
						rs.getString("lvl"),
						rs.getString("lname"),
						rs.getString("ldesc"),
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
	
	public ArrayList<V_stdDto> selectActiveStdByCrs(int cno) throws SQLException{
		ArrayList<V_stdDto> list=new ArrayList<V_stdDto>();
		String sql="select * from v_std where lvl='L03' and cno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new V_stdDto(
						rs.getInt("cno"),
						rs.getInt("cdays"),
						rs.getInt("sno"),
						rs.getInt("gr1"),
						rs.getInt("gr2"),
						rs.getInt("gr3"),
						rs.getInt("cnt"),
						rs.getInt("att_total"),
						rs.getInt("att_rate"),
						rs.getInt("late"),
						rs.getInt("leftearly"),
						rs.getInt("absent"),
						rs.getString("ctitle"),
						rs.getString("prof"),
						rs.getString("name"),
						rs.getString("lvl"),
						rs.getString("lname"),
						rs.getString("ldesc"),
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
	
	public ArrayList<V_stdDto> selectAllStdByCrs(int cno) throws SQLException{
		ArrayList<V_stdDto> list=new ArrayList<V_stdDto>();
		String sql="select * from v_std where cno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new V_stdDto(
						rs.getInt("cno"),
						rs.getInt("cdays"),
						rs.getInt("sno"),
						rs.getInt("gr1"),
						rs.getInt("gr2"),
						rs.getInt("gr3"),
						rs.getInt("cnt"),
						rs.getInt("att_total"),
						rs.getInt("att_rate"),
						rs.getInt("late"),
						rs.getInt("leftearly"),
						rs.getInt("absent"),
						rs.getString("ctitle"),
						rs.getString("prof"),
						rs.getString("name"),
						rs.getString("lvl"),
						rs.getString("lname"),
						rs.getString("ldesc"),
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
	
	public ArrayList<V_stdDto> selectAllPerPage(int startRow, int endRow) throws SQLException{
		ArrayList<V_stdDto> list=new ArrayList<V_stdDto>();
		String sql="select * from (select cno, cdays, sno, gr1, gr2, gr3, cnt, att_total,"
				+ "att_rate, late,leftearly,absent,ctitle, prof, name, lvl, lname,"
				+ "ldesc,cbegin, cend, rownum as rn from v_std) where rn between ? and ?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			System.out.println(startRow);
			System.out.println(endRow);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new V_stdDto(
						rs.getInt("cno"),
						rs.getInt("cdays"),
						rs.getInt("sno"),
						rs.getInt("gr1"),
						rs.getInt("gr2"),
						rs.getInt("gr3"),
						rs.getInt("cnt"),
						rs.getInt("att_total"),
						rs.getInt("att_rate"),
						rs.getInt("late"),
						rs.getInt("leftearly"),
						rs.getInt("absent"),
						rs.getString("ctitle"),
						rs.getString("prof"),
						rs.getString("name"),
						rs.getString("lvl"),
						rs.getString("lname"),
						rs.getString("ldesc"),
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
	
	public V_stdDto selectOne(int sno) throws SQLException{
		String sql="select * from v_std where sno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, sno);
			rs=pstmt.executeQuery();
			if(rs.next())return new V_stdDto(
					rs.getInt("cno"),
					rs.getInt("cdays"),
					rs.getInt("sno"),
					rs.getInt("gr1"),
					rs.getInt("gr2"),
					rs.getInt("gr3"),
					rs.getInt("cnt"),
					rs.getInt("att_total"),
					rs.getInt("att_rate"),
					rs.getInt("late"),
					rs.getInt("leftearly"),
					rs.getInt("absent"),
					rs.getString("ctitle"),
					rs.getString("prof"),
					rs.getString("name"),
					rs.getString("lvl"),
					rs.getString("lname"),
					rs.getString("ldesc"),
					rs.getDate("cbegin"),
					rs.getDate("cend"));
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return null;
	}
	
	public void update(int sno, int gr1, int gr2, int gr3) throws SQLException{
		String sql="update std set gr1=?, gr2=?, gr3=? where sno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, gr1);
			pstmt.setInt(2, gr2);
			pstmt.setInt(3, gr3);
			pstmt.setInt(4, sno);
			pstmt.executeUpdate();
		}finally{
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
	
	public void sedit(int sno, String lvl) throws SQLException{
		String sql="update mbr set lvl=? where id=(select id from std where sno=?)";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, lvl);
			pstmt.setInt(2, sno);
			pstmt.executeUpdate();
		}finally{
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
}