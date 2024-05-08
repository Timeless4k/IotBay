package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import model.DAO.userDAO;
import model.user;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{
    private Connection conn;
    private userDAO UDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("running login servlet");
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");
        
        try {
            conn = (Connection) session.getAttribute("acticonn");
            if (conn == null) {
                throw new SQLException("Database connection not established");
            }
            UDAO = new userDAO(conn);
        } catch (SQLException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return; // Stop further execution in case of error
        }

        // Retrieve form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(email.length() > 200) {
            response.sendRedirect("login.jsp?error=true");
            return;
        }
        // Create a User instance and set its properties
        user newUser = new user();
        newUser.setEmail(email);
        newUser.setPassword(password);  // Consider hashing the password before setting

        try {
            if () {
                response.sendRedirect("index.jsp");
            } else {
                response.sendRedirect("login.jsp?error=true");
            }
        } catch (SQLException ex) {
            System.err.println("Database error: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
    
}
