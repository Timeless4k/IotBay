<!-- <%@ page import="java.util.List" %>
<%@ page import="model.shipment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shipment Details</title>
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
    <div class="container">
        <h1>Shipment Details</h1>
    </div>
    <div class="container">
        <table border="1">
            <tr>
                <th>Shipment ID</th>
                <th>Address</th>
                <th>Date</th>
                <th>Method</th>
            </tr>
            <c:forEach var="shipment" items="${shipments}">
                <tr>
                    <td>${shipment.shipmentID}</td>
                    <td>${shipment.shipmentAddress}</td>
                    <td>${shipment.shipmentDate}</td>
                    <td>${shipment.shipmentMethod}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html> -->





<!DOCTYPE html>
<html>
<head>
    <title>Shipment Details</title>
    <link rel="stylesheet" href="css/general-settings.css">
    <link rel="stylesheet" href="css/style.css">
    <script>
        window.onload = function() {
            // Create a new XMLHttpRequest object
            var xhr = new XMLHttpRequest();
            
            // Define the servlet URL
            var servletUrl = "/ShipmentServlet?action=read";
            
            // Open a GET request to the servlet URL
            xhr.open("GET", servletUrl, true);
            
            // Set up a callback function to handle the response
            xhr.onreadystatechange = function() {
                console.log("Ready state:", xhr.readyState); // Print ready state
                
                if (xhr.readyState == 4) {
                    console.log("Status:", xhr.status); // Print status
                    
                    if (xhr.status == 200) {
                        console.log("Response text:", xhr.responseText); // Print response text
                        
                        // Check if the response text is not empty
                        if (xhr.responseText.trim() !== "") {
                            try {
                                // Parse the JSON response
                                var shipments = JSON.parse(xhr.responseText);
                                
                                // Update the HTML content with the retrieved shipment details
                                var tableBody = document.getElementById("shipmentTableBody");
                                tableBody.innerHTML = ""; // Clear existing rows
                                
                                // Iterate over the shipments and add them to the table
                                shipments.forEach(function(shipment) {
                                    var row = "<tr>" +
                                              "<td>" + shipment.shipmentID + "</td>" +
                                              "<td>" + shipment.shipmentAddress + "</td>" +
                                              "<td>" + shipment.shipmentDate + "</td>" +
                                              "<td>" + shipment.shipmentMethod + "</td>" +
                                              "</tr>";
                                    tableBody.innerHTML += row;
                                });
                            } catch (error) {
                                console.error("Error parsing JSON:", error); // Log JSON parsing error
                            }
                        } else {
                            console.error("Error: Response text is empty"); // Log empty response error
                        }
                    } else {
                        console.error("Error: Status code", xhr.status); // Print error status
                    }
                }
            };
            
            // Send the request
            xhr.send();
        };
    </script>    
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
    <div class="container">
        <h1>Shipment Details</h1>
    </div>
    <div class="container">
        <table border="1">
            <thead>
                <tr>
                    <th>Shipment ID</th>
                    <th>Address</th>
                    <th>Date</th>
                    <th>Method</th>
                </tr>
            </thead>
            <tbody id="shipmentTableBody">
                <!-- Table rows will be dynamically added here -->
            </tbody>
        </table>
    </div>
</body>
</html>
