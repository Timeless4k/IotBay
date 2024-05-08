<%@ page import="model.*"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Login to your account</title>
    <link rel="stylesheet" type="text/css" href="register.css">
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='index.jsp'">
        <div class="login-button">
            <a href="index.jsp" class="button-link">Back to Page</a>
        </div>
    </header>

<div class="center-screen">
    <div class="form-container">
        <div class="header">
            <h1>Login to Your Account</h1>
        </div>
        
        <!-- Modified form action to point to the LoginServlet -->
        <form action="LoginServlet" method="post">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <div style="margin-top: 5px;">
                    <a href="forgotPassword.jsp" style="font-size: 14px; color: #008CBA; text-decoration: none;">Forgot password?</a>
                </div>
            </div>
            <div>
                <input type="submit" value="Login">
            </div>
        </form>
        <hr>
        <div style="text-align: center; margin-top: 20px;">
            <p>Not an IoTBay customer yet? <a href="register.jsp">Register now.</a></p>
        </div>
    </div>
</div>

</body>

</html>
