<%@ page import="java.util.List" %>
<%@ page import="java.util.*, model.user, model.payment"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Shipment Details Page</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">

</head>
<body>
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
                <li><a href="logout.jsp">Logout</a></li>
            </ul>
        </nav>
    </header>
    <div class="container">
    <h1>Shipment Details</h1>
    

    <hr>
    <br><br>

    <div class="shipment-info">



    </div>
    </div>
    <!-- <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer> -->
</body>
</html>
