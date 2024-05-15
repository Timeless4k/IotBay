<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.cart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Cart</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" type="image/png" href="images/Logo.webp">
</head>
<body>
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="product.jsp">Catalog</a></li>
                <li><a href="account.jsp">Account</a></li>
                <li><a href="logout.jsp">Logout</a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <h1>Your Shopping Cart</h1>
        <c:if test="${not empty cartItems}">
            <table>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="item" items="${cartItems}">
                    <tr>
                        <td>${item.product.name}</td>
                        <td>
                            <form action="CartServlet?action=update" method="post">
                                <input type="number" name="quantity" value="${item.quantity}" min="1">
                                <input type="hidden" name="productID" value="${item.productID}">
                                <input type="submit" value="Update">
                            </form>
                        </td>
                        <td>${item.product.price}</td>
                        <td>
                            <a href="CartServlet?action=remove&productID=${item.productID}" class="button">Remove</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty cartItems}">
            <p>Your cart is currently empty.</p>
        </c:if>
    </div>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer>
</body>
</html>
