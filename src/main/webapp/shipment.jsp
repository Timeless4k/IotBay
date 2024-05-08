
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

            <div class="row">

                <div class="column">

                    <div class="vertical_spacing"></div>

                    <div class="shipment_input">
                        <label class="shipment_input_header">Shipment Address:</label>
                        <br>
                        <input type="text" id="shipmentAddress" name="shipmentAddress" required class="shipment_input_field">
                    </div>

                    <div class="shipment_input">
                        <label class="shipment_input_header">Contact Information:</label>
                        <br>
                        <div class="contact_info">
                            <p>Email</p>
                            <input type="text" id="shipmentContactInfo_Email" name="shipmentContactInfo_Email" required class="shipment_input_field">
                            <div class="vertical_spacing"></div>
                            <p>Phone Number</p>
                            <input type="text" id="shipmentContactInfo_PhoneNumber" name="shipmentContactInfo_PhoneNumber" required class="shipment_input_field">
                        </div>
                    </div>     

                    <div class="shipment_input">
                        <label class="shipment_input_header">Preferred Arrival Date:</label>
                        <br>
                        <input type="date" id="shipmenttDate" name="shipmenttDate" required class="shipment_input_field">
                    </div>
                
                </div>




                <!-- Contiue here... -->
                <div class="column">

                    <div class="vertical_spacing"></div>
                    
                    <div class="shipment_input">
                        <label for="shipmentMethod" class="shipment_input_header">Shipment Method:</label>
                        <br>
                        <input type="text" id="shipmentMethod" name="shipmentMethod" required class="shipment_input_field">
                    </div>

                </div>



                <input type="submit" class="button" value="Place Order">

            </div>

        </form>

    </div>

    <!-- <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer> -->
</body>
</html>
