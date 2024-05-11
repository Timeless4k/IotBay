package model.DAO;

import model.payment;
import model.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class paymentDAO {
    private Connection conn;

    public paymentDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean createPayment(payment payment) throws SQLException {
        String sql = "INSERT INTO payments (PaymentID, PaymentAmount, PaymentMethod, PaymentDate, PaymentStatus, CardID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, payment.getPaymentID());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getMethod());
            statement.setString(4, payment.getDate());
            statement.setString(5, payment.getStatus());
            statement.setString(6, payment.getCardID());
            int result = statement.executeUpdate();
            return result > 0;  // Return true if the row was inserted
        }
    }

    public payment readPayment(long paymentID) throws SQLException {
        String sql = "SELECT PaymentID, PaymentAmount, PaymentMethod, PaymentDate, PaymentStatus, CardID FROM payments WHERE PaymentID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, paymentID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new payment(
                        resultSet.getLong("PaymentID"),
                        resultSet.getDouble("PaymentAmount"),
                        resultSet.getString("PaymentMethod"),
                        resultSet.getString("PaymentDate"),
                        resultSet.getString("PaymentStatus"),
                        resultSet.getString("CardID")
                    );
                }
            }
        }
        return null;
    }

    public boolean updatePayment(payment payment) throws SQLException {
        String sql = "UPDATE payments SET PaymentAmount = ?, PaymentMethod = ?, PaymentDate = ?, PaymentStatus = ?, CardID = ? WHERE PaymentID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setDouble(1, payment.getAmount());
            statement.setString(2, payment.getMethod());
            statement.setString(3, payment.getDate());
            statement.setString(4, payment.getStatus());
            statement.setString(5, payment.getCardID());
            statement.setLong(6, payment.getPaymentID());
            int result = statement.executeUpdate();
            return result > 0;  // Return true if the row was updated
        }
    }

    public boolean deletePayment(long paymentID) throws SQLException {
        String sql = "DELETE FROM payments WHERE PaymentID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, paymentID);
            int result = statement.executeUpdate();
            return result > 0;  // Return true if the row was deleted
        }
    }

    public List<payment> getAllPayments() throws SQLException {
        List<payment> payments = new ArrayList<>();
        String sql = "SELECT PaymentID, PaymentAmount, PaymentMethod, PaymentDate, PaymentStatus, CardID FROM payments";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                payments.add(new payment(
                    resultSet.getLong("PaymentID"),
                    resultSet.getDouble("PaymentAmount"),
                    resultSet.getString("PaymentMethod"),
                    resultSet.getString("PaymentDate"),
                    resultSet.getString("PaymentStatus"),
                    resultSet.getString("CardID")
                ));
            }
        }
        return payments;
    }
}
