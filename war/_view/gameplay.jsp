<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Game Play</title>
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
			.container .content {
				position: fixed;
				bottom: 0;
				background: rgb(0, 0, 0);
				background: rgba(0, 0, 0, 0.5);
				color: #f1f1f1;
				width: 100%;
				padding: 60px;
			}
			span.text {
				display: block;
				padding: 10px;
				text-align: left;
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
				<p class="gamename">The Escape of the Minotaur</p>
			</div>
			<div class="content">
				<c:if test="${! empty errorMessage}">
					<div class="error">${errorMessage}</div>
				</c:if>
				<form action="${pageContext.servletContext.contextPath}/gameplay" method="post">
				<div id="output" style="overflow-y:scroll; height: 400px; width: 500px; margin: auto;">
					<table>
						<c:forEach items="${gameplay.output}" var="string">
							<tr>
								<td>${string}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div style="width: 500px; margin: auto;">
					<table>
						<tr>
							<td><span class='text'>Input:<input type="text" name="input" size="12" /></span></td>
						</tr>
					</table>
				</div>
				</form>
			</div>
		</div>
		
		<script>
			document.addEventListener('DOMContentLoaded', function scrollToBottom()
			{
				var element = document.querySelector('#output');
				element.scrollTop = element.scrollHeight;
			});
		</script>
	</body>
</html>