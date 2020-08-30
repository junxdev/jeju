package com.bit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.course.model.PagingDto;
import com.bit.lms_notice.model.NtcDao;
import com.bit.lms_notice.model.NtcDto;

@WebServlet("/notice.bit")
public class NoticeController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			NtcDao dao = new NtcDao();
			
			int page = 1;
		
			if(request.getParameter("page")!=null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			PagingDto paging = new PagingDto();
			paging.setPage(page);
			
			int count = dao.getAllCount();
			paging.setTotalCount(count);
			
		
			NtcDao dao2 = new NtcDao();
			ArrayList<NtcDto> list = dao2.getList(page);
			
			NtcDao dao3 = new NtcDao();
			dao3.selectAll();
			request.setAttribute("list",list);
			request.setAttribute("paging", paging);
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		request.getRequestDispatcher("/notice.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}