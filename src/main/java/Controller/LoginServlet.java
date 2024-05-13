package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.accesslog;
import model.user;
import model.DAO.accesslogDAO;
import model.DAO.userDAO;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");
       
        // FOR THE ERRORS!
        if (conn == null) {
            System.err.println("Database connection error: Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        userDAO userDao = null;
        try {
            userDao = new userDAO(conn);
        } catch (SQLException e) {
            System.err.println("Error creating userDAO: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating userDAO");
            return;
        }

        accesslogDAO accesslogDAO = null;
        try {
            accesslogDAO = new accesslogDAO(conn);
        } catch (SQLException e) {
            System.err.println("Error creating accesslogDAO: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating accesslogDAO");
            return;
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            user user = userDao.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password) && user.getActivationStatus()) {
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
                response.sendRedirect("account.jsp#profile");
            } else {
                response.sendRedirect("login.jsp?error=invalid");
            }
        } finally {
            // try {
            //     conn.close();
            // } catch (SQLException e) {
            //     System.err.println("Error closing database connection: " + e.getMessage());
            // }
        }
    }
}
