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
        
        <form action="login" method="post">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <div style="margin-top: 5px;">
                </div>
            </div>
            <div>
                <input type="submit" value="Login">
            </div>
        </form>
        <hr>
        <div style="text-align: center; margin-top: 20px;">
            <p>Not an IoTBay customer yet? <a href="register.jsp">Register now.</a></p>
            <form action="/GuestUserServlet" method="post">
                <input type="submit" value="Guest Access">
            </form>
        </div>
    </div>
</div>

<script>
    function redirectToVerification() {
        // Here you would normally validate credentials
        // If valid, redirect to the verification page
        window.location.href = 'verification.jsp'; // Change 'verification.jsp' to the actual path if different
        return false; // Prevent the default form submission
    }
</script>

</body>

</html>