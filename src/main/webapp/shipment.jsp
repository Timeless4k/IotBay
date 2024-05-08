
<!-- 
A customer user can create shipment details (e.g. shipment method, date and address) 
for their order (shipment id linked to order id).
-->



<!-- 
Implement:
- Shipping address (including street address, city, state/province, postal/ZIP code, and country)

- Contact phone number
- Email address (for confirmation and updates, if applicable)

- Shipping method (along with showing what each method costs)

- Preferred arrival date (hopefully when the date to receive the order by)
-->



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Shipment Page</title>
    <!-- <link rel="stylesheet" href="css/style.css"> -->
    <link rel="stylesheet" href="css/shipment.css">
</head>
<body>
    <h1>Shipment Information</h1>

    <hr>
    <br><br>

    <div class="shipment-info">

        <form action="ShipmentServlet" method="post">

            <div class="shipment_input_field">
                <label for="shipmentAddress" class="shipment_input_header">Shipment Address:</label>
                <br>
                <input type="text" id="shipmentAddress" name="shipmentAddress" required>
            </div>
         
            <div class="shipment_input_field">
                <label for="shipmentMethod" class="shipment_input_header">Shipment Method:</label>
                <br>
                <input type="text" id="shipmentMethod" name="shipmentMethod" required>
            </div>

            <div class="shipment_input_field">
                <label for="shipmenttDate" class="shipment_input_header">Shipment Date:</label>
                <br>
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
