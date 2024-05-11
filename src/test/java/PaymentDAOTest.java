// import model.payment;
// import model.DAO.DBConnector;
// import model.DAO.paymentDAO;
// import model.DAO.userDAO;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class PaymentDAOTest {
//     private Connection conn;
//     private paymentDAO paymentDAO;

//     @BeforeAll
//     public void setUp() throws SQLException, ClassNotFoundException {
//         DBConnector connector = new DBConnector();
//         conn = connector.openConnection();
//         conn.setAutoCommit(false);  // Transaction control starts
//         paymentDAO = new paymentDAO(conn);
//         System.out.println("Setup complete: Database connected and DAO initialized.");
//     }

//     @AfterAll
//     public void tearDown() throws SQLException {
//         conn.rollback(); // Rollback transaction to avoid saving test data
//         conn.close();
//     }

//     @Test
//     @Order(1)
//     public void testConnectionNotNull() {
//         assertNotNull(conn, "Database connection should not be null");
//         System.out.println("Connection test passed: Connection is not null.");
//     }

//     @Test
//     @Order(2)
//     public void testCreatePayment() throws SQLException {
//         System.out.println("Starting testCreatePayment...");
//         payment payment = new payment(1, 100.0, "Credit Card", "2024-01-01", "Pending");
//         boolean isCreated = paymentDAO.createPayment(payment);
//         assertTrue(isCreated, "payment should be created successfully");

//         payment fetchedPayment = paymentDAO.readPayment(payment.getPaymentID());
//         assertNotNull(fetchedPayment, "payment should be readable after creation");
//         assertEquals(100.0, fetchedPayment.getAmount(), "Amount should match");

//         System.out.println("testCreatePayment completed successfully.");
//     }

//     @Test
//     @Order(3)
//     public void testReadPayment() throws SQLException {
//         System.out.println("Starting testReadPayment...");
//         try {
//             // Assuming an existing payment with ID 1
//             payment payment = paymentDAO.readPayment(1);
//             if (payment != null) {
//                 System.out.println("Payment details retrieved successfully:");
//                 System.out.println("Payment ID: " + payment.getPaymentID());
//                 System.out.println("Amount: " + payment.getAmount());
//                 System.out.println("Method: " + payment.getMethod());
//                 System.out.println("Date: " + payment.getDate());
//                 System.out.println("Status: " + payment.getStatus());
//                 System.out.println("testReadPayment completed successfully.");
//             } else {
//                 System.out.println("Error: No payment found with ID 1");
//                 fail("Payment should exist");
//             }
//         } catch (SQLException ex) {
//             System.out.println("Error occurred during testReadPayment: " + ex.getMessage());
//             fail("SQLException occurred: " + ex.getMessage());
//         }
//     }

//     @Test
//     @Order(4)
//     public void testUpdatePayment() throws SQLException {
//         System.out.println("Starting testUpdatePayment...");
//         // Assuming an existing payment with ID 1
//         payment payment = new payment(1, 150.0, "Debit Card", "2024-01-02", "Approved");
//         boolean isUpdated = paymentDAO.updatePayment(payment);
//         assertTrue(isUpdated, "Payment update should be successful");

//         payment updatedPayment = paymentDAO.readPayment(1);
//         assertEquals("Approved", updatedPayment.getStatus(), "Status should be updated");
//         System.out.println("testUpdatePayment completed successfully.");
//     }

//     @Test
//     @Order(5)
//     public void testDeletePayment() throws SQLException {
//         System.out.println("Starting testDeletePayment...");
//         // Assuming an existing payment with ID 1
//         boolean isDeleted = paymentDAO.deletePayment(1);
//         assertTrue(isDeleted, "Payment should be deletable");

//         payment deletedPayment = paymentDAO.readPayment(1);
//         assertNull(deletedPayment, "Payment should be null after deletion");
//         System.out.println("testDeletePayment completed successfully.");
//     }
// }
