<%@page import="com.bit.course.model.SalesCourseDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,com.bit.course.model.CoursesDto"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BitCamp Jeju</title>
<%@ include file="template/head.jspf" %>
<script type="text/javascript">
	$(document).ready(function() {
		document.querySelectorAll('tr').forEach(function(e, i) {
		    if(i == 0) return false;
		    e.addEventListener('click', function() {
		        location.href = '/jeju/lms/courses/assign.bit?cno=' + e.firstElementChild.innerText;
			});
		});
	});
</script>
</head>
<body>
<%@ include file="template/header.jspf" %>
<%@ include file="template/menu.jspf" %>
<h2>수강신청 관리</h2>
<table>
	<tr>
		<th>강좌번호</th>
		<th>강좌명</th>
		<th>개강일</th>
		<th>정원</th>
		<th>신청인원</th>
	</tr>
<% 
ArrayList<SalesCourseDto> list = (ArrayList<SalesCourseDto>) request.getAttribute("list"); 
for(SalesCourseDto bean : list) {
%>
	<tr>
		<td><%= bean.getCno() %></td>
		<td><a href="/jeju/lms/courses/assign.bit?cno=<%= bean.getCno() %>"><%= bean.getCtitle() %></a></td>
		<td><%= bean.getCbegin() %></td>
		<td><%= bean.getClimit() %></td>
		<td><%= bean.getStdcnt() %></td>
	</tr>
<%
}
%>
</table>
<%@ include file="template/footer.jspf" %>
</body>
</html>