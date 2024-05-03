package model.DAO;
import java.sql.*;

public class LoginDAO {
    private Connection conn;
    private PreparedStatement loginSt;
    private String loginQuery = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

    public LoginDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(true);
        loginSt = conn.prepareStatement(loginQuery);
    }

    /**
     * Checks if the given username and password are valid.
     * @param username The user's username
     * @param password The user's password
     * @return true if the username and password are valid, false otherwise
     * @throws SQLException If a database error occurs
     */
    public boolean validate(String username, String password) throws SQLException {
        loginSt.setString(1, username);
        loginSt.setString(2, password);
        ResultSet rs = loginSt.executeQuery();
        rs.next(); // Move to first row (the count)
        return rs.getInt(1) > 0; // Return true if count is greater than 0
    }
}
