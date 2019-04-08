<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Register</title>
		<style type="text/css">
		.error {
			color: red;
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
			<input type="Submit" name="submitinsertuser" value="Add new user">
	</form>
	<br>
		<form action="${pageContext.servletContext.contextPath}/login" method="post">
			<input type="Submit" name="submithome" value="Login page">
		</form>	
	</body>
</html>