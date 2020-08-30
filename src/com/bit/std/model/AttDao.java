package com.bit.std.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

public class AttDao {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public AttDao() throws SQLException{
		String driver="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="scott";
		String password="tiger";
		try {
			Class.forName(driver);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn=DriverManager.getConnection(url, user, password);
	}
	
	public ArrayList<AttDto> selectAllBySno(int sno) throws SQLException{
		ArrayList<AttDto> list=new ArrayList<AttDto>();
		String sql="select att.*,v_std.name from att,v_std where v_std.sno=att.sno and att.sno=?";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, sno);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new AttDto(
						rs.getInt("ano"),
						rs.getInt("sno"),
						rs.getInt("ckin"),
						rs.getInt("ckout"),
						rs.getDate("adate"),
						rs.getString("name")));
			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return list;
	}
	
	public TreeMap<Integer, String> attByCno(int cno) throws SQLException{
		TreeMap<Integer,String> attlist=new TreeMap<Integer,String>();
		String sql="select a.sno,a.name "
				+ "from (select v_std.sno, v_std.name from v_std where lvl='L03' and cno=?) a left outer join "
				+ "(select sno from att where to_char(adate,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')) b "
				+ "on a.sno=b.sno where b.sno is null";
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			rs=pstmt.executeQuery();
			while(rs.next()){
				attlist.put(
						rs.getInt("sno"),
						rs.getString("name"));
			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return attlist;
	}
	
	public void addAtt(int sno, int ckin, int ckout) throws SQLException{
		try{
				String sql="insert into att values (att_seq.nextval,?,sysdate,?,?)";
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, sno);
				pstmt.setInt(2, ckin);
				pstmt.setInt(3, ckout);
				pstmt.executeUpdate();
//			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
	}
}