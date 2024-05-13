package model.DAO;

import model.payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class paymentDAO {
    private Connection conn;

    public paymentDAO(Connection conn) {
        this.conn = conn;
    }

    // Create a payment entry
    public boolean createPayment(payment pay) throws SQLException {
        String sql = "INSERT INTO payments (PaymentAmount, PaymentMethod, PaymentDate, PaymentStatus, CardID) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, pay.getAmount());
            ps.setString(2, pay.getMethod());
            ps.setString(3, pay.getDate());
            ps.setString(4, pay.getStatus());
            ps.setString(5, pay.getCardID());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Fetch payment details by ID
    public payment getPaymentById(long paymentID) throws SQLException {
        String sql = "SELECT * FROM payments WHERE PaymentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, paymentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new payment(
                        rs.getLong("PaymentID"),
                        rs.getDouble("PaymentAmount"),
                        rs.getString("PaymentMethod"),
                        rs.getString("PaymentDate"),
                        rs.getString("PaymentStatus"),
                        rs.getString("CardID")
                    );
                }
            }
        }
        return null;
    }

    // Update payment details
    public boolean updatePayment(payment pay) throws SQLException {
        String sql = "UPDATE payments SET PaymentAmount = ?, PaymentMethod = ?, PaymentDate = ?, PaymentStatus = ?, CardID = ? WHERE PaymentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, pay.getAmount());
            ps.setString(2, pay.getMethod());
            ps.setString(3, pay.getDate());
            ps.setString(4, pay.getStatus());
            ps.setString(5, pay.getCardID());
            ps.setLong(6, pay.getPaymentID());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Delete payment record
    public boolean deletePayment(long paymentID) throws SQLException {
        String sql = "DELETE FROM payments WHERE PaymentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, paymentID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
}
