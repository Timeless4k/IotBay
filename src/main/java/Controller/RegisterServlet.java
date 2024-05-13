package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.accesslogDAO;
import model.DAO.userDAO;
import model.user;

public class RegisterServlet extends HttpServlet {
    private Connection conn;
    private userDAO UDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("running register servlet");
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
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String mobilePhone = request.getParameter("mobilePhone");
        String gender = request.getParameter("gender");

        // Check if email already exists
        user existingUser = UDAO.getUserByEmail(email);
        if (existingUser != null) {
            response.sendRedirect("register.jsp?error=duplicate");
            return;
        }

        // Create a User instance and set its properties
        user newUser = new user();
        newUser.setEmail(email);
        newUser.setPassword(password);  // Consider hashing the password before setting
        newUser.setFirstName(firstName);
        newUser.setMiddleName(middleName);
        newUser.setLastName(lastName);
        newUser.setBirthDate(birthDate);
        newUser.setMobilePhone(mobilePhone);
        newUser.setGender(gender);
        newUser.setuType("Customer");  // Assuming new registrations are 'Customer' type by default
        newUser.setCreationDate(birthDate);  // Consider using the current date/time instead

        // Attempt to create user
        try {
            boolean createUserSuccess = UDAO.createUser(newUser);
            conn.commit();
            if (createUserSuccess) {
                accesslogDAO logDAO = new accesslogDAO(conn); // Create the log DAO instance
                logDAO.logLogin(newUser);
                
                session.setAttribute("user", newUser);
                response.sendRedirect("welcome.jsp");
            } else {
                response.sendRedirect("register.jsp?error=true");
            }
        } catch (Exception e) {
            System.err.println("User creation failed: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating user");
        }

        }

    }
