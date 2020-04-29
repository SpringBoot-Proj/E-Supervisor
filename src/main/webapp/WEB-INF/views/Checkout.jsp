<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="paynow" method="post">
		Card : <input type="text" name="cc"/><br>
		Exp Date: <input type="text" name="dt"><br>
		Amount: <input type="text" name="amount"><br>
	
		<input type="submit" value="Pay"> 
	</form>
</body>
</html>