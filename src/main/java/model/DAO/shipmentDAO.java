
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
import model.Shipment;
import java.sql.*;
import java.util.Random;

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

    public boolean createShipment(Shipment shipmentDetails) throws SQLException {
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
}
