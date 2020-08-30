<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.bit.std.model.V_stdDto, com.bit.std.model.AttDto" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mystus</title>
<%@ include file="template/head.jspf" %>
<style rel="stylesheet" type="text/css">
</style>
<style type="text/css">
	#today{
		background-color: gray;
		font-size: 3em;
		color: white;
	}
	.alarm{
		font-size: 2em;
		color: red;
	}
	.alarm2{
		font-size: 2em;
		color: gray;
	}
</style>
<script>
</script>
</head>
<body>
<%@ include file="template/header.jspf" %>
<%@ include file="template/menu.jspf" %>
<h2>출결입력</h2>

<c:if test="${att.key ne null}">
	<div class="alarm">주의: 출석입력은 당일만 가능합니다. 반드시 당일 입력 완료해 주시기바랍니다.</div>
</c:if>
<c:if test="${att.key eq null}">
	<div class="alarm2">오늘의 출석입력을 모두 완료하였습니다.</div>
</c:if>

<table>
	<tr>
		<th>강좌번호</th>
		<th>강좌명</th>
		<th>개강일</th>
		<th>종강일</th>
		<th>수업일수</th>
		<th>정원</th>
		<th>현재학생수</th>
	</tr>
	<tr>
		<td>${crs.cno }</td>
		<td>${crs.ctitle }</td>
		<td>${crs.cbegin }</td>
		<td>${crs.cend }</td>
		<td>${crs.cdays }</td>
		<td>${crs.climit }</td>
		<td>${crs.stdcnt }</td>
	</tr>
</table>
<br>
<div id="today">오늘은
	<c:set var="today" value="<%=new java.util.Date()%>"/>
	<fmt:formatDate value="${today}" pattern="yyyy-MM-dd"/>
	입니다.
</div>
<br>
<form action="<%=root %>/lms/mytask/mystus/att.bit" method="post">
	<input type="hidden" name="cno" value="${crs.cno }"/>
	<table>
		<tr>
			<th>학생번호</th>
			<th>학생이름</th>
			<th>정상등원</th>
			<th>지각</th>
			<th>오전등원안함</th>
			<th>정상하원</th>
			<th>조퇴</th>
			<th>오후등원안함</th>
		</tr>
		<c:forEach items="${att }" var="att">
			<tr id="row">
				<td><input type="hidden" name="${att.key }" value="${att.key }"/>${att.key }</td>
				<td>${att.value }</td>
				<td><input type="radio" name="ckin${att.key }" id="ckin1" value="1" checked="checked"/></td>
				<td><input type="radio" name="ckin${att.key }" id="ckin2" value="2" /></td>
				<td><input type="radio" name="ckin${att.key }" id="ckin0" value="0" /></td>
				<td><input type="radio" name="ckout${att.key }" id="ckout1" value="1" checked="checked"/></td>
				<td><input type="radio" name="ckout${att.key }" id="ckout3" value="3" /></td>
				<td><input type="radio" name="ckout${att.key }" id="ckout0" value="0" /></td>
			</tr>
		</c:forEach>
	</table>
	<button type="submit">출결입력완료</button>
</form>

<c:if test="${att.key ne null}">
	<div class="alarm">주의: 출석입력은 저장 후 수정이 불가능합니다.</div>
</c:if>
<a href="<%=root %>/lms/mytask/mycourses.bit?eno=${elogin.eno}">내강의목록으로 돌아가기</a>

<%@ include file="template/footer.jspf" %>
</body>
</html>