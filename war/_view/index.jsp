<!DOCTYPE html>

<html>
	<head>
		<title>Index view</title>
	</head>

	<body>
		<h3>Please make a selection</h3>
		
		<form action="${pageContext.servletContext.contextPath}/addNumbers" method="get">
			<input name="addNumbers" type="submit" value="Add Numbers" />
		</form>
		<br/>
		<form action="${pageContext.servletContext.contextPath}/multiplyNumbers" method="get">
			<input name="multiplyNumbers" type="submit" value="Multiply Numbers" />
		</form>
		<br/>
		<form action="${pageContext.servletContext.contextPath}/guessingGame" method="get">
			<input name="guessingGame" type="submit" value="Guessing Game" />
		</form>
	</body>
</html>
