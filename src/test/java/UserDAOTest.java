import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import model.DAO.DBConnector;
import model.DAO.userDAO;
import model.user;
import java.sql.Connection;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserDAOTest {
    private userDAO userDao;
    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);
        userDao = new userDAO(conn);
        System.out.println("Setup complete: Database connected and DAO initialized.");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        System.out.println("Starting cleanup: Attempting to roll back any changes.");
        conn.rollback();
        conn.setAutoCommit(true);
        conn.close();
        System.out.println("Cleanup complete: Connection closed and all changes rolled back.");
    }

    @Test
    public void testConnectionNotNull() {
        assertNotNull(conn, "Database connection should not be null");
        System.out.println("Connection test passed: Connection is not null.");
    }

    @Test
    public void testCreateUser() throws SQLException {
        user newUser = new user(111111,"testemail@example.com", "hashedpassword", "John", "Q", "Public", "1980-01-01", "1234567890", "Male", "2024-01-01", "Customer");
        boolean result = userDao.createUser(newUser);
        assertTrue(result, "User creation failed when it should have succeeded.");
        System.out.println("User creation test passed: User was successfully created.");

        user fetchedUser = userDao.getUserByEmail("testemail@example.com");
        assertNotNull(fetchedUser, "Failed to fetch the user after creation.");
        assertEquals("John", fetchedUser.getFirstName(), "Mismatch in the first name of the created user.");
        System.out.println("Fetch after creation test passed: User details match.");
    }

    @Test
    public void testReadUser() throws SQLException {
        user createdUser = new user(1111111,"readtest@example.com", "hashedpassword", "Jane", "D", "Doe", "1980-01-01", "987654321", "Female", "2024-01-01", "Customer");
        boolean creationResult = userDao.createUser(createdUser);
        assertTrue(creationResult, "Failed to create user for read operation.");
        System.out.println("User creation for read test passed.");

        user fetchedUser = userDao.getUserByEmail("readtest@example.com");
        assertNotNull(fetchedUser, "User read failed: user should be fetchable.");
        assertEquals("Jane", fetchedUser.getFirstName(), "Read user mismatch: first names do not match.");
        System.out.println("Read test passed: User details verified after fetch.");
    }

    @Test
    public void testUpdateUser() throws SQLException {
        user existingUser = new user(1111111,"updatetest@example.com", "hashedpassword", "Alice", "B", "Wonderland", "1980-01-01", "1234567890", "Female", "2024-01-01", "Customer");
        userDao.createUser(existingUser);
        existingUser.setLastName("UpdatedLast");

        boolean updated = userDao.updateUser(existingUser);
        assertTrue(updated, "User update failed: update should have succeeded.");
        System.out.println("Update test passed: User last name updated.");

        user updatedUser = userDao.getUserByEmail("updatetest@example.com");
        assertEquals("UpdatedLast", updatedUser.getLastName(), "Update user mismatch: last names do not match.");
        System.out.println("User last name update verification passed.");
    }

    @Test
    public void testDeleteUser() throws SQLException {
        user toBeDeletedUser = new user(1111111,"deletetest@example.com", "hashedpassword", "Bob", "C", "Builder", "1980-01-01", "1234567890", "Male", "2024-01-01", "Customer");
        userDao.createUser(toBeDeletedUser);
        System.out.println("User created for deletion test.");

        boolean deleted = userDao.deleteUser("deletetest@example.com");
        assertTrue(deleted, "User deletion failed: deletion should have succeeded.");
        System.out.println("Deletion test passed: User was successfully deleted.");

        user deletedUser = userDao.getUserByEmail("deletetest@example.com");
        assertNull(deletedUser, "Deleted user fetch test failed: user should not be found.");
        System.out.println("Verification of deletion passed: No user found post-deletion.");
    }
}