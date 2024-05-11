
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

        <form action="/ShipmentServlet" method="post" onsubmit="setActionValue('create')">

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



                <div class="column">

                    <div class="vertical_spacing"></div>

                    <div class="shipment_input">
                        <label class="shipment_input_header">Shipment Method:</label>
                        <br>
                        <div class="shipment_options">
                            <div class="shipment_option">
                                <input type="checkbox" id="FedEx" name="shipmentMethod" value="FedEx">
                                <label for="FedEx">FedEx Express</label>
                                <div class="vertical_spacing"></div>
                                <p><i>2-3 days shipping</i></p>

                                <img src="images/fedex.png" alt="FedEx Logo" class="fedex_logo">
                            </div>
                            <div class="shipment_option">
                                <input type="checkbox" id="Aramex" name="shipmentMethod" value="Aramex">
                                <label for="Aramex">Aramex Standard</label>
                                <div class="vertical_spacing"></div>
                                <p><i>1 week shipping</i></p>

                                <img src="images/aramex.png" alt="Aramex Logo" class="aramex_logo">
                            </div>
                            <div class="shipment_option">
                                <input type="checkbox" id="DHL" name="shipmentMethod" value="DHL">
                                <label for="DHL">DHL Standard</label>
                                <div class="vertical_spacing"></div>
                                <p><i>2 weeks shipping</i></p>

                                <img src="images/dhl.png" alt="DHL Logo" class="dhl_logo">
                            </div>
                        </div>
                    </div>                    
                </div>

                <button type="submit" onclick="document.getElementById('action').value = 'create'" class="shipment_submit_button">Next &#10132;</button> 

                <input type="hidden" id="action" name="action" value="">
                
            </div>

        </form>

    </div>

    <!-- <footer>
        <p>&copy; 2024 IoTBay. All rights reserved.</p>
    </footer> -->
</body>
</html>
