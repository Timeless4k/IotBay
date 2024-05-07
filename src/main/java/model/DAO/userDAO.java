package model.DAO;

import java.sql.*;
import model.user;

public class userDAO {
    private Connection conn;
    private PreparedStatement createUserSt;
    private PreparedStatement getUserByEmailSt;
    private PreparedStatement updateUserSt;
    private PreparedStatement deleteUserSt;

    public userDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(false);  // Start with transaction block
        createUserSt = conn.prepareStatement(
            "INSERT INTO User (UserID, UserFirstName, UserMiddleName, UserLastName, UserType, UserEmail, UserPhone, UserGender, PasswordHash, UserCreationDate, ActivationFlag, VerificationCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        getUserByEmailSt = conn.prepareStatement("SELECT * FROM User WHERE UserEmail = ?");
        updateUserSt = conn.prepareStatement(
            "UPDATE User SET UserFirstName = ?, UserMiddleName = ?, UserLastName = ?, UserType = ?, UserPhone = ?, UserGender = ?, PasswordHash = ?, UserCreationDate = ?, ActivationFlag = ?, VerificationCode = ? WHERE UserEmail = ?");
        deleteUserSt = conn.prepareStatement("DELETE FROM User WHERE UserEmail = ?");
    }

    public boolean createUser(user newUser) {
        try {
            createUserSt.setLong(1, newUser.getuID());
            createUserSt.setString(2, newUser.getFirstName());
            createUserSt.setString(3, newUser.getMiddleName());
            createUserSt.setString(4, newUser.getLastName());
            createUserSt.setString(5, newUser.getuType());
            createUserSt.setString(6, newUser.getEmail());
            createUserSt.setString(7, newUser.getMobilePhone());
            createUserSt.setString(8, newUser.getGender());
            createUserSt.setString(9, newUser.getPassword());
            createUserSt.setString(10, newUser.getCreationDate());
            createUserSt.setString(11, "0");
            createUserSt.setString(12, "");
            int rowsAffected = createUserSt.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Create user failed: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
        }
        return false;
    }

    public user getUserByEmail(String email) {
        try {
            getUserByEmailSt.setString(1, email);
            ResultSet rs = getUserByEmailSt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Fetch user failed: " + e.getMessage());
        }
        return null;
    }

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
            updateUserSt.setString(9, "0");  // Assuming no changes to ActivationFlag and VerificationCode
            updateUserSt.setString(10, ""); // Assuming no changes to ActivationFlag and VerificationCode
            updateUserSt.setString(11, user.getEmail());
            int rowsAffected = updateUserSt.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit();
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

    public boolean deleteUser(String email) {
        try {
            deleteUserSt.setString(1, email);
            int rowsAffected = deleteUserSt.executeUpdate();
            if (rowsAffected > 0) {
                conn.commit();
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
}
