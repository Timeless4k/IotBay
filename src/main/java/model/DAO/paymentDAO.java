package model.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

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
            // Generate a unique payment ID
            long paymentID = generateUniquePaymentID();
            
            pstmt.setLong(1, paymentID);
            pstmt.setDouble(2, pay.getAmount());
            pstmt.setString(3, pay.getMethod());
            pstmt.setString(4, pay.getDate()); // Set the formatted date and time obtained from the servlet
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
    

    private long generateUniquePaymentID() {
        // Generate a unique payment ID (you can implement your own logic)
        long paymentID = 0;
        boolean isUnique = false;
        while (!isUnique) {
            paymentID = generateRandomPaymentID(); // Implement your own method to generate a random ID
            
            // Check if the generated ID already exists in the database
            String checkSql = "SELECT COUNT(*) FROM payments WHERE PaymentID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                pstmt.setLong(1, paymentID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        if (count == 0) {
                            isUnique = true;
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error checking payment ID uniqueness: " + e.getMessage());
            }
        }
        return paymentID;
    }

    private long generateRandomPaymentID() {
        // Implement your logic to generate a random payment ID
        // You can use UUID or any other method to generate unique IDs
        return Math.abs(new Random().nextLong()); // Example: Using random long value
    }

    public ResultSet getPaymentsForUser(long userID) {
        // Corrected the SQL query to reference the correct table name `card`
        String sql = "SELECT * FROM payments WHERE CardID IN (SELECT CardID FROM card WHERE UserID = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userID);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get payments failed: " + e.getMessage());
            return null;
        }
    }
}
