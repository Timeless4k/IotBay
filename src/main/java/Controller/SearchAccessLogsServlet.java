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

@WebServlet("/SearchAccessLogsServlet")
public class SearchAccessLogsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");
        user loggedInUser = (user) session.getAttribute("user");
       
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        accesslogDAO accesslogDAO = null;
        try {
            accesslogDAO = new accesslogDAO(conn);
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating accesslogDAO");
            return;
        }

        String loginDate = request.getParameter("loginDate");

        try {
            ResultSet accessLogsRs = accesslogDAO.getLogsByLoginDate(loggedInUser.getuID(), loginDate);
            List<accesslog> accessLogList = new ArrayList<>();
            while (accessLogsRs.next()) {
                accesslog accesslog = new accesslog();
                accesslog.setlogID(accessLogsRs.getLong("logID"));
                accesslog.setuserID(accessLogsRs.getLong("userID"));
                accesslog.setloginTime(accessLogsRs.getTimestamp("loginTime").toString());
                accesslog.setlogoutTime(accessLogsRs.getTimestamp("logoutTime") != null ? accessLogsRs.getTimestamp("logoutTime").toString() : null);
                accessLogList.add(accesslog);
            }
            session.setAttribute("logs", accessLogList);
            response.sendRedirect("account.jsp#access");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving access logs");
        }
    }
}
