<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thank You for Your Purchase!</title>
    <link rel="stylesheet" href="css/confirmation.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Thank You for Your Purchase!</h1>
        </header>
        <section class="order-details">
            <h2>Order Details</h2>
            <!-- Use requestScope to ensure data is coming from the request attribute -->
            <p>Your order has been successfully placed on ${requestScope.payment.date}.</p>
            <ul>
                <li>Amount: <strong>${requestScope.payment.amount}</strong></li>
                <li>Payment Method: <strong>${requestScope.payment.method}</strong></li>
                <li>Customer: <strong>${requestScope.user.firstName} ${requestScope.user.lastName}</strong></li>
            </ul>
        </section>
        <section class="card-info">
            <h2>Card Information</h2>
            <ul>
                <li>Card Number: <strong>${requestScope.card.cardNumber}</strong></li>
                <li>Card Holder: <strong>${requestScope.card.cardHolderName}</strong></li>
                <li>Expiry Date: <strong>${requestScope.card.cardExpiry}</strong></li>
            </ul>
        </section>
    </div>
</body>
</html>
