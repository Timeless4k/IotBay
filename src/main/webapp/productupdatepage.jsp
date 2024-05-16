<%@ page import="java.util.List" %>
<%@ page import="java.util.*, model.user, model.payment, model.product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Your Payment History</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <% product selected = (product) session.getAttribute("requestedProduct"); %>
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="/account.jsp#profile">Profile</a></li>
                <li><a href="PaymentHistoryServlet">Payment History</a></li>
                <li><a href="orders.jsp">Order History</a></li>
                <li><a href="shipmentDetails.jsp">Shipment Details</a></li>
                <li><a href="/account.jsp#access">Access Logs</a></li>
                <c:if test="${user.uType == 'Admin'}">
                    <li><a href=UserServlet?action=displayAll>User Management</a></li>
                </c:if>
                <c:if test="${user.uType == 'Employee'}">
                    <li><a href=productmanagement.jsp>Product Management</a></li>
                </c:if>
                <form action="logout" method="post">
                    <input type="submit" value="Logout">
                </form>
            </ul>
        </nav>
    </header>

    <div class="container">  <!-- So do all the html in here formatting -->

    <%
    if(selected != null) {
        %>
        <form method="post" action="UpdateProductServlet">
        <input type="hidden" name="action" value="update">
        Product ID : <input type="text" name="id" value="<%=selected.getpID()%>" required><br>
        Product Name: <input type="text" name="name" value="<%=selected.getName()%>" required><br>
        Product Type: <input type="text" name="type" value="<%=selected.getType()%>" required><br>
        Product Status: <select name="status" id="status" value="<%=selected.getStatus()%>">
                    <option value="Backorder">Backorder</option>
                    <option value="InStock">InStock</option>
                    <option value="OutOfStock">OutOfStock</option>
                        </select>
        Product Price: <input type="text" name="price" value="<%=selected.getPrice()%>" required><br>
        Product Description: <input type="text" name="description" value="<%=selected.getDescription()%>" required><br>
        Product Quantity: <input type="text" name="stockLevel" value="<%=selected.getStockLevel()%>" required><br>
        Product Release Date: <input type="date" name="releaseDate" value="<%=selected.getReleaseDate()%>" required><br>
        <button type="submit">Update Product</button> 
        </form>
        
        <%
    } else {
        %> <p> There appears to have been a update error </p> <%
    }
    %>
    
    </div>
       
</body>
</html>