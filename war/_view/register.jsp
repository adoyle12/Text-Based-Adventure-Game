<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Register</title>
		<style type="text/css">
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
			.container {
				position: relative;
				margin: 0 auto;
			}
			.container p.register {
				position: absolute;
				background: rgb(0, 0, 0);
				background: rgba(0, 0, 0, 0.5);
				color: #f1f1f1;
				width: 100%;
				text-align: center;
				font-size: 40px;
			}
			.container .content {
				position: fixed;
				bottom: 0;
				color: #f1f1f1;
				width: 100%;
				padding: 40px;
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
			.error {
				color: red;
			}
			body {
				background-color: black;
			}
			* {
				box-sizing: border-box;
			}
			.success {
					color: blue;
					font-weight: bold;
				}
			.success_title {
				color: darkblue;
				font-style: italic;
				font-weight: bold;			
			}
			td.label {
				text-align: center;
			}
		</style>
	</head>
	
	<body>
		<div id="bg">
			<img src="labyrinth.jpg" alt="">
		</div>
		<div class="container">
			<p class="register">What?!<br>You've never been here before?<br>No problem!<br>Just create a new username and password to
				start your adventure!</p>
			<div class="content">
			<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
			</c:if>
			<c:if test="${! empty successMessage}">
				<div class="success">Successfully added <span class="success_title">${successMessage}</span> to Library</div>
			</c:if>
			<form action="${pageContext.servletContext.contextPath}/register" method="post">
				<table>
					<tr>
						<td class="label">User Name:</td>
						<td><input type="text" name="username" size="12" value="${username}" /></td>
					</tr>
					<tr>
						<td class="label">Password:</td>
						<td><input type="password" name="password" size="12" value="${password}" /></td>
					</tr>
				</table>
				<br>
				<input type="Submit" name="submitinsertuser" value="Add new user">
			</form>
			<br>
			<form action="${pageContext.servletContext.contextPath}/login" method="post">
				<input type="Button" name="submithome" value="Login page"
					onclick="window.location = 'http://localhost:8081/tbag/login';">
			</form>
			</div>
		</div>
	</body>
</html>