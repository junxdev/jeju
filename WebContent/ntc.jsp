<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.bit.lms_notice.model.NtcDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="template/head.jspf" %>
<style rel="stylesheet" type="text/css">
</style>
</head>
<body>
<%@ include file="template/header.jspf" %>
<%@ include file="template/menu.jspf" %>

<h2>LMS 공지사항</h2>

<table>

	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>종류</th>
		<th>날짜</th>
	</tr>
	
	<%
	ArrayList<NtcDto> list=(ArrayList<NtcDto>)request.getAttribute("list");
		for(NtcDto bean:list){
	%>	
	<tr>
			<td><%=bean.getNno() %></td>
			<td><a href="ntc/ndetail2.bit?idx=<%=bean.getNno() %>"><%=bean.getNtitle() %></a></td>
			<td><%=bean.getNtype() %></td>
			<td><%=bean.getNdate() %></td>			
	</tr>
		<%} %>
			
</table>
	<a href = "/jeju/lms/ntc/nadd.bit">글쓰기</a>
	
	<jsp:include page = "/paging2.jsp">
		<jsp:param value = "${paging.page}" name = "page"/>		
		<jsp:param value = "${paging.beginPage}" name = "beginPage"/>
		<jsp:param value = "${paging.endPage}" name = "endPage"/>
		<jsp:param value = "${paging.prev}" name = "prev"/>	
		<jsp:param value = "${paging.next}" name = "next"/>	
	</jsp:include>

<%@ include file="template/footer.jspf" %>
</body>
</html>