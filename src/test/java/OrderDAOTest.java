import org.junit.jupiter.api.*;
import model.DAO.DBConnector;
import model.DAO.orderDAO;
import model.order;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

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
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.rollback(); // Roll back any changes made during the tests
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Test
    public void testCreateOrder() throws SQLException {
        order newOrder = new order(0, 1, java.time.LocalDate.now(), "Pending", 120.00);
        assertTrue(orderDao.createOrder(newOrder), "Should successfully create an order.");
    }

    @Test
    public void testReadOrder() throws SQLException {
        order expectedOrder = new order(1, 1, java.time.LocalDate.now(), "Pending", 120.00);
        orderDao.createOrder(expectedOrder);
        order fetchedOrder = orderDao.readOrder(1);
        assertNotNull(fetchedOrder, "Should fetch the order with specified ID.");
    }

    @Test
    public void testUpdateOrder() throws SQLException {
        order orderToUpdate = new order(1, 1, java.time.LocalDate.now(), "Pending", 150.00);
        orderDao.createOrder(orderToUpdate);
        orderToUpdate.setOrderStatus("Completed");
        assertTrue(orderDao.updateOrder(orderToUpdate), "Should successfully update the order.");
    }

    @Test
    public void testDeleteOrder() throws SQLException {
        order orderToDelete = new order(2, 1, java.time.LocalDate.now(), "Pending", 100.00);
        orderDao.createOrder(orderToDelete);
        assertTrue(orderDao.deleteOrder(orderToDelete.getOrderID()), "Should successfully delete the order.");
    }
}