package com.bit.std.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.crs.model.V_crsDao;
import com.bit.crs.model.V_crsDto;
import com.bit.std.model.AttDao;
import com.bit.std.model.AttDto;
import com.bit.std.model.V_stdDao;

@WebServlet("/lms/mytask/mystus/att.bit")
public class AttController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			V_crsDao dao = new V_crsDao();
			V_crsDto crs = dao.selectOne(Integer.parseInt(request.getParameter("cno")));
			request.setAttribute("crs", crs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			AttDao dao2 = new AttDao();
			TreeMap<Integer, String> att=dao2.attByCno(Integer.parseInt(request.getParameter("cno")));
			request.setAttribute("att", att);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/att.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Enumeration en = request.getParameterNames();
		ArrayList<AttDto> attAddList=new ArrayList<AttDto>();
	
		int cnt=0;
		int cno=0;
		while(en.hasMoreElements()){
			cnt++;
			if(cnt==1) {cno=Integer.parseInt(request.getParameter((String)(en.nextElement())));}
			else{
				attAddList.add(new AttDto(
					Integer.parseInt(request.getParameter((String)(en.nextElement()))),
					Integer.parseInt(request.getParameter((String)(en.nextElement()))),
					Integer.parseInt(request.getParameter((String)(en.nextElement())))));
			}
		}
		
		int sno=0;
		int ckin=0;
		int ckout=0;
		
		for(AttDto bean : attAddList){
			sno=bean.getSno();
			ckin=bean.getCkin();
			ckout=bean.getCkout();
			try {
				AttDao dao = new AttDao();
				dao.addAtt(sno,ckin,ckout);
				System.out.println("updated");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect(request.getContextPath()+"/lms/mytask/mystus.bit?cno="+cno);
	}
}