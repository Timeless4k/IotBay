<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Details</title>
</head>
<body>
    <h1>Payment Details</h1>
    <div>
        <% 
            model.payment payment = (model.payment) request.getAttribute("payment");
            if(payment != null) {
        %>
            <p><strong>Payment ID:</strong> <%= payment.getPaymentID() %></p>
            <p><strong>Amount:</strong> <%= payment.getAmount() %></p>
            <p><strong>Method:</strong> <%= payment.getMethod() %></p>
            <p><strong>Date:</strong> <%= payment.getDate() %></p>
            <p><strong>Status:</strong> <%= payment.getStatus() %></p>
        <% } else { %>
            <p>No payment found.</p>
        <% } %>
    </div>
</body>
</html>
