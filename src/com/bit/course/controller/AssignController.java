package com.bit.course.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.course.model.CourseDao;
import com.bit.emp.model.EmpDto;
import com.bit.util.ErrorChecker;

@WebServlet("/lms/courses/assign.bit")
public class AssignController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-store");
//		req.setAttribute("salesno", 1001);
		EmpDto dto = (EmpDto) req.getSession().getAttribute("elogin");
		try {
			if(!"영업".equals(dto.getDname())) { // 영업이 아닌 경우
				resp.sendRedirect("/jeju/lms/courses.bit");
				return;
			}
		} catch (NullPointerException e) { // salesno 오류
			System.out.println("Assign 비로그인 접근");
			resp.sendRedirect("/jeju/login/elogin.bit");
			return;
		}
		
		int cno = Integer.parseInt(req.getParameter("cno"));
		int salesno = dto.getEno();
		ArrayList<Object> list = null ;
		try {
			list = new CourseDao().getAssignList(cno, salesno);
		} catch (SQLException e) { // list 오류
			e.printStackTrace();
		}
		req.setAttribute("list", list);
		req.getRequestDispatcher("/assign.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		Object x = req.getAttribute("assignList");
		String[] assignList = req.getParameterValues("assignList");
		String[] cancelList = req.getParameterValues("cancelList");
//		System.out.println(Arrays.toString(assignList));
//		System.out.println(Arrays.toString(cancelList));
		int assignResult = 0;
		int cancelResult = 0;
		if(assignList != null) {
			try {
				assignResult = new CourseDao().assignStudent(assignList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cancelList != null) {
			try {
				cancelResult = new CourseDao().cancelStudent(cancelList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("assign : " + assignResult + "cancel : " + cancelResult);
		PrintWriter out = resp.getWriter();
		if(assignResult == 0 && cancelResult == 0) {
			out.print("nothing");
			return;
		}
		if(assignResult != 0) {
			out.print(" assign");
		} 
		if(cancelResult != 0) {
			out.print(" cancel");
		}
	}
}
