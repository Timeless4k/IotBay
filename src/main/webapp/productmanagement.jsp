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
    
    <header>
        <img src="images/Logo.png" alt="IoTBay Logo" class="logo" onclick="window.location='main.jsp'">
        <nav class="navbar">
            <ul>
                <li><a href="/account.jsp#profile">Profile</a></li>
                <li><a href="PaymentHistoryServlet">Payment History</a></li>
                <li><a href="order.jsp">Order History</a></li>
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
    <% ArrayList<product> currentproducts = (ArrayList<product>) session.getAttribute("products"); %>
    <% if(currentproducts != null) { %>
                <table>
                    <tr>
                        <th>ProductID</th>
                        <th>ProductName</th>
                        <th>ProductType</th>
                        <th>ProductStatus</th>
                        <th>ProductPrice</th>
                        <th>ProductDescription</th>
                        <th>ProductQuantity</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                    <%
                            if(!currentproducts.isEmpty()) {
                                for(product p : currentproducts) {
                                    %> 
                                    <tr>
                                        <td><%= p.getpID() %></td>
                                        <td><%= p.getName() %></td>
                                        <td><%= p.getType() %></td>
                                        <td><%= p.getStatus() %></td>
                                        <td><%= p.getPrice() %></td>
                                        <td><%= p.getDescription() %></td>
                                        <td><%= p.getStockLevel() %></td>
                                        <td><form method="post" action="ActualUpdateProductServlet"><input type="hidden" name="action" value="update"> <input type="hidden" name="pID" value="<%= p.getpID() %>"> <button type="submit">Update</button> </form></td>
                                        <td><form method="post" action="UpdateProductServlet"><input type="hidden" name="action" value="delete"> <input type="hidden" name="pID" value="<%= p.getpID() %>"> <button type="submit">Delete</button> </form></td>
                                    </tr>
                                    <%
                                }
                            }
                    %>

                </table>
  <%  } else { %>

        <p>Servlet Product Injection Failure or products may not Existing</p>
        
    <% } %>
    <form method="post" action="UpdateProductServlet">
        <input type="hidden" name="action" value="add">
        Product Name: <input type="text" name="name" required><br>
        Product Type: <input type="text" name="type" required><br>
        Product Status: <select name="status" id="status">
                    <option value="Backorder">Backorder</option>
                    <option value="InStock">InStock</option>
                    <option value="OutOfStock">OutOfStock</option>
                        </select>
        Product Price: <input type="text" name="price" required><br>
        Product Description: <input type="text" name="description" required><br>
        Product Quantity: <input type="text" name="stockLevel" required><br>
        Product Release Date: <input type="date" name="releaseDate" required><br>
        <button type="submit">Add Product</button> 
    </form>

    
    
    
    
    </div>
       
</body>
</html>