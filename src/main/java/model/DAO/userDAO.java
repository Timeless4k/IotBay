package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BooleanSupplier;

import model.user;

public class userDAO {
    private Connection conn;
    private PreparedStatement createUserSt;
    private PreparedStatement getUserByEmailSt;
    private PreparedStatement updateUserSt;
    private PreparedStatement deleteUserSt;
    private PreparedStatement checkUserIDExistsSt;

    /**
     * Constructor to initialize the userDAO with a database connection and prepare statements.
     * 
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */
    public userDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(false);  

        createUserSt = conn.prepareStatement(
            "INSERT INTO User (UserID, UserFirstName, UserMiddleName, UserLastName, UserType, UserEmail, UserPhone, UserGender, PasswordHash, UserCreationDate, ActivationFlag, VerificationCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        getUserByEmailSt = conn.prepareStatement("SELECT * FROM User WHERE UserEmail = ?");
        updateUserSt = conn.prepareStatement(
            "UPDATE User SET UserFirstName = ?, UserMiddleName = ?, UserLastName = ?, UserType = ?, UserPhone = ?, UserGender = ?, PasswordHash = ?, UserCreationDate = ?, ActivationFlag = ?, VerificationCode = ? WHERE UserEmail = ?");
        deleteUserSt = conn.prepareStatement("DELETE FROM User WHERE UserEmail = ?");
        checkUserIDExistsSt = conn.prepareStatement("SELECT COUNT(*) FROM User WHERE UserID = ?");
    }

    /**
     * Generates a unique UserID.
     * 
     * @return a unique UserID
     * @throws SQLException if a database access error occurs
     */
    public long generateUniqueUserID() throws SQLException {
        Random rand = new Random();
        long userID = Math.abs(rand.nextLong());
        while (userIDExists(userID)) {
            userID = Math.abs(rand.nextLong());
        }
        return userID;
    }

    /**
     * Checks if a UserID already exists in the database.
     * 
     * @param userID the UserID to check
     * @return true if the UserID exists, false otherwise
     * @throws SQLException if a database access error occurs
     */
    private boolean userIDExists(long userID) throws SQLException {
        checkUserIDExistsSt.setLong(1, userID);
        ResultSet rs = checkUserIDExistsSt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    /**
     * Retrieves a user by their email address from the database.
     * This method retrieves and returns user information including the user ID.
     * 
     * @param email the email address of the user to retrieve
     * @return the user object if found, or null if not found
     */
    // Retrieve a user by email along with activation status
    public user getUserByEmail(String email) {
        try {
            getUserByEmailSt.setString(1, email);
            ResultSet rs = getUserByEmailSt.executeQuery();
            if (rs.next()) {
                user usr = extractUserFromResultSet(rs);
                usr.setActivationStatus(rs.getBoolean("ActivationFlag")); // Set activation status from the result set
                return usr;
            }
        } catch (SQLException e) {
            System.err.println("Fetch user failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Creates a new user in the database.
     * 
     * @param newUser the user object containing user details
     * @return the unique user ID if the user was successfully created, otherwise -1
     * @throws SQLException if a database access error occurs
     */
    public long createUser(user newUser) throws SQLException {
        // First, check if the email already exists
        getUserByEmailSt.setString(1, newUser.getEmail());
        ResultSet rs = getUserByEmailSt.executeQuery();
        if (rs.next()) {
            throw new SQLException("Duplicate email registration");
        }
   
        try {
            long uniqueUserID = generateUniqueUserID(); // Generate a unique UserID
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
            String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            createUserSt.setLong(1, uniqueUserID);
            createUserSt.setString(2, newUser.getFirstName());
            createUserSt.setString(3, newUser.getMiddleName());
            createUserSt.setString(4, newUser.getLastName());
            createUserSt.setString(5, newUser.getuType());
            createUserSt.setString(6, newUser.getEmail());
            createUserSt.setString(7, newUser.getMobilePhone());
            createUserSt.setString(8, newUser.getGender());
            createUserSt.setString(9, newUser.getPassword());  // Password should be hashed
            createUserSt.setString(10, formattedDate); // Setting the current Sydney date and time
            createUserSt.setString(11, "1"); // Assuming activation flag is set to active
            createUserSt.setString(12, "");  // Assuming verification code is not needed initially
            int rowsAffected = createUserSt.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit(); // Commit the transaction after successful insertion
                return uniqueUserID;
            }
        } catch (SQLException e) {
            System.err.println("Create user failed: " + e.getMessage());
            conn.rollback();
            throw e;  // Re-throw the exception to be caught by the servlet or controller
        }
        return -1;
    }

    /**
     * Updates an existing user in the database.
     * 
     * @param user the user object with updated details
     * @return true if the user was successfully updated, false otherwise
     */
    public boolean updateUser(user user) {
        try {
            updateUserSt.setString(1, user.getFirstName());
            updateUserSt.setString(2, user.getMiddleName());
            updateUserSt.setString(3, user.getLastName());
            updateUserSt.setString(4, user.getuType());
            updateUserSt.setString(5, user.getMobilePhone());
            updateUserSt.setString(6, user.getGender());
            updateUserSt.setString(7, user.getPassword());
            updateUserSt.setString(8, user.getCreationDate());
            updateUserSt.setBoolean(9, user.getActivationStatus());  // Assuming the activation status is stored in the user object
            updateUserSt.setString(10, ""); // Assuming no changes to VerificationCode
            updateUserSt.setString(11, user.getEmail());
            int rowsAffected = updateUserSt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Update user failed: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
        }
        return false;
    }

    /**
     * Deletes a user from the database by their email address.
     * 
     * @param email the email address of the user to delete
     * @return true if the user was successfully deleted, false otherwise
     */
    public boolean deleteUser(String email) {
        try {
            deleteUserSt.setString(1, email);
            int rowsAffected = deleteUserSt.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit(); // Commit the transaction after successful deletion
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Delete user failed: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
        }
        return false;
    }

    /**
     * Retrieves a list of all users from the database.
     * 
     * @return a list of all users
     * @throws SQLException if a database access error occurs
     */
    public List<user> getAllUsers() throws SQLException {
        List<user> userList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM User");
            rs = stmt.executeQuery();
            while (rs.next()) {
                user usr = extractUserFromResultSet(rs);
                userList.add(usr);
                System.out.println("User found: " + usr.getFirstName()); // Debugging line
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return userList;
    }

    private user extractUserFromResultSet(ResultSet rs) throws SQLException {
        user usr = new user();
        usr.setuID(rs.getLong("UserID"));
        usr.setEmail(rs.getString("UserEmail"));
        usr.setPassword(rs.getString("PasswordHash"));
        usr.setFirstName(rs.getString("UserFirstName"));
        usr.setMiddleName(rs.getString("UserMiddleName"));
        usr.setLastName(rs.getString("UserLastName"));
        usr.setuType(rs.getString("UserType"));
        usr.setMobilePhone(Long.toString(rs.getLong("UserPhone")));
        usr.setGender(rs.getString("UserGender"));
        usr.setCreationDate(rs.getString("UserCreationDate"));
        return usr;
    }


    public BooleanSupplier validateLogin(String string, String string2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateLogin'");
    }


    public List<user> searchUsersByFullNameAndPhone(String fullName, String phoneNumber) throws SQLException {
        List<user> userList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM User WHERE 1=1");

            if (fullName != null && !fullName.isEmpty()) {
                queryBuilder.append(" AND CONCAT(UserFirstName, ' ', UserMiddleName, ' ', UserLastName) LIKE ?");
            }

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                queryBuilder.append(" AND UserPhone LIKE ?");
            }

            stmt = conn.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;

            if (fullName != null && !fullName.isEmpty()) {
                stmt.setString(parameterIndex++, "%" + fullName + "%");
            }

            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                stmt.setString(parameterIndex++, "%" + phoneNumber + "%");
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                user usr = extractUserFromResultSet(rs);
                userList.add(usr);
                System.out.println("User found: " + usr.getFirstName()); // Debugging line
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return userList;
    }
   
}
