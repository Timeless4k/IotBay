
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

    // Method to generate a unique ShipmentID
    public long generateUniqueShipmentID() throws SQLException {
        Random rand = new Random();
        long shipmentID = Math.abs(rand.nextLong());
        while (shipmentIDExists(shipmentID)) {
            shipmentID = Math.abs(rand.nextLong());
        }
        return shipmentID;
    }

    // Check if a ShipmentID already exists in the database
    private boolean shipmentIDExists(long shipmentID) throws SQLException {
        checkShipmentIDExistsSt.setLong(1, shipmentID);
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
            preparedStatement.setLong(1, generateUniqueShipmentID());
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
    public List<shipment> readShipment(String userId) throws SQLException {
        List<shipment> shipments = new ArrayList<>(); // Create a new ArrayList to hold shipment objects

        String query = "SELECT * FROM ShipmentData WHERE UserID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // String shipmentID = resultSet.getString("ShipmentID");
                String shipmentAddress = resultSet.getString("ShipmentAddress");
                String shipmentDate = resultSet.getString("ShipmentExpectedDate");
                String shipmentMethod = resultSet.getString("ShipmentType");

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
