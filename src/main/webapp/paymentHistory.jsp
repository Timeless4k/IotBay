<%@ page import="java.util.List" %>
<%@ page import="java.util.*, model.user, model.payment"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Payment History</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">

    <script>
        function clearSearch() {
            // Dynamically construct the base URL from the current location
            var baseUrl = window.location.origin + window.location.pathname;
            window.location.href = baseUrl;  // Redirect to base URL
        }
    </script>
</head>
<body>
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="/account.jsp#profile">Profile</a></li>
                <li><a href="PaymentHistoryServlet">Payment History</a></li>
                <li><a href="orders.jsp">Order History</a></li>
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
        <h1>Payment History</h1>
        <!-- Search Form -->
        <form action="PaymentHistoryServlet" method="GET">
            <input type="text" name="searchID" placeholder="Search by Payment ID" value="<%= request.getParameter("searchID") != null ? request.getParameter("searchID") : "" %>">
            <input type="text" name="searchDate" placeholder="Search by Date (YYYY-MM-DD)" value="<%= request.getParameter("searchDate") != null ? request.getParameter("searchDate") : "" %>">
            <button type="submit">Search</button>
            <button type="button" onclick="clearSearch()">Clear</button>
        </form>
    </div>
    <div class="container">
        <table border="1">
            <tr>
                <th>Payment ID</th>
                <th>Amount</th>
                <th>Method</th>
                <th>Date</th>
                <th>Status</th>
                <th>Card Number</th>
            </tr>
            <%
                List<payment> payments = (List<payment>) request.getAttribute("payments");
                if (payments != null) {
                    for (payment pay : payments) {
            %>
            <tr>
                <td><%= pay.getPaymentID() %></td>
                <td><%= pay.getAmount() %></td>
                <td><%= pay.getMethod() %></td>
                <td><%= pay.getDate() %></td>
                <td><%= pay.getStatus() %></td>
                <td><%= pay.getCardID() %></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>
       
</body>
</html>