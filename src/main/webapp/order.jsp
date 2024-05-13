<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Order Details</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" type="image/png" href="images/Logo.webp">
</head>
<body class="light-mode">
    <div class="top-banner">
        Free Shipping Over $49 (AUD)
    </div>

    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        
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
                    <div>${user.firstName} ${user.lastName}</div>
                    <a href="account.jsp">Account</a>
                    <a href="order.jsp">Your Orders</a>
                    <a href="logout.jsp">Logout</a>
                </div>
            </div>
        </div>
    </header>

    <div class="container">
        <h1>Order Details</h1>
        <table class="order-summary">
            <tr>
                <th>Order ID</th>
                <td>${order.orderID}</td>
            </tr>
            <tr>
                <th>Order Date</th>
                <td><fmt:formatDate value="${order.orderDate}" pattern="dd MMMM yyyy"/></td>
            </tr>
            <tr>
                <th>Status</th>
                <td>${order.orderStatus}</td>
            </tr>
            <tr>
                <th>Total Amount</th>
                <td>$${order.totalAmount}</td>
            </tr>
        </table>

        <c:if test="${not empty orderItems}">
            <table class="order-items">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${orderItems}">
                        <tr>
                            <td>${item.product.name}</td>
                            <td>${item.quantity}</td>
                            <td>${item.product.price}</td>
                            <td>${item.quantity * item.product.price}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
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
