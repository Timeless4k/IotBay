package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.user;
import model.accesslog;
import model.DAO.userDAO;
import model.DAO.accesslogDAO;
import java.sql.Connection;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            System.err.println("Database connection error: Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return; // Stop further execution in case of error
        }

        userDAO userDao = null;
        try {
            userDao = new userDAO(conn);
        } catch (SQLException e) {
            System.err.println("Error creating userDAO: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating userDAO");
            return; // Stop further execution in case of error
        }

        accesslogDAO accesslogDAO = null;
        try {
            accesslogDAO = new accesslogDAO(conn);
        } catch (SQLException e) {
            System.err.println("Error creating accesslogDAO: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating accesslogDAO");
            return; // Stop further execution in case of error
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password"); // Assume passwords are hashed and compared securely

        try {
            user user = userDao.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) { // Simplified check, replace with hashed password check in practice
                accesslogDAO.logLogin(user);

                ResultSet accessLogsRs = accesslogDAO.getLogsForUser(user);
                List<accesslog> accessLogList = new ArrayList<>();
                try {
                    while (accessLogsRs.next()) {
                        accesslog accesslog = new accesslog();
                        accesslog.setlogID(accessLogsRs.getLong("logID"));
                        accesslog.setuserID(accessLogsRs.getLong("userID"));
                        if (accessLogsRs.getTimestamp("loginTime") == null) {
                            accesslog.setlogoutTime(accessLogsRs.getTimestamp("logoutTime").toString());
                        } else {
                            accesslog.setloginTime(accessLogsRs.getTimestamp("loginTime").toString());
                        }
                        accessLogList.add(accesslog);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                session.setAttribute("user", user);
                session.setAttribute("logs", accessLogList);
                response.sendRedirect("account.jsp");
            } else {
                response.sendRedirect("login.jsp?error=invalid");
            }
        } finally {
            // Add any necessary cleanup or error handling code here
            // try {
            //     conn.close();
            // } catch (SQLException e) {
            //     System.err.println("Error closing database connection: " + e.getMessage());
            // }
        }
    }
}
