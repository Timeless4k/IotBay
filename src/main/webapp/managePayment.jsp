<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.user" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Payment</title>
    <link rel="stylesheet" href="css/payment.css">
</head>
<body>  
    <div class="container">
        <h1>Manage Payment</h1>
        <a href="CardServlet?action=displayAll">Refresh Payment Methods</a>

        <!-- Add New Payment Method Form -->
        <h2>Add New Payment Method</h2>
        <form action="CardServlet" method="post">
            <input type="hidden" name="action" value="create">
            Card Number: <input type="text" name="cardNumber" required><br>
            Card Holder Name: <input type="text" name="cardHolderName" required><br>
            Expiry Date: <input type="text" name="cardExpiry" required><br>
            CVV: <input type="text" name="cardCVV" required><br>
            <button type="submit">Add Payment Method</button>
        </form>

        <!-- Existing Payment Methods Table -->
        <h2>Existing Payment Methods</h2>
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
                        <td><c:out value="${card.cardNumber}"/></td>
                        <td><c:out value="${card.cardHolderName}"/></td>
                        <td><c:out value="${card.cardExpiry}"/></td>
                        <td>
                            <a href="CardServlet?action=edit&cardId=${card.cardID}">Edit</a>
                            <a href="CardServlet?action=delete&cardId=${card.cardID}">Delete</a>
                        </td>
                    </tr>      
                </c:forEach>
            </tbody>
        </table>
    </div>

    <%-- Check if a user is logged in --%>
    <%
        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser != null) {
    %>
    <div>User ID: <%= loggedInUser.getuID() %></div>
    <div>Email Address: <%= loggedInUser.getEmail() %></div>
    <%-- Additional debugging information can be added here --%>
    <div>Debugging Info: User is logged in</div>
    <%
        } else {
    %>
    <div>User is not logged in</div>
    <%-- Additional debugging information can be added here --%>
    <%
        }
    %>
</body>
</html>
