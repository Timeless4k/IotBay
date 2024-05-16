<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thank You for Your Purchase!</title>
    <link rel="stylesheet" href="css/confirmation.css">
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        
        <nav class="navbar">
            <ul>
                <li><a href="#best-sellers">Best Sellers</a></li>
                <li><a href="#new-arrivals">New Arrivals</a></li>
                <li><a href="ProductNameSearchServlet?SearchType=ProductName&SearchQuery=">Catalog</a></li>
            </ul>
        </nav>

        <div class="nav-icons">
            <a href="orders.jsp">
                <img src="images/shopping-bag.png" alt="Cart" class="cart-icon">
            </a>
            <div class="account-menu">
                <img src="images/user.png" alt="Account" class="account-icon" onmouseover="showWelcomeMessage()" onmouseout="hideWelcomeMessage()">
                <div class="dropdown-content">
                    <div >
                        ${user.firstName} ${user.lastName}
                    </div>
                    <a href="account.jsp#profile">Account</a>
                    <form action="logout" method="post">
                        <input type="submit" value="Logout">
                    </form>
                </div>
            </div>
        </div>
    </header>
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
