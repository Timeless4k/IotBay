import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DAO.DBConnector;
import model.DAO.orderDAO;
import model.order;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class OrderDAOTest {
    private orderDAO orderDao;
    private Connection conn;
    
    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        // Setup DB connection
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);  // Start each test with a clean slate
        orderDao = new orderDAO(conn);
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
    public void testCreateOrder_Success() throws SQLException {
        // Example order data
        long orderID = orderDao.uniqueOrderID();
        boolean result = orderDao.createOrder(orderID, "TestOrder", "TestType", 10, "2024-05-10 10:00:00");
        assertTrue(result, "Order creation should be successful.");
        System.out.println("Order creation test passed.");
    }

    @Test
    public void testFetchOrders() throws SQLException {
        // Create example orders
        long orderID1 = orderDao.uniqueOrderID();
        long orderID2 = orderDao.uniqueOrderID();
        orderDao.createOrder(orderID1, "TestOrder1", "TestType1", 10, "2024-05-10 10:00:00");
        orderDao.createOrder(orderID2, "TestOrder2", "TestType2", 20, "2024-05-11 11:00:00");

        // Fetch all orders
        ArrayList<order> orders = orderDao.fetchOrders();
        assertNotNull(orders, "Orders list should not be null when orders exist.");
        assertTrue(orders.size() >= 2, "Should fetch at least 2 orders.");
        System.out.println("Fetch orders test passed: Found " + orders.size() + " orders.");
    }

    @Test
    public void testUpdateOrder_Success() throws SQLException {
        // Create example order
        long orderID = orderDao.uniqueOrderID();
        orderDao.createOrder(orderID, "TestOrder", "TestType", 10, "2024-05-10 10:00:00");

        // Update the order
        orderDao.updateOrder(orderID, "UpdatedOrder", "UpdatedType", 20, "2024-05-11 11:00:00");
        order updatedOrder = orderDao.getOrder(orderID);
        assertEquals("UpdatedOrder", updatedOrder.getOrderName());
        assertEquals("UpdatedType", updatedOrder.getOrderType());
        assertEquals(20, updatedOrder.getOrderQuantity());
        assertEquals("2024-05-11 11:00:00", updatedOrder.getOrderDate());
        System.out.println("Order update test passed.");
    }

    @Test
    public void testDeleteOrder_Success() throws SQLException {
        // Create example order
        long orderID = orderDao.uniqueOrderID();
        orderDao.createOrder(orderID, "TestOrder", "TestType", 10, "2024-05-10 10:00:00");

        // Delete the order
        orderDao.deleteOrder(orderID);
        assertFalse(orderDao.orderIDExists(orderID), "Order should be deleted.");
        System.out.println("Order deletion test passed.");
    }

    @Test
    public void testSearchOrderByOrderID() throws SQLException {
        // Create example order
        long orderID = orderDao.uniqueOrderID();
        orderDao.createOrder(orderID, "TestOrder", "TestType", 10, "2024-05-10 10:00:00");

        // Search by order ID
        ArrayList<order> orders = orderDao.searchOrderBy("OrderID", String.valueOf(orderID));
        assertTrue(orders.size() > 0, "Should find at least one order with the given ID.");
        assertEquals(orderID, orders.get(0).getOrderID());
        System.out.println("Order search by ID test passed.");
    }

    @Test
    public void testSearchOrderByOrderDate() throws SQLException {
        // Create example order
        long orderID = orderDao.uniqueOrderID();
        orderDao.createOrder(orderID, "TestOrder", "TestType", 10, "2024-05-10 10:00:00");

        // Search by order date
        ArrayList<order> orders = orderDao.searchOrderBy("OrderDate", "2024-05-10");
        assertTrue(orders.size() > 0, "Should find at least one order with the given date.");
        assertEquals("2024-05-10 10:00:00", orders.get(0).getOrderDate());
        System.out.println("Order search by date test passed.");
    }
}