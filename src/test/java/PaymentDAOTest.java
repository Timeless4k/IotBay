import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.google.protobuf.Method;

import model.DAO.DBConnector;
import model.DAO.paymentDAO;
import model.payment;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PaymentDAOTest {
    private paymentDAO paymentDao;
    private Connection conn;
    
    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        // Setup DB connection
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);  // Start each test with a clean slate
        paymentDao = new paymentDAO(conn);
        System.out.println("Setup complete: Database connected and DAO initialized.");
    }

    @AfterEach
        public void tearDown() throws SQLException {
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Cleanup complete: Changes rolled back and connection closed.");
                } catch (SQLException e) {
                    System.err.println("Error during cleanup: " + e.getMessage());
                }
            }
        }
    

    @Test
    public void testCreatePayment_Success() throws SQLException {
        // Example payment data
        payment pay = new payment(0, 150.0, "Card", "2024-05-10", "Approved", "8111111111");
        
        boolean result = paymentDao.createPayment(pay);
        assertTrue(result, "Payment creation should be successful.");
        System.out.println("Payment creation test passed.");
    }

    @Test
    public void testGetPaymentsForUser() throws SQLException {
        System.out.println("Setup complete: Database connected and DAO initialized.");

        // Example user ID
        long userID = 1111111111;

        // Create example payments
        payment pay1 = new payment(65465465, 100.0, "Card", "2024-05-10", "Approved", "8111111111");
        payment pay2 = new payment(65464646, 200.0, "Card", "2024-05-11", "Approved", "8111111111");

        // Ensure payments are created
        assertTrue(paymentDao.createPayment(pay1), "First payment creation failed.");
        assertTrue(paymentDao.createPayment(pay2), "Second payment creation failed.");

        // Fetch payments for the user and process the ResultSet within the try block
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM payments WHERE CardID IN (SELECT CardID FROM card WHERE UserID = ?)")) {
            pstmt.setLong(1, userID);
            ResultSet results = pstmt.executeQuery();

            assertNotNull(results, "ResultSet should not be null when payments exist.");

            // Count the number of payments retrieved
            int count = 0;
            while (results.next()) {
                count++;
            }

            // Assert that the expected number of payments were fetched
            assertTrue(count >= 2, "Should fetch at least 2 payments for the user.");
            System.out.println("Get payments for user test passed: Found " + count + " payments.");
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
            fail("An exception should not have been thrown");
        }
    }

    @Test
    public void testGenerateUniquePaymentID() throws Exception {
        java.lang.reflect.Method method = paymentDAO.class.getDeclaredMethod("generateUniquePaymentID");
        method.setAccessible(true);
        
        long paymentID1 = (long) method.invoke(paymentDao);
        long paymentID2 = (long) method.invoke(paymentDao);
        
        assertNotEquals(paymentID1, paymentID2, "Each generated payment ID should be unique.");
    }

    
}
