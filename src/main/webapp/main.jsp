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
        
        <nav class="navbar">
            <ul>
                <li><a href="#best-sellers">Best Sellers</a></li>
                <li><a href="#new-arrivals">New Arrivals</a></li>
                <li><a href="product.jsp">Catalog</a></li>
            </ul>
        </nav>

        <div class="nav-icons">
            <a href="cart.jsp">
                <img src="images/shopping-bag.png" alt="Cart" class="cart-icon">
            </a>
            <div class="account-menu">
                <img src="images/user.png" alt="Account" class="account-icon" onmouseover="showWelcomeMessage()" onmouseout="hideWelcomeMessage()">
                <div class="dropdown-content">
                    <div >
                        <% if (session.getAttribute("username") != null) { %>
                            Welcome back, <%= session.getAttribute("username") %>
                        <% } else { %>
                            Error | Log In
                        <% } %>
                    </div>
                    <a href="settings.jsp">Settings</a>
                    <a href="logout.jsp">Logout</a>
                </div>
            </div>
        </div>
    </header>

    <div class="hero">
        <h1>Welcome to IoTBay</h1>
        <p>Your premier source for the latest IoT devices.</p>
        <a href="product.jsp" class="hero-button">Shop Now</a>
    </div>

    <!-- Additional sections of the page -->

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
            Icons created by <a href="https://www.flaticon.com/free-icons/user" title="user icons" target="_blank">Freepik - Flaticon</a>
            <span>|</span>
            <a href="https://www.flaticon.com/free-icons/basket" title="basket icons" target="_blank">Basket icons created by Karacis - Flaticon</a>
        </p>
    </footer>

    <script>
        function showWelcomeMessage() {
            document.getElementById('welcome-message').style.display = 'block';
        }

        function hideWelcomeMessage() {
            document.getElementById('welcome-message').style.display = 'none';
        }
    </script>
</body>
</html>
