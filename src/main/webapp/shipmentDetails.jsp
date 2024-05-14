<%@ page import="java.util.List" %>
<%@ page import="java.util.*, model.user, model.payment"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Shipment Details Page</title>
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
    <div class="container"> <!-- So do all the html in here formatting -->
    <h1>Shipment Details</h1>
    
    
    <hr>
    <br><br>

    <div class="shipment-info" id="shipmentContainer">
        <!-- Shipment data will be dynamically added here -->
    </div>

    <!-- Reference to jQuery library (you can replace this with your own version or use the CDN) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- JavaScript code to trigger the AJAX request when the page loads -->
    <script>
        $(document).ready(function() {
            // Function to retrieve shipment data
            function getShipmentData() {
                $.ajax({
                    url: 'ShipmentServlet?action=read', // Endpoint of your servlet
                    method: 'GET',
                    success: function(response) {
                        // Process the shipment data received from the server
                        displayShipmentData(response);
                    },
                    error: function(xhr, status, error) {
                        console.error('Error fetching shipment data:', error);
                    }
                });
            }

            // Function to display shipment data on the webpage
            function displayShipmentData(shipments) {
                var container = $('#shipmentContainer');
                container.empty(); // Clear existing content
                
                // Iterate over the shipments and append them to the container
                shipments.forEach(function(shipment) {
                    container.append('<div>' +
                        '<p>Shipment ID: ' + shipment.shipmentID + '</p>' +
                        '<p>Address: ' + shipment.shipmentAddress + '</p>' +
                        '<p>Date: ' + shipment.shipmentDate + '</p>' +
                        '<p>Type: ' + shipment.shipmentMethod + '</p>' +
                        '</div>');
                });
            }

            // Call the function to retrieve shipment data when the page loads
            getShipmentData();
        });
    </script>
</body>
</html>
