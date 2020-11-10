<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비트캠프 제주지점 : 회원가입</title>
<%@ include file="template/head.jspf" %>
<style type="text/css">
	* {
		word-break: keep-all;
	}

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
	.modal {
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		position: fixed;
	}
	.modal_layer {
		background-color: rgba(0, 0, 0, 0.3);
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		position: absolute;
	}
	.modal_content {
		background-color: white;
		text-align: center;
		padding: 5px 50px;
		width: 30%;
		height: 50%;
		position: relative;
		border-radius: 10px;
		box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
	}
	.modal_content > p {
		width: 100%;
		height: 60%;
		overflow-y: auto;
		align-items: center;
		margin: 10px 0px;
	}
	.hidden {
		display: none;
	}
	
	h2 {
	    text-align: left;
	    width: 40%;
	    margin: auto;
	    margin-top: 1em;
	}
	
	.input-form {
	    text-align: left;
	    width: 40%;
	    margin: auto;
	}
	
	.input-box {
		margin-top: 1em;
	}
	
	.input-box label {
		font-size: 0.7em;
		left: 10px;
	}
	
	.input-box p {
		font-size: 0.7em;
		left: 10px;
		margin-bottom: 0;
	}
	
	.input-box span {
		font-size: 0.7em;
		color: red;
	}
	
	.input-box input {
		width: 50%;
	}
	
	#legal {
		width: inherit;
	}
	.input-form button {
		width: 100%;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		// * * * * * 블록 변수 * * * * *
		var tryjoin = false;
		// * * * * * * * * * * * * * * *
		
		// * * * * * 이용약관 및 개인정보 모달 제어 * * * * *
		var openModal = function() {
			if(document.querySelector('#legal').checked == true) {
				document.querySelector('.modal').classList.remove('hidden');
			}
		}
		var closeModal = function(ev) {
			document.querySelector('.modal').classList.add('hidden');
			ev.stopPropagation();
		}
		document.getElementById('legal').addEventListener('click', openModal);
		document.querySelector('.modal_layer').addEventListener('click', closeModal);
		document.querySelector('.close_legal').addEventListener('click', closeModal);
		// * * * * * * * * * * * * * * * * * * * * * * * * *
		
		// * * * * * 각 필드별 에러메시지 출력 함수 * * * * *
		var err_message = function(field, message) {
			$('#err' + field).text(message);	
			$('#' + field).addClass('change_input').addClass('change_check');	
			tryjoin = true;	// true면 post 실행하지 않음
		}
		// * * * * * * * * * * * * * * * * * * * * * * * * *
		
		$('#btnjoin').on('click', function() {
			// * * * * * 에러 메시지 및 값 초기화 * * * * *
			$('span').text('');
			$('input').removeClass('change_input').removeClass('change_check');
			tryjoin = false;
			
			var id = $('#id').val();
			var pw = $('#pw').val();
			var pwcheck = $('#pwcheck').val();
			var answer = $('#answer').val();
			var name = $('#name').val();
			var tel = $('#tel').val();
			var email = $('#email').val();
			var legal = $('#legal').prop('checked');
			// * * * * * * * * * * * * * * * * * * * * * * * * *
			
			// * * * * * 필드 별 유효성 검증 및 에러메시지 출력 함수 실행 * * * * *
			if(id == '') {	
				err_message('id', '아이디를 입력하세요');	
			} else if(id.length < 5) {
					err_message('id', '5자 이상의 ID를 입력하세요')
			} else if(id.length > 20) {
					err_message('id', '20자 이하의 ID를 입력하세요')
			} else {	
				if(id.search(/[^a-zA-Z0-9]/) + 1) {
					err_message('id', '영문 또는 숫자로만 이루어진 ID를 입력하세요')
				}
			}
			
			if(pw == '') {	
				err_message('pw', '비밀번호를 입력하세요');
			} else {
				if(pw.length < 8) {
					err_message('pw', '8자 이상의 비밀번호를 입력해주세요');
				} else if(pw.length > 20){
					err_message('pw', '20자 이하의 비밀번호를 입력해주세요');
				} else {
					if(!((pw.search(/[a-zA-z]/) + 1) && 
							(pw.search(/[0-9]/) + 1) && 
							(pw.search(/[^a-zA-z0-9]/) + 1))) {
						err_message('pw', '영문, 숫자, 특수문자로 조합된 비밀번호를 입력하세요');
					}
				}
			}
			
			if(pwcheck == '' || pw != pwcheck) {	
				err_message('pwcheck', '동일한 비밀번호를 입력하세요');	
			}
			
			if(answer == '') {	
				err_message('answer', '비밀번호 찾기 답을 입력하세요');
			} else if(answer.length > 10) {
				err_message('answer', '10자 이하로 입력하세요');
			}
			
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
				var x = email.match(/(?:[A-Za-z0-9]+)\@(?:[A-Za-z0-9\.]+)(?:[A-Za-z]+)/);
				console.log(x);
				if(x) {
					if(x != email) {
						err_message('email', '올바른 형식의 이메일 주소를 입력하세요');
					}
				} else {
					err_message('email', '올바른 형식의 이메일 주소를 입력하세요');
				}
			}
			
			if(legal == false) err_message('legal', '이용약관 및 개인정보 처리방침에 동의해주세요'); 
			// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
			
			// * * * * * tryjoin이 true면 오류 필드 중 가장 위 필드를 포커스하고 중지 * * * * *
			if(tryjoin) {
				$('span').each(function(i, e) {
                    if($(e).text()) {	$(e).prev('input').focus();	return false;		};
                });
				return;
			}
			// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
			
			console.log(id + ', ' + pw);
			
			// * * * * * tryjoin이 false면 ajax 실행 * * * * *
			$.ajax({
				type: 'post',
				data: 'id=' + id + '&pw=' + pw + '&answer=' + answer + '&name=' + name + '&tel=' + tel + '&email=' + email,
				url: '/jeju/login/join.bit',
				success: function(value) {
					console.log(value);
					if(value == 'existed id') {
						$('#errid').text('이미 사용 중인 ID입니다. 다른 ID를 입력하세요');
					}
					if(value == 'join') {
						location.href = 'welcome.bit';
					}
				}
			});
			// * * * * * * * * * * * * * * * * * * * * * * * * *
			
		});
	});
</script>
</head>
<body>
	<%@ include file="template/header.jspf" %>
	<%@ include file="template/menu.jspf" %>
		<div>
			<h2>회원가입</h2>
		</div>
		<div class="input-form">
			<div class="input-box">
				<div>
					<label for="id">아이디 (영문, 숫자 조합 5자 이상)</label>
				</div>
				<div>
					<input type="text" id="id" class="default_input" name="id"/>
					<span id="errid"></span>
				</div>
			</div>
			<div class="input-box">
				<div>
					<label for="pw">비밀번호 (영문, 숫자, 특수문자 포함 8자 이상)</label>
				</div>
				<div>
					<input type="password" id="pw" class="default_input" name="pw"/>
					<span id="errpw"></span>
				</div>
			</div>
			<div class="input-box">
				<div>
					<label for="pwcheck">비밀번호 확인</label>
				</div>
				<div>
					<input type="password" id="pwcheck" class="default_input" name="pwcheck"/>
					<span id="errpwcheck"></span>
				</div>
			</div>
			<div class="input-box">
				<div>
					<p>다음 비밀번호 찾기 질문에 답해주세요: 가장 좋아하는 동물은?</p>
					<label for="answer">비밀번호 찾기 답 (10자 이하)</label>
				</div>
				<div>
					<input type="text" id="answer" class="default_input" name="answer"/>
					<span id="erranswer"></span>
				</div>
			</div>
			<div class="input-box">
				<div>
					<label for="name">이름</label>
				</div>
				<div>
					<input type="text" id="name" class="default_input" name="name"/>
					<span id="errname"></span>
				</div>
			</div>
			<div class="input-box">
				<div>
					<label for="tel">전화번호</label>
				</div>
				<div>
					<input type="text" id="tel" class="default_input" name="tel"/>
					<span id="errtel"></span>
				</div>
			</div>
			<div class="input-box">
				<div>
					<label for="email">이메일</label>
				</div>
				<div>
					<input type="text" id="email" class="default_input" name="email"/>
					<span id="erremail"></span>
				</div>
			</div>
			<div class="input-box">
				<div>
					<input type="checkbox" id="legal" class="default_input" name="legal">
					<label for="legal"> 이용약관 및 개인정보 처리방침에 동의합니다</label>
				</div>
				<div>
					<span id="errlegal"></span>
				</div>
			</div>
			<div class="input-box">
				<button type="button" id="btnjoin">가입하기</button>
			</div>
		</div>
		<div class="modal hidden">
			<div class="modal_layer"></div>
			<div class="modal_content">
				<h3>이용약관 및 개인정보 처리방침</h3>
				<p class="legal_content">Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
				sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
				 Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
				 Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
				 Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
				<button class="close_legal" type="button">닫기</button>
			</div>
		</div>
	<%@ include file="template/footer.jspf" %>
</body>
</html>