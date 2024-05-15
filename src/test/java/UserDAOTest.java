import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import model.user;
import model.DAO.DBConnector;
import model.DAO.userDAO;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserDAOTest {
    private userDAO userDao;
    private Connection conn;
    private static long userIDCounter = 10000; // Starting user ID counter

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        DBConnector connector = new DBConnector();
        conn = connector.openConnection();
        conn.setAutoCommit(false);
        userDao = new userDAO(conn);
        System.out.println("Setup complete: Database connected and DAO initialized.");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Starting cleanup: Attempting to roll back any changes and delete test case users.");
        try {
            if (conn != null) {
                // Delete test case users
                userDao.deleteUser("testemail@example.com");
                userDao.deleteUser("readtest@example.com");
                userDao.deleteUser("updatetest@example.com");
                userDao.deleteUser("deletetest@example.com");
                userDao.deleteUser("newuser@example.com");
                userDao.deleteUser("loginuser@example.com");
                userDao.deleteUser("failedlogin@example.com");
                userDao.deleteUser("duplicate@example.com");
               
               
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
                System.out.println("Cleanup complete: Connection closed, changes rolled back, and test case users deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        } finally {
            // Reset userIDCounter to its initial value
            userIDCounter = 10000;
        }
    }

    private long generateUniqueUserID() {
        return userIDCounter++;
    }

    @Test //#T0001
    public void testConnectionNotNull() {
        assertNotNull(conn, "Database connection should not be null");
        System.out.println("Connection test passed: Connection is not null.");
    }

    @Test //#T0002
    public void testCreateUser() throws SQLException {
        // Generating a unique user ID for testing
        long userID = generateUniqueUserID();
        user newUser = new user(userID, "testemail@example.com", "hashedpassword", "John", "Q", "Public", "1980-01-01", "1234567890", "Male", "2024-01-01", "Customer", true);

        // Start transaction
        conn.setAutoCommit(false);

        try {
            long result = userDao.createUser(newUser);
            assertNotEquals(-1, result, "User creation failed when it should have succeeded.");
            System.out.println("User creation test passed: User was successfully created.");

            user fetchedUser = userDao.getUserByEmail("testemail@example.com");
        assertNotNull(fetchedUser, "Failed to fetch the user after creation.");
        assertEquals("John", fetchedUser.getFirstName(), "Mismatch in the first name of the created user.");
        System.out.println("Fetch after creation test passed: User details match.");
    } finally {
        // Roll back transaction
        conn.rollback();
    }
}

    @Test //#T0003
    public void testReadUser() throws SQLException {
        // Manually assigning specific dummy user IDs
        user createdUser = new user(1002, "readtest@example.com", "hashedpassword", "Jane", "D", "Doe", "1980-01-01", "987654321", "Female", "2024-01-01", "Customer", true);
        userDao.createUser(createdUser);
        // Start transaction
        conn.setAutoCommit(false);
        user fetchedUser = userDao.getUserByEmail("readtest@example.com");
        assertNotNull(fetchedUser, "User read failed: user should be fetchable.");
        assertEquals("Jane", fetchedUser.getFirstName(), "Read user mismatch: first names do not match.");
        System.out.println("Read test passed: User details verified after fetch.");
        // Roll back transaction
        conn.rollback();
    }

    @Test //#T0004
    public void testUpdateUser() throws SQLException {
        // Manually assigning specific dummy user IDs
        user existingUser = new user(1003, "updatetest@example.com", "hashedpassword", "Alice", "B", "Wonderland", "1980-01-01", "1234567890", "Female", "2024-01-01", "Customer", true);
        userDao.createUser(existingUser);
        // Start transaction
        conn.setAutoCommit(false);
        existingUser.setLastName("UpdatedLast");
        boolean updated = userDao.updateUser(existingUser);
        assertTrue(updated, "User update failed: update should have succeeded.");
        System.out.println("Update test passed: User last name updated.");

        user updatedUser = userDao.getUserByEmail("updatetest@example.com");
        assertEquals("UpdatedLast", updatedUser.getLastName(), "Update user mismatch: last names do not match.");
        System.out.println("User last name update verification passed.");
        // Roll back transaction
        conn.rollback();
    }

    @Test //#T0005
    public void testDeleteUser() throws SQLException {
        // Manually assigning specific dummy user IDs
        user toBeDeletedUser = new user(1004, "deletetest@example.com", "hashedpassword", "Bob", "C", "Builder", "1980-01-01", "1234567890", "Male", "2024-01-01", "Customer", true);
        userDao.createUser(toBeDeletedUser);
        // Start transaction
        conn.setAutoCommit(false);
        boolean deleted = userDao.deleteUser("deletetest@example.com");
        assertTrue(deleted, "User deletion failed: deletion should have succeeded.");
        System.out.println("Deletion test passed: User was successfully deleted.");

        user deletedUser = userDao.getUserByEmail("deletetest@example.com");
        assertNull(deletedUser, "Deleted user fetch test failed: user should not be found.");
        System.out.println("Verification of deletion passed: No user found post-deletion.");
        // Roll back transaction
        conn.rollback();
    }

    @Test //#T0006
    public void testRegistrationSuccess() throws SQLException {
        long userID = generateUniqueUserID();
        user newUser = new user(userID, "newuser@example.com", "securepassword", "New", "User", "Example", "1990-01-01", "1234567890", "Other", "2024-01-01", "Customer");
        boolean result = userDao.createUser(newUser);
        assertTrue(result, "Registration should succeed.");
        user registeredUser = userDao.getUserByEmail("newuser@example.com");
        assertNotNull(registeredUser, "User should be retrievable post-registration.");
        System.out.println("Registration test passed: User registered successfully.");
    }

    @Test //#T0007
    public void testDuplicateEmailRegistration() throws SQLException {
        long userID1 = generateUniqueUserID();
        user firstUser = new user(userID1, "duplicate@example.com", "password1", "Duplicate", "Email", "User1", "1985-01-01", "987654321", "Male", "2024-01-01", "Customer", true);
        userDao.createUser(firstUser); // This should succeed
   
        long userID2 = generateUniqueUserID();
        user secondUser = new user(userID2, "duplicate@example.com", "password2", "Duplicate", "Email", "User2", "1986-02-02", "987654322", "Female", "2024-01-01", "Customer", true);
   
        assertThrows(SQLException.class, () -> userDao.createUser(secondUser), "Duplicate email registration should throw SQLException.");
    }

    @Test //#T0008
    public void testLoginSuccess() throws SQLException {
        long userID = generateUniqueUserID();
        user newUser = new user(userID, "loginuser@example.com", "loginpassword", "Login", "User", "Success", "1995-02-02", "1231231234", "Male", "2024-01-01", "Customer", true);
        userDao.createUser(newUser);
        user fetchedUser = userDao.getUserByEmail("loginuser@example.com");
        assertNotNull(fetchedUser, "User should not be null");
        assertEquals("loginpassword", fetchedUser.getPassword(), "Passwords should match.");
        System.out.println("Login test passed: User logged in successfully.");
    }
   
    @Test //#T0009
    public void testLoginFailure() throws SQLException {
        long userID = generateUniqueUserID();
        user newUser = new user(userID, "failedlogin@example.com", "rightpassword", "Failed", "Login", "User", "1988-03-03", "3213214321", "Female", "2024-01-01", "Customer", true);
        userDao.createUser(newUser);
        user fetchedUser = userDao.getUserByEmail("failedlogin@example.com");
        assertNotNull(fetchedUser, "User should not be null");
        assertNotEquals("wrongpassword", fetchedUser.getPassword(), "Passwords should not match.");
        System.out.println("Login failure test passed: User login failed as expected with wrong password.");
    }
}
