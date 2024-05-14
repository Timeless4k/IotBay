package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.userDAO;
import model.user;

public class GuestUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            System.err.println("Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        try {
            userDAO UDAO = new userDAO(conn);

            user newUser = new user();
            newUser.setuType("Guest"); // Assuming default user type
            newUser.setEmail("guest@localhost.com"); // Assuming default email
            newUser.setFirstName("Guest");

            long createUserSuccess = UDAO.createUser(newUser);
            user tempUser = UDAO.getUserByEmail("guest@localhost.com"); 
            newUser.setuID(tempUser.getuID());
            
            if (createUserSuccess != -1) {
                session.setAttribute("user", newUser);
                response.sendRedirect("welcome.jsp");
            } else {
                response.sendRedirect("register.jsp?error=true");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating user");
        }
    }
}
