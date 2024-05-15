import org.junit.jupiter.api.*;
import model.DAO.DBConnector;
import model.DAO.OrderDAO;
import model.Order;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class OrderDAOTest {
    private OrderDAO orderDao;
    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);
        orderDao = new OrderDAO(conn);
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
        Order newOrder = new Order(0, 1, java.time.LocalDate.now(), "Pending", 120.00);
        assertTrue(orderDao.createOrder(newOrder), "Should successfully create an order.");
    }

    @Test
    public void testReadOrder() throws SQLException {
        Order expectedOrder = new Order(1, 1, java.time.LocalDate.now(), "Pending", 120.00);
        orderDao.createOrder(expectedOrder);
        Order fetchedOrder = orderDao.readOrder(1);
        assertNotNull(fetchedOrder, "Should fetch the order with specified ID.");
    }

    @Test
    public void testUpdateOrder() throws SQLException {
        Order orderToUpdate = new Order(1, 1, java.time.LocalDate.now(), "Pending", 150.00);
        orderDao.createOrder(orderToUpdate);
        orderToUpdate.setOrderStatus("Completed");
        assertTrue(orderDao.updateOrder(orderToUpdate), "Should successfully update the order.");
    }

    @Test
    public void testDeleteOrder() throws SQLException {
        Order orderToDelete = new Order(2, 1, java.time.LocalDate.now(), "Pending", 100.00);
        orderDao.createOrder(orderToDelete);
        assertTrue(orderDao.deleteOrder(orderToDelete.getOrderID()), "Should successfully delete the order.");
    }
}