<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thank You for Your Purchase</title>
    <link rel="stylesheet" href="css/styles.css"> <!-- Include any CSS stylesheets -->
</head>
<body>
    <div class="container">
        <h1>Thank You for Your Purchase!</h1>
        <p>Your order has been successfully placed:</p>
        <ul>
            <li>Amount: ${payment.amount}</li>
            <li>Payment Method: ${payment.method}</li>
            <li>Payment Date: ${payment.date}</li>
            <li>User: ${user.firstName} ${user.lastName}</li>
        </ul>
    </div>
</body>
</html>
