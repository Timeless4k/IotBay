<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Home</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <jsp:include page="/ConnServlet" />
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='index.jsp'">
        
        <nav class="navbar">
            <ul>
                <li><a href="#about_us">About Us</a></li>
                <li><a href="#FAQs">FAQs</a></li>
                <li><a href="/ProductNameSearchServlet?SearchType=ProductName&SearchQuery=">Catalog</a></li>
            </ul>
        </nav>

        <div class="nav-icons">
            <a href="orders.jsp">
                <img src="images/shopping-bag.png" alt="Cart" class="cart-icon">
            </a>
            <div class="account-menu">
                <img src="images/user.png" alt="Account" class="account-icon" onmouseover="showWelcomeMessage()" onmouseout="hideWelcomeMessage()">
                <div class="dropdown-content">
                    <a href="login.jsp">Login</a>
                    <a href="register.jsp">Register</a>
                </div>
            </div>
        </div>
    </header>
    
    <div class="hero">
        <h1>Welcome to IoTBay</h1>
        <p>Your premier source for the latest IoT devices.</p>
        <a href="login.jsp" class="hero-button">Shop Now</a>
    </div>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer>
</body>
</html>
