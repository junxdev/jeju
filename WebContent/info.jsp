<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비트캠프 제주지점 : 회원정보</title>
<%@ include file="template/head.jspf" %>
<style type="text/css">
#content table tr:nth-child(n+2):hover{
	color: #81d8d0;
}

</style>
<script type="text/javascript">
	$(document).ready(function() {
		var typelist = ['id', 'pw', 'answer', 'name', 'tel', 'email'];
		$('tr').each(function(i, e) {
			if(i == 0) return;
			$(e).on('click', function() {
				/* console.log($(e).children().eq(1).text()); */
				location.href = '/jeju/mypage/infoedit.bit?type=' + typelist[i];
			});
			$(e).css('cursor', 'pointer');
		});
	});
</script>
</head>
<body>
<%@ include file="template/header.jspf" %>
<%@ include file="template/menu.jspf" %>
	<h2>회원 정보 ${user.name }</h2>
	<div>
		<table>
			<tbody>
				<tr><th>아이디</th><td>${user_info.id }</td><td></td></tr>
				<tr><th>비밀번호</th><td>xxxxxxxx</td><td>수정</td></tr>
				<tr><th>비밀번호 찾기 답</th><td>${user_info.answer }</td><td>수정</td></tr>
				<tr><th>이름</th><td>${user_info.name }</td><td>수정</td></tr>
				<tr><th>전화번호</th><td>${user_info.tel }</td><td>수정</td></tr>
				<tr><th>이메일</th><td>${user_info.email }</td><td>수정</td></tr>
			</tbody>
		</table>
	</div>
	<div>
		<a href="/jeju/login/leave.bit">탈퇴하기</a>
	</div>
<%@ include file="template/footer.jspf" %>
</body>
</html>