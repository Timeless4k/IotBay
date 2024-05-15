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
    private PreparedStatement getLogsByLoginDateSt;

    public accesslogDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(false); 
       
        // Prepare statements
        loginLogoutSt = conn.prepareStatement(
            "INSERT INTO accessdata (UserID, LoginTime, LogoutTime) VALUES (?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        getLogsForUserSt = conn.prepareStatement(
            "SELECT * FROM accessdata WHERE UserID = ? ORDER BY LoginTime DESC");
        getLogsByLoginDateSt = conn.prepareStatement(
            "SELECT * FROM accessdata WHERE UserID = ? AND DATE(LoginTime) = ? ORDER BY LoginTime DESC");
    }

    public boolean logLogin(user user) {
        try {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
            String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            loginLogoutSt.setLong(1, user.getuID());
            loginLogoutSt.setString(2, formattedDate);
            loginLogoutSt.setNull(3, Types.TIME);
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

    public boolean logLogout(user user) {
        try {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
            String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            loginLogoutSt.setLong(1, user.getuID());
            loginLogoutSt.setNull(2, Types.TIME);
            loginLogoutSt.setString(3, formattedDate);
    
            int affectedRows = loginLogoutSt.executeUpdate();
            if (affectedRows > 0) {
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Log logout failed: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Transaction rollback failed: " + ex.getMessage());
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

    public ResultSet getLogsByLoginDate(long userID, String loginDate) {
        try {
            getLogsByLoginDateSt.setLong(1, userID);
            getLogsByLoginDateSt.setString(2, loginDate);
            return getLogsByLoginDateSt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Get logs by login date failed: " + e.getMessage());
        }
        return null;
    }
}
