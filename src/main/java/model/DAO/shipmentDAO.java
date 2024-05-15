
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
            // preparedStatement.setString(1, generateUniqueShipmentID());
            preparedStatement.setString(1, shipmentDetails.getShipmentID());
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
                String shipmentAddress = resultSet.getString("ShipmentAddress");
                String shipmentDate = resultSet.getString("ShipmentExpectedDate");
                String shipmentMethod = resultSet.getString("ShipmentType");

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





    // // Method to retrieve the current shipment ID for a user
    // public String getCurrentShipmentIdByUserId(String userId) throws SQLException {
    //     String shipmentId = null;
    //     String query = "SELECT ShippingID FROM orders WHERE UserID = ? ORDER BY OrderDate DESC LIMIT 1";

    //     // Print statement to indicate start of method execution
    //     System.out.println("Executing getCurrentShipmentIdByUserId method...");
        
    //     try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    //         // Print statement to indicate the SQL query being executed
    //         System.out.println("Executing SQL query: " + query);

    //         preparedStatement.setString(1, userId);
    //         ResultSet resultSet = preparedStatement.executeQuery();

    //         if (resultSet.next()) {
    //             shipmentId = resultSet.getString("ShippingID");
    //             // Print statement to show the current shipment ID retrieved
    //             System.out.println("Current Shipment ID retrieved: " + shipmentId);
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("Error retrieving current shipment ID: " + e.getMessage());
    //         throw e;
    //     }

    //     // Print statement to indicate end of method execution
    //     System.out.println("getCurrentShipmentIdByUserId method execution complete.");
        
    //     return shipmentId;
    // }


    // Method to retrieve shipment details by shipment ID
    public shipment getCurrentShipmentDetailsById(String shipmentId) throws SQLException {
        shipment shipmentDetails = null;
        String query = "SELECT * FROM ShipmentData WHERE ShipmentID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Print statement to indicate the start of the method execution
            System.out.println("Executing getShipmentDetailsById method...");

            // Set the shipment ID parameter in the prepared statement
            long longShipmentId = Long.parseLong(shipmentId);
            preparedStatement.setLong(1, longShipmentId);

            // Print statement to indicate the executed SQL query
            System.out.println("Executing SQL query: " + query);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Print statement to indicate that shipment details are retrieved
                System.out.println("Shipment details found for shipment ID: " + shipmentId);

                // Retrieve shipment details from the result set
                String shipmentID= resultSet.getString("ShipmentID");
                String shipmentAddress = resultSet.getString("ShipmentAddress");
                String shipmentDate = resultSet.getString("ShipmentExpectedDate");
                String shipmentMethod = resultSet.getString("ShipmentType");

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


    // public List<shipment> readForCurrentOrder(String shipmentID) throws SQLException {
    //     List<shipment> shipments = new ArrayList<>();
    
    //     try {
    //         // Get the shipment IDs associated with the user ID
    //         List<String> shipmentIds = getShipmentIdsByUserId(userID);
            
    //         // Retrieve shipment details for each shipment ID
    //         for (String shipmentId : shipmentIds) {
    //             // Call a method to retrieve shipment details based on the shipment ID
    //             // This method would query the Shipment table based on the shipment ID
    //             // and return the shipment details
    //             shipment shipmentDetails = getShipmentDetailsById(shipmentId);
                
    //             // Add the shipment details to the list of shipments
    //             shipments.add(shipmentDetails);
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("Error reading shipment details: " + e.getMessage());
    //         throw e;
    //     }
        
    //     return shipments;
    // }    


    public shipment readForCurrentOrder(String shipmentID) throws SQLException {
        shipment shipment = null;
    
        try {
            // Retrieve shipment details based on the shipment ID
            shipment = getCurrentShipmentDetailsById(shipmentID);
        } catch (SQLException e) {
            System.err.println("Error reading shipment details: " + e.getMessage());
            throw e;
        }
    
        return shipment;
    }    


    // public shipment readForCurrentOrder(String shipmentID) throws SQLException {
    //     shipment shipment = null;
    
    //     try {
    //         // Retrieve shipment details based on the shipment ID
    //         shipment = getCurrentShipmentDetailsById(shipmentID);
                
    //         // Add the shipment details to the list of shipments
    //         shipment.add(shipment);
    //     } catch (SQLException e) {
    //         System.err.println("Error reading shipment details: " + e.getMessage());
    //         throw e;
    //     }
        
    //     return shipment;
    // }


    // public shipment updateShipment(String shipmentId, String shipmentAddress, String shipmentDate, String shipmentMethod) throws SQLException {

    // } 

    
    public shipment updateShipment(String shipmentId, String shipmentAddress, String shipmentDate, String shipmentMethod) throws SQLException {
        try {
            // Create the SQL query to update the shipment details
            String query = "UPDATE ShipmentData SET ShipmentAddress = ?, ShipmentExpectedDate = ?, ShipmentType = ? WHERE ShipmentID = ?";
            
            // Create a PreparedStatement with the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            // Set the parameters for the PreparedStatement
            preparedStatement.setString(1, shipmentAddress);
            preparedStatement.setString(2, shipmentDate);
            preparedStatement.setString(3, shipmentMethod);
            preparedStatement.setString(4, shipmentId);
            
            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Shipment details updated successfully.");
                // If the update was successful, return the updated shipment details
                return new shipment(shipmentId, shipmentAddress, shipmentDate, shipmentMethod);
            } else {
                System.out.println("Shipment details NOT updated successfully.");
                // If no rows were affected, return null to indicate failure
                return null;
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            throw e; // Rethrow the exception for handling at a higher level
        }
    }


    public void deleteShipment(String shipmentId) throws SQLException {        
        // Define the SQL query to delete the shipment record
        String query = "DELETE FROM ShipmentData WHERE ShipmentID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set the shipment ID parameter in the prepared statement
            preparedStatement.setString(1, shipmentId);
            
            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Check if the deletion was successful
            if (rowsAffected > 0) {
                System.out.println("DELETION SUCCESSFUL");
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.err.println("Error deleting shipment: " + e.getMessage());
            throw e;
        }
    }
    
}
