
package model.DAO;

import model.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShipmentDAO {
    private Connection connection;

    public ShipmentDAO(Connection connection) throws SQLException {
		this.connection = connection;
        connection.setAutoCommit(false);
	}

    public void createShipment(Shipment shipmentDetails) throws SQLException {
        String query = "INSERT INTO shipments (address, contact_email, contact_phone, arrival_date, method) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, shipmentDetails.getShipmentAddress());
            preparedStatement.setString(2, shipmentDetails.getShipmentContactInfoEmail());
            preparedStatement.setString(3, shipmentDetails.getShipmentContactInfoPhoneNumber());
            preparedStatement.setString(4, shipmentDetails.getShipmentDate());
            preparedStatement.setString(5, shipmentDetails.getShipmentMethod());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Create shipment failed: " + e.getMessage());
        }
    }
}
