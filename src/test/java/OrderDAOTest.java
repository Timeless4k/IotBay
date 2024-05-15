import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;

import model.DAO.DBConnector;
import model.DAO.orderDAO;
import model.order;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDAOTest {

    public DBConnector connector;
    public Connection conn;
    public orderDAO ODAO;

    public void intODAO() throws SQLException,ClassNotFoundException{
        if(ODAO == null) {
            connector = new DBConnector();
            conn = connector.openConnection();
            ODAO = new orderDAO(conn);
        }
        conn.setAutoCommit(false);
    }

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        // Setup DB connection
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);  // Start each test with a clean slate
        ODAO = new orderDAO(conn);
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
    public void connTest() throws SQLException, ClassNotFoundException {
        intODAO();
        assertNotNull(conn, "Connection does not exist");
    }

    @Test
    public void readTest() throws SQLException, ClassNotFoundException {
        intODAO();
        ArrayList<order> testList = ODAO.fetchOrders();
        assertTrue(testList.size() >= 1, "Table is empty");
    }

    @Test
    public void createTest() throws SQLException, ClassNotFoundException {
        intODAO();
        assertTrue(ODAO.createOrder(9999, "TestOrder", "TestType", 10, "2024-01-01 10:00:00"));
    }

    @Test
    public void deleteTest() throws SQLException, ClassNotFoundException {
        intODAO();
        ODAO.createOrder(9999, "TestOrder", "TestType", 10, "2024-01-01 10:00:00");
        ArrayList<order> before = ODAO.fetchOrders();
        ODAO.deleteOrder(9999);
        ArrayList<order> after = ODAO.fetchOrders();
        assertTrue(before.size() > after.size());
    }

    @Test
    public void updateTest() throws SQLException, ClassNotFoundException {
        intODAO();
        ODAO.createOrder(9999, "TestOrder", "TestType", 10, "2024-01-01 10:00:00");
        ODAO.updateOrder(9999, "UpdatedOrder", "UpdatedType", 20, "2024-01-02 10:00:00");
        ArrayList<order> test = ODAO.searchOrderBy("OrderName", "UpdatedOrder");
        assertTrue(test.size() > 0);
    }

    @Test
    public void searchTest() throws SQLException, ClassNotFoundException {
        intODAO();
        ArrayList<order> test = ODAO.searchOrderBy("OrderName", "Test");
        for (order o : test) {
            System.out.println(o.getOrderName());
        }
        assertTrue(test.size() > 0);
    }

    @Test
    public void genIDTest() throws SQLException, ClassNotFoundException {
        intODAO();
        assertTrue(ODAO.orderIDExists(99999999)); // does not exist
        assertFalse(ODAO.orderIDExists(999999999)); // does exist
    }
}