package com.bit.course.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bit.course.model.CourseDto;
import com.bit.std.model.StdDto;

public class CourseDao {
	Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
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
		String sql = "select cno, ctitle, cbegin, cend, cdays, climit, croom, prof, salesno from v_crs where cno=?";
		CourseDto bean = new CourseDto();
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
				bean.setCroom(rs.getString("croom"));
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
		int result = 0;
		PreparedStatement pstmt = null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			result = pstmt.executeUpdate();		
			System.out.println("del : " + cno + " result : " + result);	
		}finally{
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		return result;
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

	public ArrayList<CourseDto> selectAll() throws SQLException {
		ArrayList<CourseDto> list=new ArrayList<CourseDto>();
		String sql="select * from crs where cbegin > '2020-08-01' ORDER BY cno DESC";
		System.out.println("selectAll()1");
		try{
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				list.add(new CourseDto(
						 rs.getInt("cno")
						,rs.getString("ctitle")
						,rs.getDate("cbegin")
						,rs.getDate("cend")
						));
			}
		}finally{
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		}
		System.out.println("selectAll()2");
		return list;
	}

	public int registerOne (String id, int cno) throws SQLException {
		String sql = "insert into std values (std_seq.nextval, ?, ?, null, null, null, null, null, null)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, cno);
			return pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				if (pstmt != null)pstmt.close();
				if (conn != null)conn.close();
			
		}
		return 0;
	}
		
	public int levelUp (String id) throws SQLException {
		String sql2 = "update mbr set lvl='L02' where id=?";

		try {
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				if (pstmt != null)pstmt.close();
				if (conn != null)conn.close();
			
		}
		System.out.println(id);
		System.out.println(sql2);
		
		return 0;
	}
	
	public List<CourseDto> getSalesList(int eno) {
		String sql = "SELECT * FROM v_crs WHERE salesno = ? ORDER BY cno DESC";
		List<CourseDto> list = new ArrayList<CourseDto>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SalesCourseDto bean = new SalesCourseDto();
				bean.setCno(rs.getInt("cno")); 
				bean.setCtitle(rs.getString("ctitle"));
				bean.setCbegin(rs.getDate("cbegin"));
				bean.setClimit(rs.getInt("climit"));
				bean.setSalesno(rs.getInt("salesno"));
				bean.setStdcnt(rs.getInt("stdcnt"));
				list.add(bean);				
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public ArrayList<Object> getAssignList(int cno, int salesno) {
		String sql = "select std.sno, mbr.id as id, mbr.name as mname, mbr.tel, mbr.lvl, crs.cno, crs.ctitle, crs.cbegin, empl.name as ename from std "
				+ "left join mbr on std.id = mbr.id "
				+ "left join crs on std.cno = crs.cno "
				+ "left join empl on crs.profno = empl.eno "
				+ "where crs.cno = ? and crs.salesno = ?";
		StdDto std = null;
		CourseDto crs = null;
		ArrayList<Object> list = new ArrayList<Object>();
		try {
//			CourseDto crs = this.selectOne(cno);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			pstmt.setInt(2, salesno);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				std = new StdDto();
				std.setId(rs.getString("id"));
				std.setSno(rs.getInt("sno"));
				std.setName(rs.getString("mname"));
				std.setTel(rs.getString("tel"));
				std.setLvl(rs.getString("lvl"));
				if(rs.isFirst()) {
					crs = new CourseDto();
					crs.setCno(cno);
					crs.setCtitle(rs.getString("ctitle"));
					crs.setCbegin(rs.getDate("cbegin"));
					list.add(rs.getString("ename"));
					list.add(crs);
				}
				list.add(std);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		return list;
	}
	
	public int assignStudent(String[] array) {
		String sql = "UPDATE mbr SET lvl = 'L03' WHERE id IN (";
		for(int i = 0; i < array.length; i++) {
			if(i > 0) {
				sql += ", "; 
			}
			sql += "?";
		}
		sql += ")";
		System.out.println("assignStudent : " + sql);
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i = 0; i < array.length; i++) {
				pstmt.setString(i + 1, array[i]);
			}
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public int cancelStudent(String[] array) {
		String sql = "UPDATE mbr SET lvl = 'L02' WHERE id IN (";
		for(int i = 0; i < array.length; i++) {
			if(i > 0) {
				sql += ", "; 
			}
			sql += "?";
		}
		sql += ")";
		System.out.println("cancelStudent : " + sql);
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i = 0; i < array.length; i++) {
				pstmt.setString(i + 1, array[i]);
			}
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
