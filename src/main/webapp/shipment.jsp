<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Shipment Page</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="shipment-info">
        <h2>Shipment Information</h2>
        <form action="ShipmentServlet" method="post">
            <div class="">
                <label for="shipmentAddress">Shipment Address:</label>
                <input type="text" id="shipmentAddress" name="shipmentAddress" required>
            </div>
            <div class="">
                <label for="shipmentMethod">Shipment Method:</label>
                <input type="text" id="shipmentMethod" name="shipmentMethod" required>
            </div>
            <div class="">
                <label for="shipmenttDate">Shipment Date:</label>
                <input type="date" id="shipmenttDate" name="shipmenttDate" required>
            </div>
            <input type="submit" class="button" value="Place Order">
        </form>
    </div>

    <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer>
</body>
</html>
