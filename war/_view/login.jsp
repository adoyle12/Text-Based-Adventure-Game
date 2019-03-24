<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Login</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: center;
		}
		</style>
	</head>
	<body>
		<div class="body">
			<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
			</c:if>
			<form action="${pageContext.servletContext.contextPath}/login" method="post">
				<table>
					<tr>
						<td class="label">User Name:</td>
						<td><input type="text" name="username" size="12" value="${login.username}" /></td>
					</tr>
					<tr>
						<td class="label">Password:</td>
						<td><input type="password" name="password" size="12" value="${login.password}" /></td>
					</tr>
				</table>
				<input type="Submit" name="submit" value="Enter">
			</form>
			<c:if test="${login.credentials}">
				<c:redirect url= "http://localhost:8081/tbag/gameplay">
				</c:redirect>
			</c:if>
		</div>
	</body>
</html>