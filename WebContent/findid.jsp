<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비트캠프 제주지점 : 회원가입</title>
<%@ include file="template/head.jspf" %>
<style type="text/css">
	.default_input {
	}
	
	.change_input {
		border-color: red;
		border-radius: 3px;
		border-style: solid;
	}
	
	.change_check {
		outline-color: red;
		outline-style: auto;
	}
</style>
<script type="text/javascript">
	var checkFindId;
	$(document).ready(function() {
		// * * * * * 블록 변수 * * * * *
		var tryfindid = false;
		// * * * * * * * * * * * * * * *
		
		// * * * * * 각 필드별 에러메시지 출력 함수 * * * * *
		var err_message = function(field, message) {
			$('#err' + field).text(message);	
			$('#' + field).addClass('change_input');	
			tryfindid = true;	// true면 post 실행하지 않음
		}
		// * * * * * * * * * * * * * * * * * * * * * * * * *
		
		checkFindId = function() {
			// * * * * * 에러 메시지 및 값 초기화 * * * * *
			$('span').text('');
			$('input').removeClass('change_input').removeClass('change_check');
			tryfindid = false;
			
			var name = $('#name').val();
			var tel = $('#tel').val();
			var email = $('#email').val();
			// * * * * * * * * * * * * * * * * * * * * * * * * *
			
			// * * * * * 필드 별 유효성 검증 및 에러메시지 출력 함수 실행 * * * * *
			if(name == '') {	
				err_message('name', '이름을 입력하세요');	
			} else if(name.length > 6) {
				err_message('name', '6자 이하로 입력하세요');
			}
			
			if(tel == '') {	
				err_message('tel', '전화번호를 입력하세요');	
			} else if(tel.length > 11) {
				err_message('tel', '11자리 이하의 올바른 전화번호를 입력하세요');	
			} 
			
			if(email == '') {
				err_message('email', '이메일을 입력하세요');
			} else if(email.length > 30) { 
				err_message('email', '30자 이하의 이메일을 입력하세요');
			} else {
				var x = email.match(/(?:[A-Za-z0-9]+)\@(?:[A-Za-z0-9]+)\.(?:[A-Za-z0-9]+)/);
				console.log(x);
				if(x) {
					if(x != email) {
						err_message('email', '올바른 형식의 이메일 주소를 입력하세요');
					}
				} else {
					err_message('email', '올바른 형식의 이메일 주소를 입력하세요');
				}
			}
			// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
			
			// * * * * * tryfindid이 true면 오류 필드 중 가장 위 필드를 포커스하고 중지 * * * * *
			if(tryfindid) {
				$('span').each(function(i, e) {
                    if($(e).text()) {	
                    	$(e).prev('input').focus();	
                    	return false;
                    };
                });
				return false;
			}
			// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		};
	});
</script>
</head>
<body>
	<%@ include file="template/header.jspf" %>
	<%@ include file="template/menu.jspf" %>
		<div>
			<h2>아이디 찾기</h2>
			<p>회원가입 시 입력했던 정보들을 입력하세요</p>
		</div>
		<div>
			<form method="post" onsubmit="return checkFindId()">
				<div>
					<label for="name">이름</label>
					<input type="text" id="name" class="default_input" name="name"/>
					<span id="errname"></span>
				</div>
				<div>
					<label for="tel">전화번호</label>
					<input type="text" id="tel" class="default_input" name="tel"/>
					<span id="errtel"></span>
				</div>
				<div>
					<label for="email">이메일</label>
					<input type="text" id="email" class="default_input" name="email"/>
					<span id="erremail"></span>
				</div>
				<div>
					<button type="submit" id="btnfindid">아이디 찾기</button>
				</div>
			</form>
		</div>
	<%@ include file="template/footer.jspf" %>
</body>
</html>