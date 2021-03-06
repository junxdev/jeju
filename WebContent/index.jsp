<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>index</title>
	<%@ include file="template/head.jspf" %>
	<link rel="stylesheet" type="text/css" href="<%=root %>/css/jquery.bxslider.min.css"/>
	<style type="text/css">
	#content{
		background-image: url(img/bg3.jpg);
		background-size: 100%;
	    background-position: center;
	    background-repeat:no-repeat;
	}
	
	</style>
	<script type="text/javascript" src="<%=root %>/js/jquery.bxslider.min.js"></script>
	<script type="text/javascript">
	
	$(document).ready(function(){
	
		$('.slider').bxSlider({
			mode: 'fade',
			auto: true,			
			controls : false,	
			pager: false,		
			pause: 3000,
			autoDelay: 0,	
			slideWidth: 800,
			speed: 500
					
		});
	 	
		$(".bx-start").hide();	
	});
	</script>
</head>
<body>

<%@ include file="template/header.jspf" %>
<%@ include file="template/menu.jspf" %>

<div class="slider">
        <div><img src="<%=root %>/img/ad01.jpg" /></div>
        <div><img src="<%=root %>/img/ad02.jpg" /></div>
        <div><img src="<%=root %>/img/ad03.jpg" /></div>
</div>
		
<%@ include file="template/footer.jspf" %>
</body>
</html>