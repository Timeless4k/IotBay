
<!-- For "UPDATE": A customer user can update their saved order before final submission. -->

<!-- when you create the shipment, store the shipment id into the session, then recall the shipment id in the next page, and 
search in the dao by that shipment id, and display the data. -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IoTBay - Shipment Page</title>
    <link rel="stylesheet" href="css/shipment.css">

    <script>
        window.onload = function() {
            // Create a new XMLHttpRequest object
            var xhr = new XMLHttpRequest();
            
            // Define the servlet URL
            var servletUrl = "/ShipmentServlet?action=readForCurrentOrder";
            
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
                                
                                // Loop through each shipment
                                for (var i = 0; i < shipments.length; i++) {
                                    var shipment = shipments[i];
                                    
                                    // Display current shipment details in a table row
                                    var row = "<tr>" +
                                                "<td>" + shipment.shipmentID + "</td>" +
                                                "<td>" + shipment.shipmentAddress + "</td>" +
                                                "<td>" + shipment.shipmentDate + "</td>" +
                                                "<td>" + shipment.shipmentMethod + "</td>" +
                                              "</tr>";
                                    tableBody.innerHTML += row;
                                }
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
    
    <script>
        function showUpdateForm() {
            var updateForm = document.getElementById("updateForm");
            if (updateForm.style.display === "none") {
                 updateForm.style.display = "block";
             } else {
                 updateForm.style.display = "none";
             }
         }
    </script>
</head>
<body>
    <h1>"Update" Saved Shipment Details</h1>

    <hr>
    <br><br>

    <div class="shipment-info">

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

        <br><br>

        
        <div class="shipment_confirmation_container">
            <!-- Button to trigger the update form -->
            <button onclick="showUpdateForm()" class="shipment_confirmation_button">Update Shipment</button>

            <!-- Button to delete shipment -->
            <form action="/ShipmentServlet" method="post">
                <input type="hidden" name="action" value="delete">
                <button type="submit" class="shipment_confirmation_button_two">Delete Shipment</button>
            </form>
        </div>

        <!-- Update form -->
        <div id="updateForm" style="display: none;">
            <form action="/ShipmentServlet" method="post" onsubmit="setActionValue('update')">

                <div class="shipment_input">
                    <label class="shipment_input_header">Shipment Address:</label>
                    <br>
                    <input type="text" id="shipmentAddress" name="shipmentAddress" required class="shipment_input_field">
                </div>

                <div class="shipment_input">
                    <label class="shipment_input_header">Preferred Arrival Date:</label>
                    <br>
                    <input type="date" id="shipmenttDate" name="shipmenttDate" required class="shipment_input_field">
                </div>

                <div class="shipment_input">
                    <label class="shipment_input_header">Shipment Method:</label>
                    <br>
                    <div class="shipment_options">
                        <div class="shipment_option">
                            <!-- <input type="checkbox" id="FedEx" name="shipmentMethod" value="FedEx"> -->
                            <input type="checkbox" id="FedEx" name="shipmentMethod" value="FedEx">
                            <label for="FedEx">FedEx Express</label>
                            <div class="vertical_spacing"></div>
                            <p><i>2-3 days shipping</i></p>

                            <img src="images/fedex.png" alt="FedEx Logo" class="fedex_logo">
                        </div>
                        <div class="shipment_option">
                            <!-- <input type="checkbox" id="Aramex" name="shipmentMethod" value="Aramex"> -->
                            <input type="checkbox" id="Aramex" name="shipmentMethod" value="Aramex">
                            <label for="Aramex">Aramex Standard</label>
                            <div class="vertical_spacing"></div>
                            <p><i>1 week shipping</i></p>

                            <img src="images/aramex.png" alt="Aramex Logo" class="aramex_logo">
                        </div>
                        <div class="shipment_option">
                            <!-- <input type="checkbox" id="DHL" name="shipmentMethod" value="DHL"> -->
                            <input type="checkbox" id="DHL" name="shipmentMethod" value="DHL">
                            <label for="DHL">DHL Standard</label>
                            <div class="vertical_spacing"></div>
                            <p><i>2 weeks shipping</i></p>

                            <img src="images/dhl.png" alt="DHL Logo" class="dhl_logo">
                        </div>
                    </div>
                </div>   

                <button type="submit" onclick="document.getElementById('action').value = 'update'" class="shipment_submit_button">Proceed to Payment  &#10132;</button> 
                <input type="hidden" id="action" name="action" value="">
            </form>
        </div>
        
    </div>

</body>
</html>
