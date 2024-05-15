package model.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.payment;

public class paymentDAO {
    private Connection conn;

    /**
     * Constructor to initialize the paymentDAO with a database connection.
     * 
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */
    public paymentDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(false);
    }

    /**
     * Creates a new payment record in the database.
     * 
     * @param pay the payment object containing payment details
     * @return true if the payment was successfully created, false otherwise
     */
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

    /**
     * Generates a unique payment ID.
     * 
     * @return a unique payment ID
     */
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

    /**
     * Generates a random payment ID.
     * 
     * @return a randomly generated payment ID
     */
    private long generateRandomPaymentID() {
        // Implement your logic to generate a random payment ID
        // You can use UUID or any other method to generate unique IDs
        return Math.abs(new Random().nextLong()); // Example: Using random long value
    }

    /**
     * Retrieves a list of payments for a specific user.
     * 
     * @param userID the ID of the user
     * @return a list of payments associated with the user
     * @throws SQLException if a database access error occurs
     */
    public List<payment> getPaymentsForUser(long userID) throws SQLException {
        List<payment> payments = new ArrayList<>();
        String sql = "SELECT p.*, c.CardNumber FROM payments p " +
                     "JOIN card c ON p.CardID = c.CardID " +
                     "WHERE c.UserID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(new payment(
                        rs.getLong("PaymentID"),
                        rs.getDouble("PaymentAmount"),
                        rs.getString("PaymentMethod"),
                        rs.getString("PaymentDate"),
                        rs.getString("PaymentStatus"),
                        rs.getString("CardNumber")
                    ));
                }
            }
        }
        return payments;
    }

    /**
     * Searches for payments based on user ID, payment ID, and payment date.
     * 
     * @param userID the ID of the user
     * @param paymentID the ID of the payment (can be null)
     * @param paymentDate the date of the payment (can be null or empty)
     * @return a list of payments matching the search criteria
     * @throws SQLException if a database access error occurs
     */
    public List<payment> searchPayments(long userID, Long paymentID, String paymentDate) throws SQLException {
        List<payment> payments = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.*, c.CardNumber FROM payments p " +
            "JOIN card c ON p.CardID = c.CardID " +
            "WHERE c.UserID = ?"
        );
    
        if (paymentID != null) {
            sql.append(" AND p.PaymentID = ?");
        }
        if (paymentDate != null && !paymentDate.isEmpty()) {
            // Convert the search date to a range that includes the entire day
            sql.append(" AND p.PaymentDate >= ? AND p.PaymentDate < DATE_ADD(?, INTERVAL 1 DAY)");
        }
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            pstmt.setLong(1, userID);
            int index = 2;
            if (paymentID != null) {
                pstmt.setLong(index++, paymentID);
            }
            if (paymentDate != null && !paymentDate.isEmpty()) {
                // Set the search date for both lower and upper bounds of the range
                pstmt.setString(index, paymentDate);
                pstmt.setString(index + 1, paymentDate);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(new payment(
                        rs.getLong("PaymentID"),
                        rs.getDouble("PaymentAmount"),
                        rs.getString("PaymentMethod"),
                        rs.getString("PaymentDate"),
                        rs.getString("PaymentStatus"),
                        rs.getString("CardNumber")
                    ));
                }
            }
        }
        return payments;
    }
}
