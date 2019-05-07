<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<title>Login</title>
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
		<div id="bg">
			<img src="labyrinth.jpg" alt="">
		</div>
		<div class="container">
			<div class="title">
				<p class="gamename">The Escape of the Minotaur</p>
				<p style="font-size: 30px;">A Text-Based Adventure Game</p>
			</div>
			<p class="intro">You are the minotaur at the center of the labyrinth.<br>
				Having been trapped here for ages, forced to accept the challenge of any hero who dares to find you,<br>
				a whimsical goddess takes pity on you and offers you a chance at freedom.<br>
				Now you must find your way out of the labyrinth whilst searching for stolen treasures<br>
				or remain a prisoner to this maze forever.<br>
				Are you up to the challenge?</p>
			<div class="content">
			<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
			</c:if>
			<form action="${pageContext.servletContext.contextPath}/login" method="post">
				<table>
					<tr>
						<td class="label">User Name:</td>
						<td><input type="text" name="username" size="12" /></td>
					</tr>
					<tr>
						<td class="label">Password:</td>
						<td><input type="password" name="password" size="12" /></td>
					</tr>
				</table>
				<br>
				<input type="Submit" name="submit" value="Login">
			</form>
			<br>
			<form action="${pageContext.servletContext.contextPath}/register" method="post">
				<input type="Button" name="register" value="Register"
				onclick="window.location = 'http://localhost:8081/tbag/register';">
			</form>
			<c:if test="${login.credentials}">
				<c:redirect url= "http://localhost:8081/tbag/gameplay"></c:redirect>
			</c:if>
			</div>
		</div>
	</body>
</html>