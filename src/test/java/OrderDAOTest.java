import org.junit.jupiter.api.*;
import model.DAO.DBConnector;
import model.DAO.orderDAO;
import model.order;
import model.cart;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class OrderDAOTest {
    private orderDAO orderDao;
    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);
        orderDao = new orderDAO(conn);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.rollback();
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Test
    public void testCreateOrder() throws SQLException {
        List<cart> items = new ArrayList<>();
        items.add(new cart(1, 1, 2));
        order newOrder = new order(0, 1, LocalDate.now(), "Pending", 120.00, items, "123 Main St", "Credit Card");
        assertTrue(orderDao.createOrder(newOrder), "Should successfully create an order.");
    }

    @Test
    public void testReadOrder() throws SQLException {
        List<cart> items = new ArrayList<>();
        items.add(new cart(1, 1, 2));
        order expectedOrder = new order(1, 1, LocalDate.now(), "Pending", 120.00, items, "123 Main St", "Credit Card");
        orderDao.createOrder(expectedOrder);
        order fetchedOrder = orderDao.readOrder(1);
        assertNotNull(fetchedOrder, "Should fetch the order with specified ID.");
    }

    @Test
    public void testUpdateOrder() throws SQLException {
        List<cart> items = new ArrayList<>();
        items.add(new cart(1, 1, 2));
        order orderToUpdate = new order(1, 1, LocalDate.now(), "Pending", 150.00, items, "123 Main St", "Credit Card");
        orderDao.createOrder(orderToUpdate);
        orderToUpdate.setOrderStatus("Completed");
        assertTrue(orderDao.updateOrder(orderToUpdate), "Should successfully update the order.");
    }

    @Test
    public void testDeleteOrder() throws SQLException {
        List<cart> items = new ArrayList<>();
        items.add(new cart(1, 1, 2));
        order orderToDelete = new order(2, 1, LocalDate.now(), "Pending", 100.00, items, "123 Main St", "Credit Card");
        orderDao.createOrder(orderToDelete);
        assertTrue(orderDao.deleteOrder(orderToDelete.getOrderID()), "Should successfully delete the order.");
    }
}