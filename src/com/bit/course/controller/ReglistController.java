package com.bit.course.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.course.model.CourseDao;
import com.bit.course.model.CourseDto;
import com.bit.course.model.SalesCourseDto;
import com.bit.emp.model.EmpDto;

@WebServlet("/lms/courses/reglist.bit")
public class ReglistController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		req.setAttribute("salesno", 1001);
		EmpDto dto = (EmpDto) req.getSession().getAttribute("elogin");
		try {
			if(!"영업".equals(dto.getDname())) { // 영업이 아닌 경우
				resp.sendRedirect("/jeju/lms/courses.bit");
				return;
			}
		} catch (NullPointerException e) { // salesno 오류
			System.out.println("Reglist 비로그인 접근");
			resp.sendRedirect("/jeju/login/elogin.bit");
			return;
		}
		int salesno = dto.getEno();
		List<CourseDto> list = null;
		try {
			list = new CourseDao().getSalesList(salesno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.setAttribute("list", list);
		RequestDispatcher rd = req.getRequestDispatcher("/reglist.jsp");
		rd.forward(req, resp);
	}

}
