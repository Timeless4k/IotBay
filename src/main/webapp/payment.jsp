<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.user" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout Payment</title>
    <link rel="stylesheet" href="css/payment.css">
</head>
<body>
    <div class="container">
        <h1>Checkout Payment</h1>
        
        <!-- Display Existing Payment Methods for Selection -->
        <h2>Select a Payment Method</h2>
        <form action="CardServlet" method="post">
            <input type="hidden" name="action" value="processPayment">
            <select name="cardID" required>
                <c:forEach var="card" items="${cardList}">
                    <option value="${card.cardID}">${card.cardHolderName} - ${card.cardNumber} (Exp: ${card.cardExpiry})</option>
                </c:forEach>
            </select>
            <button type="submit">Pay Now</button>
        </form>

        <!-- Add New Payment Method Form -->
        <h2>Add New Payment Method</h2>
        <form action="CardServlet" method="post">
            <input type="hidden" name="action" value="create">
            Card Number: <input type="text" name="cardNumber" required><br>
            Card Holder Name: <input type="text" name="cardHolderName" required><br>
            Expiry Date: <input type="text" name="cardExpiry" required placeholder="MM/YYYY" pattern="\d{2}/\d{4}" title="Format MM/YYYY"><br>
            CVV: <input type="text" name="cardCVV" required pattern="\d{3}" title="3 digits"><br>
            <button type="submit">Add Payment Method</button>
        </form>

        <!-- List of Existing Payment Methods with Edit/Delete Options -->
        <h2>Manage Your Payment Methods</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Card Number</th>
                    <th>Card Holder Name</th>
                    <th>Expiry Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="card" items="${cardList}">
                    <tr>
                        <td>${card.cardNumber}</td>
                        <td>${card.cardHolderName}</td>
                        <td>${card.cardExpiry}</td>
                        <td>
                            <a href="CardServlet?action=edit&cardId=${card.cardID}">Edit</a>
                            <a href="CardServlet?action=delete&cardId=${card.cardID}" onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>      
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Display User Information and Debugging Info -->
    <% 
        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser != null) {
    %>
    <div>User ID: <%= loggedInUser.getuID() %></div>
    <div>Email Address: <%= loggedInUser.getEmail() %></div>
    <div>Debugging Info: User is logged in</div>
    <%
        } else {
    %>
    <div>User is not logged in</div>
    <%
        }
    %>
</body>
</html> 
