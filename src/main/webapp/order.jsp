<%@ page import="java.util.List" %>
<%@ page import="java.util.*, model.user, model.payment"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Order Details</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">

    <link rel="icon" type="image/png" href="images/Logo.webp">
</head>
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="/account.jsp#profile">Profile</a></li>
                <li><a href="PaymentHistoryServlet">Payment History</a></li>
                <li><a href="order.jsp">Order History</a></li>
                <li><a href="shipmentDetails.jsp">Shipment Details</a></li>
                <li><a href="/account.jsp#access">Access Logs</a></li>
                <c:if test="${user.uType == 'Admin'}">
                    <li><a href=UserServlet?action=displayAll>User Management</a></li>
                </c:if>
                <c:if test="${user.uType == 'Employee'}">
                    <li><a href=productmanagement.jsp>Product Management</a></li>
                </c:if>
                <form action="logout" method="post">
                    <input type="submit" value="Logout">
                </form>
            </ul>
        </nav>
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

</body>
</html>
