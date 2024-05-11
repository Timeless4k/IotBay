<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Payments and Cards</title>
</head>
<body>
    <h1>Payment and Card Management</h1>

    <!-- Section to create a new payment -->
    <h2>Create Payment</h2>
    <form action="PaymentServlet" method="post">
        <input type="hidden" name="action" value="create">
        <label>Amount:</label>
        <input type="text" name="amount" required><br>
        <label>Payment Method:</label>
        <select name="method">
            <option value="Credit Card">Credit Card</option>
            <option value="Debit Card">Debit Card</option>
            <option value="Payment on Delivery">Payment on Delivery</option>
        </select><br>
        <label>Date (DD/MM/YYYY):</label>
        <input type="date" name="date" required><br>
        <label>Status:</label>
        <select name="status">
            <option value="Approved">Approved</option>
            <option value="Pending">Pending</option>
            <option value="Failed">Failed</option>
        </select><br>
        <label>Card ID:</label>
        <input type="text" name="cardID" required><br>
        <button type="submit">Submit Payment</button>
    </form>

    <!-- Section to create a new card -->
    <h2>Create Card</h2>
    <form action="CardServlet" method="post">
        <input type="hidden" name="action" value="create">
        <label>Card Number:</label>
        <input type="text" name="cardNumber" required><br>
        <label>Card Holder Name:</label>
        <input type="text" name="cardHolderName" required><br>
        <label>Card Expiry (DD/MM/YYYY):</label>
        <input type="date" name="cardExpiry" required><br>
        <label>Card CVV:</label>
        <input type="text" name="cardCVV" required><br>
        <label>User ID:</label>
        <input type="text" name="userID" required><br>
        <button type="submit">Submit Card</button>
    </form>

    <!-- Section to display all payments -->
    <h2>List of Payments</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Payment ID</th>
                <th>Amount</th>
                <th>Method</th>
                <th>Date</th>
                <th>Status</th>
                <th>Card ID</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="payment" items="${payments}">
                <tr>
                    <td>${payment.paymentID}</td>
                    <td>${payment.amount}</td>
                    <td>${payment.method}</td>
                    <td>${payment.date}</td>
                    <td>${payment.status}</td>
                    <td>${payment.cardID}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Section to display all cards -->
    <h2>List of Cards</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Card ID</th>
                <th>Card Number</th>
                <th>Card Holder Name</th>
                <th>Expiry</th>
                <th>CVV</th>
                <th>User ID</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="card" items="${cards}">
                <tr>
                    <td>${card.cardID}</td>
                    <td>${card.cardNumber}</td>
                    <td>${card.cardHolderName}</td>
                    <td>${card.cardExpiry}</td>
                    <td>${card.cardCVV}</td>
                    <td>${card.userID}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
