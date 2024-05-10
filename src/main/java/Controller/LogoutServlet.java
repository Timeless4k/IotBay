package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import model.DAO.accesslogDAO;
import model.user;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get the existing session
        HttpSession session = request.getSession(false); 
        if (session != null) {
            user currentUser = (user) session.getAttribute("user");
            if (currentUser != null) {
                // Log the logout event
                accesslogDAO logDao;
                try {
                    logDao = new accesslogDAO((java.sql.Connection) session.getAttribute("acticonn"));
                    logDao.logLogout(currentUser);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Invalidate the session
                session.invalidate();
            }
            // Redirect to the logout page
            response.sendRedirect("logout.jsp"); 
        }
    }
}
