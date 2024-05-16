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

    private orderDAO ODAO;
    private Connection conn;
    public DBConnector connector;

    @BeforeEach
    public void intODAO() throws SQLException, ClassNotFoundException{
        if(ODAO == null) {
            connector = new DBConnector();
            conn = connector.openConnection();
            ODAO = new orderDAO(conn);
        }
        conn.setAutoCommit(false);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }

    @Test
    public void connTest() throws SQLException,ClassNotFoundException{
        intODAO();
        assertNotNull(conn, "Connection does not exist");
    }

    @Test
    public void readtest() throws SQLException, ClassNotFoundException{
        intODAO();
        ArrayList<order> testlist = ODAO.fetchOrders();
        assertTrue(testlist.size() >= 1, "Table is empty");
    }

    @Test
    public void createTest() throws SQLException, ClassNotFoundException{
        intODAO();
        assertTrue(ODAO.createOrder("MacBook Sucks","Laptop",1, "2023-01-03 12:00:00"));
    }

    @Test
    public void createWrongEntry() throws SQLException, ClassNotFoundException {
        intODAO();
        // Negative quantity
        assertThrows(SQLException.class, () -> {
            ODAO.createOrder("Invalid Order", "Laptop", -1, "2023-01-03 12:00:00");
        });
        // Null order name
        assertThrows(SQLException.class, () -> {
            ODAO.createOrder(null, "Laptop", 1, "2023-01-03 12:00:00");
        });
        // Invalid date format
        assertThrows(SQLException.class, () -> {
            ODAO.createOrder("Invalid Order", "Laptop", 1, "invalid-date-format");
        });
    }

    @Test
    public void deleteTest() throws SQLException, ClassNotFoundException {
        intODAO();
        String orderName = "Test Order";
        String orderType = "Laptop";
        long orderQuantity = 1;
        String orderDate = "2023-01-03 12:00:00";
        // Create the order and retrieve its ID
        boolean created = ODAO.createOrder(orderName, orderType, orderQuantity, orderDate);
        assertTrue(created, "Order creation failed");
        // Fetch orders and get the ID of the newly created order
        ArrayList<order> before = ODAO.fetchOrders();
        long orderID = 0;
        for (order o : before) {
            if (o.getOrderName().equals(orderName) && o.getOrderType().equals(orderType)
                    && o.getOrderQuantity() == orderQuantity && o.getOrderDate().equals(orderDate)) {
                orderID = o.getOrderID();
                break;
            }
        }
        // Ensure we have a valid order ID
        assertTrue(orderID != 0, "Failed to retrieve order ID");
        // Delete the order
        ODAO.deleteOrder(orderID);
        // Fetch orders after deletion
        ArrayList<order> after = ODAO.fetchOrders();
        assertTrue(before.size() > after.size(), "Delete operation failed");
    }

    @Test
    public void updateTest() throws SQLException, ClassNotFoundException {
        intODAO();
        String orderName = "Test Order";
        String orderType = "Laptop";
        long orderQuantity = 1;
        String orderDate = "2023-01-03 12:00:00";
        // Create the order and retrieve its ID
        boolean created = ODAO.createOrder(orderName, orderType, orderQuantity, orderDate);
        assertTrue(created, "Order creation failed");
        // Fetch orders and get the ID of the newly created order
        ArrayList<order> before = ODAO.fetchOrders();
        long orderID = 0;
        for (order o : before) {
            if (o.getOrderName().equals(orderName) && o.getOrderType().equals(orderType)
                    && o.getOrderQuantity() == orderQuantity && o.getOrderDate().equals(orderDate)) {
                orderID = o.getOrderID();
                break;
            }
        }
        // Ensure we have a valid order ID
        assertTrue(orderID != 0, "Failed to retrieve order ID");
        // Update the order
        ODAO.updateOrder(orderID, "Updated Order", "Laptop", 2, "2023-01-04 12:00:00");
        order updatedOrder = ODAO.getOrder(orderID);
        assertEquals("Updated Order", updatedOrder.getOrderName(), "Order update failed");
    }

    @Test
    public void getOrderTest() throws SQLException, ClassNotFoundException {
        intODAO();
        String orderName = "Test Order";
        String orderType = "Laptop";
        long orderQuantity = 1;
        String orderDate = "2023-01-03 12:00:00";
        // Create the order and retrieve its ID
        boolean created = ODAO.createOrder(orderName, orderType, orderQuantity, orderDate);
        assertTrue(created, "Order creation failed");
        // Fetch orders and get the ID of the newly created order
        ArrayList<order> before = ODAO.fetchOrders();
        long orderID = 0;
        for (order o : before) {
            if (o.getOrderName().equals(orderName) && o.getOrderType().equals(orderType)
                    && o.getOrderQuantity() == orderQuantity && o.getOrderDate().equals(orderDate)) {
                orderID = o.getOrderID();
                break;
            }
        }
        // Ensure we have a valid order ID
        assertTrue(orderID != 0, "Failed to retrieve order ID");
        // Retrieve the order using getOrder method
        order retrievedOrder = ODAO.getOrder(orderID);
        assertEquals(orderName, retrievedOrder.getOrderName(), "Failed to retrieve the correct order");
    }

    @Test
    public void searchOrderByTest() throws SQLException, ClassNotFoundException {
        intODAO();
        String orderName = "Search Test Order";
        String orderType = "Laptop";
        long orderQuantity = 1;
        String orderDate = "2023-01-03 12:00:00";
        // Create the order and retrieve its ID
        boolean created = ODAO.createOrder(orderName, orderType, orderQuantity, orderDate);
        assertTrue(created, "Order creation failed");
        // Fetch orders and get the ID of the newly created order
        ArrayList<order> before = ODAO.fetchOrders();
        long orderID = 0;
        for (order o : before) {
            if (o.getOrderName().equals(orderName) && o.getOrderType().equals(orderType)
                    && o.getOrderQuantity() == orderQuantity && o.getOrderDate().equals(orderDate)) {
                orderID = o.getOrderID();
                break;
            }
        }
        // Ensure we have a valid order ID
        assertTrue(orderID != 0, "Failed to retrieve order ID");
        // Search for the order using the OrderID
        ArrayList<order> searchResults = ODAO.searchOrderBy("OrderID", String.valueOf(orderID));
        assertTrue(searchResults.size() > 0, "Search operation failed");
        assertEquals(orderName, searchResults.get(0).getOrderName(), "Failed to retrieve the correct order in search");
    }
}