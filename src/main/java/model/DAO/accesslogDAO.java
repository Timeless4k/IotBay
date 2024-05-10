package model.DAO;

import java.sql.*;

import model.user;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class accesslogDAO {
    private Connection conn;
    private PreparedStatement loginLogoutSt;
    private PreparedStatement getLogsForUserSt;

    public accesslogDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(false);  // Start with transaction block
       
        // Prepare statements
        loginLogoutSt = conn.prepareStatement(
            "INSERT INTO accessdata (UserID, LoginTime, LogoutTime) VALUES (?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        getLogsForUserSt = conn.prepareStatement(
            "SELECT * FROM accessdata WHERE UserID = ? ORDER BY LoginTime DESC");
    }

    public boolean logLogin(user user) {
        try {
            // ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
            // String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Date date = new Date(System.currentTimeMillis());

            loginLogoutSt.setLong(1, user.getuID());
            loginLogoutSt.setDate(2, date);
            loginLogoutSt.setDate(3, date);
            int rowsAffected = loginLogoutSt.executeUpdate();
            conn.commit();
            System.out.println("logLogin " + user.getuID() + " rowsAffected=" + rowsAffected);
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Create log failed: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
        }
        return false;
    }

    public ResultSet getLogsForUser(user user) {
        try {
            getLogsForUserSt.setLong(1, user.getuID());
            return getLogsForUserSt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get logs failed: " + e.getMessage());
        }
        return null;
    }
}
