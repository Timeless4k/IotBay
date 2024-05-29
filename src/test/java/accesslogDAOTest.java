import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.user;
import model.DAO.DBConnector;
import model.DAO.accesslogDAO;


public class accesslogDAOTest {
    private accesslogDAO accessLogDao;
    private Connection conn;
    private static long userIDCounter = 10000; // Starting user ID counter

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);
        accessLogDao = new accesslogDAO(conn);
        System.out.println("Setup complete: Database connected and DAO initialized.");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Starting cleanup: Attempting to roll back any changes.");
        try {
            if (conn != null) {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
                System.out.println("Cleanup complete: Connection closed, changes rolled back.");
            }
        } catch (SQLException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    private user createTestUser() {
        long userID = userIDCounter++;
        return new user(userID, "testemail" + userID + "@example.com", "hashedpassword", "John", "Q", "Public", "1980-01-01", "1234567890", "Male", "2024-01-01", "Customer", true);
    }

    @Test //#T0010
    public void testLogLogin() throws SQLException {
        user testUser = createTestUser();
        boolean result = accessLogDao.logLogin(testUser);
        assertTrue(result, "Logging login should succeed.");
        System.out.println("Login logging test passed: Login was logged successfully.");
    }

    @Test //#T0011
    public void testLogLogout() throws SQLException {
        user testUser = createTestUser();
        accessLogDao.logLogin(testUser); // Ensure there's a login to update with logout
        boolean result = accessLogDao.logLogout(testUser);
        assertTrue(result, "Logging logout should succeed.");
        System.out.println("Logout logging test passed: Logout was logged successfully.");
    }

    @Test //#T0012
    public void testGetLogsForUser() throws SQLException {
        user testUser = createTestUser();
        accessLogDao.logLogin(testUser);

        ResultSet rs = accessLogDao.getLogsForUser(testUser);
        assertNotNull(rs, "Should retrieve log entries for the user.");
        assertTrue(rs.next(), "Result set should have at least one entry for logged in user.");
        System.out.println("Retrieve logs test passed: Logs were retrieved successfully.");
    }
}
