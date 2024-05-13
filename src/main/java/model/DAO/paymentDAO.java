package model.DAO;

import java.sql.*;
import java.time.LocalDate;
import model.payment;

public class paymentDAO {
    private Connection conn;

    public paymentDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(false);
    }

    public boolean createPayment(payment pay) {
        String sql = "INSERT INTO payments (PaymentID, PaymentAmount, PaymentMethod, PaymentDate, PaymentStatus, CardID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, pay.getPaymentID());
            pstmt.setDouble(2, pay.getAmount());
            pstmt.setString(3, pay.getMethod());
            pstmt.setString(4, LocalDate.now().toString());  // Assuming the date is set to current date
            pstmt.setString(5, pay.getStatus());
            pstmt.setString(6, pay.getCardID());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Create payment failed: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Transaction rollback failed: " + ex.getMessage());
            }
        }
        return false;
    }

    public ResultSet getPaymentsForUser(long userID) {
        String sql = "SELECT * FROM payments WHERE CardID IN (SELECT CardID FROM cardinformation WHERE UserID = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get payments failed: " + e.getMessage());
            return null;
        }
    }
}
