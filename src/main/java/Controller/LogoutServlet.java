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
        
        HttpSession session = request.getSession(false); // Get the existing session
        if (session != null) {
            user currentUser = (user) session.getAttribute("user");
            if (currentUser != null) {
                // Log the logout event
                accesslogDAO logDao;
                try {
                    logDao = new accesslogDAO((java.sql.Connection) session.getAttribute("acticonn"));
                    logDao.logLogout(currentUser);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Invalidate the session
                session.invalidate();
            }
            response.sendRedirect("logout.jsp"); // Redirect to login page or your choice of landing page
        }
    }
}
