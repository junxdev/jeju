<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/template/head.jspf" %>
<style type="text/css">
#content form table input{
	border: none;
	font-size: 16px;
	height: 40px;
	width: 100%;
	line-height: 40px;
	font-family: arial;
	background-color: #ffffff00;
	color: #81d8d0;
}
#content table td{
	padding: 0 30px;
	text-align: left;
}
</style>
</head>
<body>	
<%@ include file="template/header.jspf" %>
<%@ include file="template/menu.jspf" %>
	<h2>공지사항 추가</h2>
	<form action="/jeju/lms/ntc/nadd.bit" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th><label for="ntitle">제목</label></th>
				<td><input type="text" name="ntitle" id="ntitle" /></td>
			</tr>
			<tr>
				<th><label for="ntype">종류</label></th>
				<td>
					<input type="radio" name="ntype" id="ntype" value="센터공지"/>센터공지
					<input type="radio" name="ntype" id="ntype" value="과정공지"/>과정공지
					<input type="radio" name="ntype" id="ntype" value="기타공지"/>기타공지
				</td>
			</tr>
	 		<tr>
				<th><label for="file1">파일첨부</label></th>
				<td><input type="file" name="file1" id="file1" /></td>
			</tr>
	 		<tr>
				<th><label for="nbody">내용</label></th>
				<td><input type="text" name="nbody" id="nbody" maxlength="666"/></td>
			</tr>
		</table>
		<div>
			<button type="submit">글쓰기</button>
			<button type="button" onclick="history.back();">목록으로 돌아가기</button>
		</div>
	</form>
<%@ include file="template/footer.jspf" %>
</body>
</html>




