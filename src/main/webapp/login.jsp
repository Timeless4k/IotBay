<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

<head>
    <meta charset="UTF-8">
    <title>Login to your account</title>
    <link rel="stylesheet" type="text/css" href="register.css">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="login-button">
    <a href="register.jsp" class="button-link">Return to registration</a>
</div>

<div class="center-screen">
    <div class="form-container">
        <div class="header">
            <h1>Login to Your Account</h1>
        </div>
        
        <form action="login" method="post">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <!-- Adding the 'Forgot password?' link -->
                <div style="margin-top: 5px;">
                    <a href="forgotPassword.jsp" style="font-size: 14px; color: #008CBA; text-decoration: none;">Forgot password?</a>
                </div>
            </div>
            <div>
                <input type="submit" value="Login">
            </div>
        </form>
    </div>
</div>

</body>

</html>