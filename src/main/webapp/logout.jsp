<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Logout | IoTBay</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <% session.invalidate(); %>
    <header class="header-logout">
        <div class="logo-container-logout">
            <img src="images/Logo.png" alt="IoTBay Logo" onclick="window.location='index.jsp'">
        </div>
        <div class="login-container-logout">
            <a href="login.jsp" class="login-button">Log In</a>
        </div>
    </header>

    <main class="logout-main">
        <div class="logout-container">
            <h1>Logout Successful</h1>
            <p>You have successfully logged out. Click below to return to the homepage.</p>
            <a href="index.jsp" class="button">Go to Home</a>
        </div>
    </main>

    <footer class="footer-logout">
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer>
</body>
</html>
