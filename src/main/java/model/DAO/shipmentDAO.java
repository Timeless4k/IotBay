
// package model.DAO;

// import model.Shipment;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// public class ShipmentDAO {
//     private Connection connection;

//     public ShipmentDAO(Connection connection) throws SQLException {
// 		this.connection = connection;
//         connection.setAutoCommit(false);
// 	}

//     public void createShipment(Shipment shipmentDetails) throws SQLException {
//         String query = "INSERT INTO shipments (address, contact_email, contact_phone, arrival_date, method) VALUES (?, ?, ?, ?, ?)";
//         try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//             preparedStatement.setString(1, shipmentDetails.getShipmentAddress());
//             preparedStatement.setString(2, shipmentDetails.getShipmentContactInfoEmail());
//             preparedStatement.setString(3, shipmentDetails.getShipmentContactInfoPhoneNumber());
//             preparedStatement.setString(4, shipmentDetails.getShipmentDate());
//             preparedStatement.setString(5, shipmentDetails.getShipmentMethod());

//             preparedStatement.executeUpdate();
//         } catch (SQLException e) {
//             System.err.println("Create shipment failed: " + e.getMessage());
//         }
//     }
// }





package model.DAO;
import model.shipment;
import java.sql.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class shipmentDAO {
    private Connection connection;

    private PreparedStatement checkShipmentIDExistsSt;

    public shipmentDAO(Connection connection) throws SQLException {
		this.connection = connection;
        connection.setAutoCommit(false);

        checkShipmentIDExistsSt = connection.prepareStatement("SELECT COUNT(*) FROM ShipmentData WHERE ShipmentID = ?");
	}


    // // Method to generate a unique ShipmentID
    // public long generateUniqueShipmentID() throws SQLException {
    //     Random rand = new Random();
    //     long shipmentID = Math.abs(rand.nextLong());
    //     while (shipmentIDExists(shipmentID)) {
    //         shipmentID = Math.abs(rand.nextLong());
    //     }
    //     return shipmentID;
    // }

    // // Check if a ShipmentID already exists in the database
    // private boolean shipmentIDExists(long shipmentID) throws SQLException {
    //     checkShipmentIDExistsSt.setLong(1, shipmentID);
    //     ResultSet rs = checkShipmentIDExistsSt.executeQuery();
    //     if (rs.next()) {
    //         return rs.getInt(1) > 0;
    //     }
    //     return false;
    // }


    // Method to generate a unique ShipmentID
    public String generateUniqueShipmentID() throws SQLException {
        Random rand = new Random();
        long shipmentIDLong = Math.abs(rand.nextLong());
        String shipmentID = Long.toString(shipmentIDLong); // Convert long to string
        while (shipmentIDExists(shipmentID)) {
            // Regenerate shipmentID if it already exists
            shipmentIDLong = Math.abs(rand.nextLong());
            shipmentID = Long.toString(shipmentIDLong);
        }
        return shipmentID;
    }

    // Check if a ShipmentID already exists in the database
    private boolean shipmentIDExists(String shipmentID) throws SQLException {
        // Assuming shipmentID is stored as a string in the database
        checkShipmentIDExistsSt.setString(1, shipmentID);
        ResultSet rs = checkShipmentIDExistsSt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }


    // public void createShipment(Shipment shipmentDetails) throws SQLException {
    //     String query = "INSERT INTO ShipmentData (ShipmentID, ShipmentAddress, ShipmentExpectedDate, ShipmentType) VALUES (?, ?, ?, ?)";
    //     try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    //         preparedStatement.setLong(1, generateUniqueShipmentID());
    //         preparedStatement.setString(2, shipmentDetails.getShipmentAddress()); 
    //         preparedStatement.setString(3, shipmentDetails.getShipmentDate());
    //         preparedStatement.setString(4, shipmentDetails.getShipmentMethod());

    //         preparedStatement.executeUpdate();
    //     } catch (SQLException e) {
    //         System.err.println("Create shipment failed: " + e.getMessage());
    //     }
    // }


    public boolean createShipment(shipment shipmentDetails) throws SQLException {
        int status = 0;

        String query = "INSERT INTO ShipmentData (ShipmentID, ShipmentAddress, ShipmentExpectedDate, ShipmentType) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, generateUniqueShipmentID());
            preparedStatement.setString(2, shipmentDetails.getShipmentAddress()); 
            preparedStatement.setString(3, shipmentDetails.getShipmentDate());
            preparedStatement.setString(4, shipmentDetails.getShipmentMethod());

            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Create shipment failed: " + e.getMessage());
        }

        return (status > 0);
    }


    // Method to retrieve shipment data
<<<<<<< Updated upstream
    public List<String> getShipmentIdsByUserId(String userId) throws SQLException {
        List<String> shipmentIds = new ArrayList<>();
        String query = "SELECT ShippingID FROM orders WHERE UserID = ?";

        // Print statement to indicate start of method execution
        System.out.println("Executing getShipmentIdsByUserId method...");
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             // Print statement to indicate the SQL query being executed
            System.out.println("Executing SQL query: " + query);

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                String shipmentId = resultSet.getString("ShippingID");
                shipmentIds.add(shipmentId);

                // Print statement to show each shipment ID retrieved
                System.out.println("Shipment ID retrieved: " + shipmentId);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving shipment IDs: " + e.getMessage());
            throw e;
        }

        // Print statement to indicate end of method execution
        System.out.println("getShipmentIdsByUserId method execution complete.");
        
        return shipmentIds;
    }
    

    // Method to retrieve shipment details by shipment ID
    public shipment getShipmentDetailsById(String shipmentId) throws SQLException {
        shipment shipmentDetails = null;
        String query = "SELECT * FROM ShipmentData WHERE ShipmentID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Print statement to indicate the start of the method execution
            System.out.println("Executing getShipmentDetailsById method...");

            // Set the shipment ID parameter in the prepared statement
            int intShipmentId = Integer.parseInt(shipmentId);
            preparedStatement.setInt(1, intShipmentId);

            // Print statement to indicate the executed SQL query
            System.out.println("Executing SQL query: " + query);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Print statement to indicate that shipment details are retrieved
                System.out.println("Shipment details found for shipment ID: " + shipmentId);

                // Retrieve shipment details from the result set
                String shipmentID= resultSet.getString("ShipmentID");
=======
    public List<shipment> readShipment(String userId) throws SQLException {
        List<shipment> shipments = new ArrayList<>(); // Create a new ArrayList to hold shipment objects

        String query = "SELECT * FROM ShipmentData WHERE UserID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // String shipmentID = resultSet.getString("ShipmentID");
>>>>>>> Stashed changes
                String shipmentAddress = resultSet.getString("ShipmentAddress");
                String shipmentDate = resultSet.getString("ShipmentExpectedDate");
                String shipmentMethod = resultSet.getString("ShipmentType");

<<<<<<< Updated upstream
                // Print statement to indicate the retrieved shipment details
                System.out.println("Shipment ID: " + shipmentID);
                System.out.println("Shipment Address: " + shipmentAddress);
                System.out.println("Shipment Date: " + shipmentDate);
                System.out.println("Shipment Method: " + shipmentMethod);

                // Create a new shipment object with the retrieved details
                shipmentDetails = new shipment(shipmentID, shipmentAddress, shipmentDate, shipmentMethod);
            } else {
                // Print statement if no shipment details found for the given shipment ID
                System.out.println("No shipment details found for shipment ID: " + shipmentId);
            }
        } catch (SQLException e) {
            // Print statement to indicate any SQL exception occurred
            System.err.println("Error retrieving shipment details by ID: " + e.getMessage());
            throw e;
        }
        
        // Print statement to indicate the end of the method execution
        System.out.println("getShipmentDetailsById method execution complete.");
        
        return shipmentDetails;
    }


    public List<shipment> readShipment(String userID) throws SQLException {
        List<shipment> shipments = new ArrayList<>();
    
        try {
            // Get the shipment IDs associated with the user ID
            List<String> shipmentIds = getShipmentIdsByUserId(userID);
            
            // Retrieve shipment details for each shipment ID
            for (String shipmentId : shipmentIds) {
                // Call a method to retrieve shipment details based on the shipment ID
                // This method would query the Shipment table based on the shipment ID
                // and return the shipment details
                shipment shipmentDetails = getShipmentDetailsById(shipmentId);
                
                // Add the shipment details to the list of shipments
                shipments.add(shipmentDetails);
            }
        } catch (SQLException e) {
            System.err.println("Error reading shipment details: " + e.getMessage());
            throw e;
        }
        
        return shipments;
    }     


=======
                // Create a new Shipment object and add it to the list
                shipment shipmentObj = new shipment(shipmentAddress, shipmentDate, shipmentMethod);
                // shipment shipmentObj = new shipment(shipmentID, shipmentAddress, shipmentDate, shipmentMethod);
                shipments.add(shipmentObj);
            }
        } catch (SQLException e) {
            System.err.println("Read shipment failed: " + e.getMessage());
        }

        return shipments; // Return the list of shipment objects
    }


>>>>>>> Stashed changes
    // // Method to retrieve shipment data
    // public List<shipment> readShipment(String userId) throws SQLException {
    //     List<shipment> shipments = new ArrayList<>();

    //     String query = "SELECT * FROM ShipmentData WHERE UserID = ?";
    //     try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    //         preparedStatement.setString(1, userId);
    //         ResultSet resultSet = preparedStatement.executeQuery();

    //         while (resultSet.next()) {
    //             String shipmentId = resultSet.getString("ShipmentID");
    //             String shipmentAddress = resultSet.getString("ShipmentAddress");
    //             String shipmentDate = resultSet.getString("ShipmentExpectedDate");
    //             String shipmentType = resultSet.getString("ShipmentType");

    //             // Create a new Shipment object and add it to the list
    //             shipment.add(new shipment(shipmentId, shipmentAddress, shipmentDate, shipmentType));
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("Read shipment failed: " + e.getMessage());
    //     }

    //     return shipments;
    // }

}
