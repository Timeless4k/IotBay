<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/payment.css" />
</head>
<body>

<header>
    <!-- Your header content here -->
</header>

<div class="checkout-container">
    <div class="payment-info">
        <h2>Payment Information</h2>
        <form action="PaymentServlet" method="post">
            <div class="form-group">
                <label for="cardName">Cardholder Name:</label>
                <input type="text" id="cardName" name="cardName" required>
            </div>
            <div class="form-group">
                <label for="cardNumber">Card Number:</label>
                <input type="text" id="cardNumber" name="cardNumber" required pattern="\d{16}" title="Card number should be 16 digits">
            </div>
            <div class="form-group">
                <label for="expiryDate">Expiry Date:</label>
                <input type="text" id="expiryDate" name="expiryDate" required pattern="\d{2}/\d{2}" title="Expiry date should be in MM/YY format">
            </div>
            <div class="form-group">
                <label for="cvv">CVV:</label>
                <input type="text" id="cvv" name="cvv" required pattern="\d{3}" title="CVV should be 3 digits">
            </div>
            <input type="submit" class="button" value="Submit Payment">
        </form>
    </div>

    <div class="cart-summary">
        <h2>Cart Summary</h2>
        <!-- The cart summary should be dynamically generated based on the user's cart data -->
        <p><strong>Item:</strong> Example Item x1</p>
        <p><strong>Subtotal:</strong> $90.00</p>
        <p><strong>GST (10%):</strong> $9.00</p>
        <p><strong>Shipping:</strong> $5.00</p>
        <p><strong>Total:</strong> $104.00</p>
    </div>
</div>
