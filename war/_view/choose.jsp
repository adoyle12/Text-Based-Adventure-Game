<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<title>Choose Game</title>
		<style type="text/css">
			.error {
				color: red;
			}
			body {
				background-color: black;
			}
			* {
				box-sizing: border-box;
			}
			.container {
				position: relative;
				margin: 0 auto;
			}
			td.label {
				text-align: center;
			}
			#bg {
				position: fixed;
				top: -50%;
				left: -50%;
				width: 200%;
				height: 200%;
			}	
			#bg img {
				position: absolute;
				top: 0;
				left: 0;
				right: 0;
				bottom: 0;
				margin: auto;
				min-width: 50%;
				min-height: 50%;
			}
			.title {
				position: fixed;
				color: #f1f1f1;
				width: 100%;
				text-align: center;
				line-height: 0.1;
				text-shadow: 2px 1px darkgreen;
			}
			p.gamename {
				color: #f1f1f1;
				font-size: 80px;
				text-shadow: 3px 2px green;
			}
			.container p.intro {
				position: absolute;
				top: 200px;
				background: rgb(0, 0, 0);
				background: rgba(0, 0, 0, 0.5);
				color: #f1f1f1;
				width: 100%;
				text-align: center;
				font-size: 30px;
			}
			.container .content {
				position: fixed;
				bottom: 0;
				color: #f1f1f1;
				width: 100%;
				padding: 60px;
			}
			input[type=text] {
				margin: auto;
			}		
			input[type=text]:focus {
				background-color: lightgreen;
			}	
			input[type=password] {
				margin: auto;
			}	
			input[type=password]:focus {
				background-color: lightgreen;
			}
		</style>
	</head>
	
	<body>
		<%
			if(session.getAttribute("username") == null){
				response.sendRedirect("login.jsp");
			}
		%>
		<div id="bg">
			<img src="labyrinth.jpg" alt="">
		</div>
		<div class="container">
			<div class="title">
				<p class="gamename">Choose Your Game</p>
			</div>
			<p class="intro">This is where you would pick load/new game</p>
			<div class="content">
			<form action="${pageContext.servletContext.contextPath}/choose" method="post">
				
			</form>
			<div style="position: absolute; left: 35%; font-size: 24px; top: 60%; ">	
				<form action="${pageContext.servletContext.contextPath}/gameplay" method="get">
					<input type="Button" name="gameplay" value="Continue"
					onclick="window.location = 'http://localhost:8081/tbag/gameplay';">
				</form>
			</div>
			
			<div style="position: absolute; right: 35%;  font-size: 24px; top: 60%; ">
				<form action="${pageContext.servletContext.contextPath}/choose" method="post">
					<input type="Submit" name="new" value="New Game">
				</form>
			</div>
			
			</div>
		</div>
	</body>
</html>