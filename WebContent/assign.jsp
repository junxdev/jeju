<%@page import="com.bit.std.model.StdDto"%>
<%@page import="com.bit.course.model.CourseDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BitCamp Jeju</title>
<%@ include file="template/head.jspf" %>
<link rel="stylesheet" type="text/css" href="/jeju/css/frame.css"/>
<script type="text/javascript">
$(document).ready(function() {
	
	// 배정인원이 정원에 도달하면 체크박스 비활성화하는 함수
	var check_maximum = function() {
		if($('input:checked').length > 29) {
			$('input:checkbox:not(:checked)').each(function(i, e) {
				$(this).attr('disabled', true);
			});
			/* $('#cntassign').text($('input:checked').length + '/ 30 : 새로운 학생을 배정하려면 배정한 학생을 취소하세요'); */
		} else {
			$('input:checkbox:not(:checked)').each(function(i, e) {
				$(this).attr('disabled', false);
			});
			/* $('#cntassign').text($('input:checked').length + '/ 30'); */
		}
		document.querySelector('#assigned_count').innerText = $('input:checked').length;
	}
	
	// 접근 시 정원 도달 확인
	check_maximum();
	
	// 체크박스 클릭 시 정원 도달 확인
	$('input:checkbox').each(function(i, e) {
		$(this).on('click', check_maximum);
	});
	
	// 저장버튼 클릭 시
	$('#btnassign').on('click', function() {
		/* $('.student').each(function() {
			var student = $(this);
			if($(student).children().eq(0).text().trim().endsWith('L02') && $(student).children().eq(2).prop('checked')) {
				assignList.push($(student).children().eq(0).text().trim().substr(0, $(student).children().eq(0).text().trim().search(',')));
			} else if($(student).children().eq(0).text().trim().endsWith('L03') && !($(student).children().eq(2).prop('checked'))) {
				cancelList.push($(student).children().eq(0).text().trim().substr(0, $(student).children().eq(0).text().trim().search(',')));
			}
		}); */
		
		// 배정 학생 배열과 배정 취소 학생 배열 생성
		var assignList = [];
		var cancelList = [];
		document.querySelectorAll('.student').forEach(function(e, i) {
			var id = e.children.item(1).innerText;
			var level = e.children.item(4).innerText;
			var assigned = e.children.item(5).children.item(1).checked;
			if('L02' == level && assigned) assignList.push(id);
			if('L03' == level && !assigned) cancelList.push(id);
		});
		
		// 배정 수정이 없으면 ajax 실행하지 않음 
		if(!(assignList.length + cancelList.length)) {
			$('#resultassign').text('변경사항이 없습니다.');
			return false;
		}
		
		// 배정 수정이 있으면 ajax 실행
		$.ajax({
			method : 'POST',
			traditional : true,
			data : {'assignList' : assignList, 'cancelList' : cancelList},
			dataType: 'text',
			success: function(message) {
				/* console.log(message); */
				if(message.search("assign")) {
					var regex = /, L02/;
					for(var i = 0; i < assignList.length; i++) {
						var student = assignList[i];
						/* console.log("assign : " + student); */
						document.querySelectorAll('.student').forEach(function(e, i) {
							/* console.log("assign each" + student == $(this).children().eq(0).text().trim().substr(0, $(this).children().eq(0).text().trim().search(','))) */
							/* if(student == $(this).children().eq(0).text().trim().substr(0, $(this).children().eq(0).text().trim().search(','))) {
								$(this).children().eq(0).text($(this).children().eq(0).text().replace(regex, ', L03'));
							} */
							if(student == e.children.item(1).innerText) e.children.item(4).innerText = 'L03';
						});
					}
				}
				if(message.search("cancel")) {
					var regex = /, L03/;
					for(var i = 0; i < cancelList.length; i++) {
						var student = cancelList[i];
						/* console.log("cancel : " + student); */
						document.querySelectorAll('.student').forEach(function(e, i) {
							/* console.log("cancel each" + student == $(this).children().eq(0).text().trim().substr(0, $(this).children().eq(0).text().trim().search(',')))
							if(student == $(this).children().eq(0).text().trim().substr(0, $(this).children().eq(0).text().trim().search(','))) {
								$(this).children().eq(0).text($(this).children().eq(0).text().replace(regex, ', L02'));
							} */
							if(student == e.children.item(1).innerText) e.children.item(4).innerText = 'L02';
						});
					}
				}
				$('#resultassign').text('변경사항을 저장했습니다.');
				if("nothing" == message) {
					$('#resultassign').text('오류로 인해 변경사항을 저장하지 못했습니다. 관리자 문의');
				}
			}
		});
	});
	});
</script>

</head>
<body>
<%@ include file="template/header.jspf" %>
<%@ include file="template/menu.jspf" %>
	<h2>수강신청 관리</h2>
	<%-- <div>
	<% 
	ArrayList<Object> list = (ArrayList<Object>) request.getAttribute("list"); 
	for(int i = 0; i < list.size(); i++) {
		if(i == 0) {
			String prof = (String) list.get(i);
	%>
		<div><%= prof %></div>
			
	<%		
		} else if(i == 1) {
			CourseDto bean = (CourseDto) list.get(i);
	%>
		<div><%= bean.getCno() %>, <%= bean.getCtitle() %>, <%= bean.getCbegin() %></div>
			
	<%
		} else {
			StdDto bean = (StdDto) list.get(i);
				
	%>
		<div class="student">
			<span><%= bean.getId() %>, <%= bean.getSno() %>, <%= bean.getName() %>, <%= bean.getTel() %>, <%= bean.getLvl() %></span>
			<label for="<%= bean.getSno() %>"></label>
			<input type="checkbox" id="<%= bean.getSno() %>" name="<%= bean.getSno() %>" <% if("L03".equals(bean.getLvl())) out.print("checked=true"); %>>
		</div>
	<%
		}
	}
	%>
	</div> --%>
	<%
	ArrayList<Object> list = (ArrayList<Object>) request.getAttribute("list");
	String prof = (String) list.get(0);
	CourseDto course = (CourseDto) list.get(1);
	%>
	<table>
		<tr>
			<th>강좌번호</th>
			<th>강좌</th>
			<th>강사</th>
			<th>개강일</th>
			<th>정원</th>
			<th>배정인원</th>
			<th>신청인원</th>
		</tr>
		<tr>
			<td><%= course.getCno() %></td>
			<td><%= course.getCtitle() %></td>
			<td><%= prof %></td>
			<td><%= course.getCbegin() %></td>
			<td>30</td>
			<td id="assigned_count">0</td>
			<td id="registered_count"><%= list.size() - 2 %></td>
		</tr>
	</table>
	<table>
		<tr>
			<th>학생번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>상태</th>
			<th>배정</th>
		</tr>
		<%
		for(int i = 2; i < list.size(); i++) {
			StdDto student = (StdDto) list.get(i);
		%>
		<tr class="student">
			<td><%= student.getSno() %></td>
			<td><%= student.getId() %></td>
			<td><%= student.getName() %></td>
			<td><%= student.getTel() %></td>
			<td><%= student.getLvl() %></td>
			<td>
				<label for="<%= student.getSno() %>"></label>
				<input type="checkbox" id="<%= student.getSno() %>" name="<%= student.getSno() %>" <% if("L03".equals(student.getLvl())) out.print("checked=true"); %>>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<div>
		<span id="resultassign"></span>
	</div>
	<div>
		<button type="button" id="btnassign">저장</button>
		<button type="button" id="back" onclick="location.href = '/jeju/lms/courses/reglist.bit'">목록</button>
	</div>
<%@ include file="template/footer.jspf" %>
</body>
</html>