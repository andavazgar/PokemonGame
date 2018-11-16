<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register / Login</title>
</head>
<body>
	<h1>Welcome to the Pokemon Game</h1>

	<h2>Register</h2>
	<form action="Register" method="post">
		<label for="registerUsername">Username:</label><input type="text" id="registerUsername" name="user"><br>
		<label for="registerPassword">Password:</label><input type="password" id="registerPassword" name="pass"><br>
		<input type="submit">
	</form>
	
	<h2>Login</h2>
	<form action="Login" method="post">
		<label for="loginUsername">Username:</label><input type="text" id="loginUsername" name="user"><br>
		<label for="loginPassword">Password:</label><input type="password" id="loginPassword" name="pass"><br>
		<input type="submit">
	</form>
</body>
</html>