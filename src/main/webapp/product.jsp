<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.product" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <link rel="stylesheet" href="css/product.css" type="text/css">
        <link rel="icon" type="image/png" href="images/Logo.webp">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <title>IoTBay - Products</title>
    </head>
    <body>
    <% ArrayList<product> productlist = (ArrayList<product>) session.getAttribute("productList"); %>
    <div class="top-banner">
        Free Shipping Over $49 (AUD)
    </div>

    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo">
        <nav class="navbar">
            <ul>
                <li><a href="#about_us">About Us</a></li>
                <li><a href="#FAQs">FAQs</a></li>
                <li><a href="product.jsp">Catalog</a></li>
            </ul>
        </nav>

        <div class="nav-icons">
            <a href="cart.jsp">
                <img src="images/shopping-bag.png" alt="Cart" class="cart-icon">
            </a>
            <div class="account-menu">
                <img src="images/user.png" alt="Account" class="account-icon" onmouseover="showWelcomeMessage()" onmouseout="hideWelcomeMessage()">
                <div class="dropdown-content">
                    <div>${user.firstName} ${user.lastName}</div>
                    <a href="account.jsp">Account</a>
                    <a href="logout.jsp">Logout</a>
                </div>
            </div>
        </div>
    </header>

    <div class="product-container">
        <% if(productlist != null) {
            for(product i : productlist) { %>
                <div class="product-item">
                    <img src="images/<%=i.getpID()%>.png" alt="<%=i.getName()%>">
                    <h1><%=i.getName()%></h1>
                    <p>Price: <%=i.getPrice()%></p>
                    <p><%=i.getStockLevel()%> in stock</p>
                    <input type="number" name="quantity" id="qty<%=i.getpID()%>" min="1" value="1" style="width: 60px;">
                    <button onclick="addToCart(<%=i.getpID()%>, '<%=i.getName()%>');" >Add to Cart</button>
                    <button onclick="removeFromCart(<%=i.getpID()%>);" >Remove from Cart</button>
                </div>
            <% }
        } else { %> 
            <h2>No Products Found For Search</h2>
        <% } %>
    </div>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
        <p class="attribution">
            Icons created by <a href="https://www.flaticon.com/free-icons/user" title="user icons">Freepik - Flaticon</a>
            <span>|</span>
            <a href="https://www.flaticon.com/free-icons/basket" title="basket icons">Basket icons created by Karacis - Flaticon</a>
        </p>
    </footer>

    <script>
    function addToCart(productId, productName) {
        var quantity = $("#qty" + productId).val();
        $.post("CartServlet", { action: "add", productID: productId, quantity: quantity }, function(response) {
            alert(productName + " added to cart. Cart count: " + response);
        });
    }

    function removeFromCart(productId) {
        $.post("CartServlet", { action: "remove", productID: productId }, function(response) {
            alert("Item removed from cart. Cart count: " + response);
        });
    }

    function showWelcomeMessage() {
        document.getElementById('welcome-message').style.display = 'block';
    }

    function hideWelcomeMessage() {
        document.getElementById('welcome-message').style.display = 'none';
    }
    </script>
    </body>
</html>
