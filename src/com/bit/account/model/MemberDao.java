package com.bit.account.model;

import com.bit.util.OracleJeju;

import java.sql.*;

public class MemberDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	MemberDto dto = null;
	
	public MemberDao() {
		conn = new OracleJeju().getConnection();
	}
	
	public void close() {
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int lookupId(String id) {
		String sql = "SELECT * FROM mbr WHERE id = ?";
		System.out.println("lookupId() : " + id);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public String lookupId(String name, String tel, String email) {
		String sql = "SELECT * FROM mbr WHERE mname = ? AND tel = ? AND email = ?";
		System.out.println("lookupId() : " + name + ", " + tel + ", " + email);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, tel);
			ps.setString(3, email);
			rs = ps.executeQuery();
			if(rs.next()) return rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public int lookupPw(String id, String pw) throws SQLException {
		String sql = "SELECT pw FROM mbr WHERE id = ?";
		System.out.println("lookupPw() : " + id + ", " + pw);
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();
		if(rs.next()) {
			String original = rs.getString("pw");
			if(pw.equals(original)) return 1;
		}
		return 0;
	}
	
	public int joinMember(String id, String pw, String answer, String name, String tel, String email) {
		String sql = "INSERT INTO mbr VALUES(?, ?, ?, ?, ?, ?, 'L01')";
		System.out.println("joinMember() : " + id + ", " + pw + ", " + answer + ", " + name + ", " + tel + ", " + email);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.setString(4, tel);
			ps.setString(5, email);
			ps.setString(6, answer);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public MemberDto selectMemeber(String id) {
		String sql = "SELECT * FROM mbr WHERE id = ?";
		System.out.println("selectMemeber() : " + id);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDto();
				dto.setId(rs.getString("id"));
				dto.setAnswer(rs.getString("answer"));
				dto.setName(rs.getString("mname"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setLvl(rs.getString("lvl"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("selectMemeber() : " + dto);
		return dto;
	}
	
	public int editMember(String id, String type, String data) {
		String sql = "UPDATE mbr SET " + type + " = ? WHERE id = ?";
		System.out.println("editMember() : " + id + ", " + type + ", " + data);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, data);
			ps.setString(2, id);
			System.out.println("end?1");
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end?2");
		return 0;
	}
	
	public int deleteMember(String id, String pw) {
//		String sql = "UPDATE mbr SET lvl = 'L07' and id =  WHERE id = ? and pw = ?";
		String sql = "UPDATE mbr SET id = '#' || LPAD(deleted_member_seq.nextval, 5, '0') "
				+ "|| TO_CHAR(sysdate, 'yyyymmdd'), lvl = 'L07' "
				+ "WHERE id = ? AND pw = ?";
		System.out.println("deleteMember() : " + id + ", " + pw);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	// �α��� �� ��� ���� ���� ���޿�
	public MemberDto login(String id, String pw) {
		String sql = "SELECT * FROM mbr WHERE id = ? AND pw = ?";
		System.out.println("login() : " + id + ", " + pw);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDto();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("mname"));
				dto.setLvl(rs.getString("lvl"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("login() : " + dto);
		return dto;
	}
	
	public int checkAnswer(String id, String answer) {
		String sql = "SELECT * FROM mbr WHERE id = ? AND answer = ?";
		System.out.println("checkAnswer() : " + id + ", " + answer);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, answer);
			rs = ps.executeQuery();
			if(rs.next()) return 1;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
//	public static void main(String[] args) {
//		
//		MemberDTO dto = null;
//				dto = new MemberDAO().login("user81", "test!1111");
//				System.out.println(dto);
//		System.out.println(dto == null);
//		System.out.println(dto.hashCode());
//	}
}
