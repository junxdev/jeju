package com.bit.student.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.bit.account.model.MemberDto;
import com.bit.course.model.CourseDto;
import com.bit.util.Dto;
import com.bit.util.OracleJeju;

public class StudentDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private StudentDto studentDto = null;
	private CourseDto courseDto = null;
	private AttendanceDto attendanceDto = null;
	
	public StudentDao() {
		conn = new OracleJeju().getConnection();
	}
	
	public StudentDto selectStudent(String id) {
		String sql = "SELECT * FROM student WHERE id = ?";
		System.out.println("selectStudent() : " + id);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				studentDto = new StudentDto();
				studentDto.setSno(rs.getInt("sno"));
				studentDto.setGr1(rs.getInt("gr1"));
				studentDto.setGr2(rs.getInt("gr2"));
				studentDto.setGr3(rs.getInt("gr3"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("selectStudent() : " + studentDto);
		return studentDto;
	}

	public HashMap<String, Dto> studentInfo(int sno) {
		String sql = "SELECT * FROM v_std WHERE sno = ?";
		System.out.println("studentInfo() : " + sql + sno);
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sno);
			rs = ps.executeQuery();
			if(rs.next()) {
				courseDto = new CourseDto();
				courseDto.setCno(rs.getInt("cno"));
				courseDto.setCtitle(rs.getString("ctitle"));
				courseDto.setProf(rs.getString("prof"));
				courseDto.setCbegin(rs.getDate("cbegin"));
				courseDto.setCend(rs.getDate("cend"));
				courseDto.setCdays(rs.getInt("cdays"));
				
				attendanceDto = new AttendanceDto();
				attendanceDto.setCnt(rs.getInt("cnt"));
				attendanceDto.setAtt_total(rs.getInt("att_total"));
				attendanceDto.setAtt_rate(rs.getInt("att_rate"));
				attendanceDto.setLate(rs.getInt("late"));
				attendanceDto.setLeftearly(rs.getInt("leftearly"));
				attendanceDto.setAbsent(rs.getInt("absent"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("studentInfo() : \n" + courseDto + "\n" + attendanceDto);
		HashMap<String, Dto> dtoMap = new HashMap<String, Dto>();
		dtoMap.put("course", courseDto);
		dtoMap.put("attendance", attendanceDto);
		return dtoMap;
	}
	
	public StudentDto studentInfo(StudentDto student) {
		String sql = "SELECT * FROM v_std WHERE sno = ?";
		System.out.println("studentInfo() : " + sql + student.getSno());
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, student.getSno());
			rs = ps.executeQuery();
			if(rs.next()) {
				courseDto = new CourseDto();
				courseDto.setCno(rs.getInt("cno"));
				courseDto.setCtitle(rs.getString("ctitle"));
				courseDto.setProf(rs.getString("prof"));
				courseDto.setCbegin(rs.getDate("cbegin"));
				courseDto.setCend(rs.getDate("cend"));
				courseDto.setCdays(rs.getInt("cdays"));
				student.setCrs(courseDto);
				
				attendanceDto = new AttendanceDto();
				attendanceDto.setCnt(rs.getInt("cnt"));
				attendanceDto.setAtt_total(rs.getInt("att_total"));
				attendanceDto.setAtt_rate(rs.getInt("att_rate"));
				attendanceDto.setLate(rs.getInt("late"));
				attendanceDto.setLeftearly(rs.getInt("leftearly"));
				attendanceDto.setAbsent(rs.getInt("absent"));
				student.setAtt(attendanceDto);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("studentInfo() : \n" + courseDto + "\n" + attendanceDto);
		return student;
	}
}
