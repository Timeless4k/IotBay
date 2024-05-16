<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="model.order" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Order Management</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" type="image/png" href="images/Logo.webp">
</head>

<body class="light-mode">
    <div class="top-banner">
        Free Shipping Over $49 (AUD)
    </div>

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
                    <div>
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
        <%
            ArrayList<order> currentorders = (ArrayList<order>) session.getAttribute("orders");
            System.out.println("Current orders size: " + (currentorders != null ? currentorders.size() : "null"));
        %>
        <% if(currentorders != null) { %>
            <table>
                <tr>
                    <th>OrderID</th>
                    <th>OrderName</th>
                    <th>OrderType</th>
                    <th>OrderQuantity</th>
                    <th>OrderDate</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
                <% if(!currentorders.isEmpty()) {
                    for(order o : currentorders) { %> 
                        <tr>
                            <td><%= o.getOrderID() %></td>
                            <td><%= o.getOrderName() %></td>
                            <td><%= o.getOrderType() %></td>
                            <td><%= o.getOrderQuantity() %></td>
                            <td><%= o.getOrderDate() %></td>
                            <td><form method="post" action="OrderServlet"><input type="hidden" name="action" value="update"> <input type="hidden" name="OrderID" value="<%= o.getOrderID() %>"> <button type="submit">Update</button> </form></td>
                            <td><form method="post" action="OrderServlet"><input type="hidden" name="action" value="delete"> <input type="hidden" name="OrderID" value="<%= o.getOrderID() %>"> <button type="submit">Delete</button> </form></td>
                        </tr>
                    <% }
                } %>
            </table>
        <% } else { %>
            <p>Servlet Order Injection Failure or orders may not Existing</p>
        <% } %>
        <form method="post" action="OrderServlet">
            <input type="hidden" name="action" value="add">
            Order Name: <input type="text" name="orderName" required><br>
            Order Type: <input type="text" name="orderType" required><br>
            Order Quantity: <input type="number" name="orderQuantity" required><br>
            Order Date: <input type="datetime-local" name="orderDate" required><br>
            <button type="submit">Add Order</button> 
        </form>        
    </div>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
        <p class="attribution">
            Icons created by <a href="https://www.flaticon.com/free-icons/user" title="user icons" target="_blank">Freepik - Flaticon</a>
            <span>|</span>
            <a href="https://www.flaticon.com/free-icons/basket" title="basket icons" target="_blank">Basket icons created by Karacis - Flaticon</a>
        </p>
    </footer>
</body>
</html>