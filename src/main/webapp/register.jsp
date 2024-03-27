<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register your account</title>
    <!-- Link to your CSS file -->
    <link rel="stylesheet" type="text/css" href="register.css">
</head>
<body>

<div class="login-button">
    <a href="login.jsp" class="button-link">Return to login</a>
</div>

<div class="center-screen">
    <div class="form-container">
        <div class="header">
            <h1>Register Your Account</h1>
        </div>
        
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
    </div>
</div>
<!-- Additional footer elements here -->

</body>
</html>
