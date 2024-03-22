<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Form</title>
    <!-- Link to style.css -->
    <link rel="stylesheet" type="text/css" href="register.css">
</head>
<body>
    <form action="register" method="post">
        <div>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <label for="firstName">First name:</label>
            <input type="text" id="firstName" name="firstName">
        </div>
        <div>
            <label for="middleName">Middle name:</label>
            <input type="text" id="middleName" name="middleName">
        </div>
        <div>
            <label for="lastName">Last name:</label>
            <input type="text" id="lastName" name="lastName">
        </div>
        <div>
            <label for="birthDate">Birth date:</label>
            <input type="date" id="birthDate" name="birthDate">
        </div>
        <div>
            <label for="mobilePhone">Mobile phone:</label>
            <input type="tel" id="mobilePhone" name="mobilePhone">
        </div>
        <div>
            <input type="submit" value="Register">
        </div>
    </form>
    <div>
        <a href="login.jsp">Return to login</a>
    </div>
    <!-- You can add language selection and other footer elements here -->
</body>
</html>
