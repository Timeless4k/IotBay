<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Main</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" type="image/png" href="images/Logo.webp">
</head>
<body class="light-mode">

    <div class="top-banner">
        Free Shipping Over $49 (AUD)
    </div>

    <header>
        <img src="images/Logo.webp" alt="IoTBay Logo" class="logo" onclick="window.location='index.jsp'">

        <div class="search-bar">
            <input type="text" placeholder="Search products...">
        </div>

        <nav class="navbar">
            <a href="product.jsp">Catalog</a>
            <a href="cart.jsp">
                <img src="images/shopping-bag.png" alt="Cart" class="cart-icon">
            </a>
            <div class="account-menu">
                <img src="images/user.png" alt="Account" class="account-icon">
                <div class="dropdown-content">
                    <a href="main.jsp"> Settings</a>
                    <a href="logout.jsp">Logout</a>
                </div>
            </div>
        </nav>
    </header>

    <div class="hero">
        <h1>Welcome to IoTBay</h1>
        <p>Your premier source for the latest IoT devices.</p>
        <a href="product.jsp" class="hero-button">Shop Now</a>
    </div>

    <div class="newsletter-signup">
        <h2>Stay Updated!</h2>
        <p>Subscribe to our newsletter for the latest updates on IoT devices.</p>
        <form action="subscribeNewsletter.jsp" method="post">
            <input type="email" name="email" placeholder="Enter your email address" required>
            <button type="submit">Subscribe</button>
        </form>
    </div>


    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
        <p class="attribution">
            <a href="https://www.flaticon.com/free-icons/user" title="user icons" target="_blank">Icons created by Freepik - Flaticon</a>
            <span>|</span>
            <a href="https://www.flaticon.com/free-icons/basket" title="basket icons" target="_blank">Basket icons created by Karacis - Flaticon</a>
        </p>
    </footer>
    

</body>
</html>
