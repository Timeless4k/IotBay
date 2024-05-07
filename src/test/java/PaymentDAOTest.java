import model.payment;
import model.DAO.DBConnector;
import model.DAO.paymentDAO;
import model.DAO.userDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentDAOTest {
    private Connection conn;
    private paymentDAO paymentDAO;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);  // Transaction control starts
        paymentDAO = new paymentDAO(conn);
        System.out.println("Setup complete: Database connected and DAO initialized.");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback(); // Rollback transaction to avoid saving test data
        conn.close();
    }

    @Test
    public void testCreatePayment() throws SQLException {
        payment payment = new payment(0, 100.0, "Credit Card", "2024-01-01", "Pending");
        boolean isCreated = paymentDAO.createPayment(payment);
        assertTrue(isCreated, "payment should be created successfully");

        payment fetchedpayment = paymentDAO.readPayment(payment.getPaymentID());
        assertNotNull(fetchedpayment, "payment should be readable after creation");
        assertEquals(100.0, fetchedpayment.getAmount(), "Amount should match");
    }

    @Test
    public void testReadPayment() throws SQLException {
        // Assuming an existing payment with ID 1
        payment payment = paymentDAO.readPayment(1);
        assertNotNull(payment, "Payment should exist");
    }

    @Test
    public void testUpdatePayment() throws SQLException {
        // Assuming an existing payment with ID 1
        payment payment = new payment(1, 150.0, "Debit Card", "2024-01-02", "Completed");
        boolean isUpdated = paymentDAO.updatePayment(payment);
        assertTrue(isUpdated, "Payment update should be successful");

        payment updatedPayment = paymentDAO.readPayment(1);
        assertEquals("Completed", updatedPayment.getStatus(), "Status should be updated");
    }

    @Test
    public void testDeletePayment() throws SQLException {
        // Assuming an existing payment with ID 1
        boolean isDeleted = paymentDAO.deletePayment(1);
        assertTrue(isDeleted, "Payment should be deletable");

        payment deletedPayment = paymentDAO.readPayment(1);
        assertNull(deletedPayment, "Payment should be null after deletion");
    }
}
