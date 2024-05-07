package model.DAO;

import java.sql.*;
import java.util.ArrayList;
import model.payment;

public class paymentDAO {
    private Connection conn;

    public paymentDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean createPayment(payment payment) throws SQLException {
        String sql = "INSERT INTO Payments (PaymentAmount, PaymentMethod, PaymentDate, PaymentStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, payment.getAmount());
            pstmt.setString(2, payment.getMethod());
            pstmt.setString(3, payment.getDate());
            pstmt.setString(4, payment.getStatus());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public payment readPayment(long paymentID) throws SQLException {
        String sql = "SELECT * FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, paymentID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new payment(rs.getLong("PaymentID"), rs.getDouble("PaymentAmount"), rs.getString("PaymentMethod"), rs.getString("PaymentDate"), rs.getString("PaymentStatus"));
            }
            return null;
        }
    }

    public boolean updatePayment(payment payment) throws SQLException {
        String sql = "UPDATE Payments SET PaymentAmount = ?, PaymentMethod = ?, PaymentDate = ?, PaymentStatus = ? WHERE PaymentID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, payment.getAmount());
            pstmt.setString(2, payment.getMethod());
            pstmt.setString(3, payment.getDate());
            pstmt.setString(4, payment.getStatus());
            pstmt.setLong(5, payment.getPaymentID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean deletePayment(long paymentID) throws SQLException {
        String sql = "DELETE FROM Payments WHERE PaymentID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, paymentID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
