<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.order" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Order Details</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" type="image/png" href="images/Logo.webp">
</head>
<body>
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="product.jsp">Catalog</a></li>
                <li><a href="account.jsp">Account</a></li>
                <li><a href="logout.jsp">Logout</a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <h1>Order Details</h1>
        <c:forEach var="order" items="${orders}">
            <div class="order-detail">
                <p>Order ID: ${order.orderID}</p>
                <p>Date: <fmt:formatDate value="${order.orderDate}" pattern="dd MMMM yyyy"/></p>
                <p>Status: ${order.orderStatus}</p>
                <p>Total Amount: ${order.totalAmount}</p>
                <a href="OrderServlet?action=cancel&orderID=${order.orderID}" class="button">Cancel Order</a>
            </div>
        </c:forEach>
    </div>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer>
</body>
</html>
