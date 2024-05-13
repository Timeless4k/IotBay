<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thank You for Your Order!</title>
    <link rel="stylesheet" href="css/style.css"> <!-- Make sure to link your CSS for styling -->
</head>
<body>
    <h1>Order Confirmation</h1>
    <p>Thank you for your purchase!</p>
    
    <div>
        <h2>Order Details</h2>
        <p>Order ID: ${orderID}</p>
        <p>Payment Method Used: ${paymentMethod}</p>
        <p>Card Used: **** **** **** ${lastFourDigits}</p>
        <p>If you have any questions, please contact us at <a href="mailto:support@yourwebsite.com">support@yourwebsite.com</a>.</p>
    </div>

    <h3>What's Next?</h3>
    <p>You will receive an email confirmation shortly. We will send you a shipment notification once your order is on its way.</p>
    <p><a href="main.jsp">Continue Shopping</a></p>
</body>
</html>
