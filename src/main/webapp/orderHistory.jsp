<%@ page import="java.util.List" %>
<%@ page import="model.payment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Order History</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h1>Your Order History</h1>

    <!-- Search Form -->
    <form action="OrderHistoryServlet" method="GET">
        <input type="text" name="searchID" placeholder="Search by Payment ID">
        <input type="text" name="searchDate" placeholder="Search by Date (YYYY-MM-DD)">
        <button type="submit">Search</button>
    </form>

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
            <td><%= pay.getCardID() %></td> <!-- Make sure this is showing the card number -->
        </tr>
        <%
                }
            }
        %>
    </table>

</body>
</html>
