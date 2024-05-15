package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.accesslogDAO;
import model.DAO.userDAO;
import model.accesslog;
import model.user;
import util.ValidationUtils;

public class RegisterServlet extends HttpServlet {

    // Handles POST requests for user registration
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            // Log and send an error response if the database connection is not established
            System.err.println("Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        try {
            userDAO UDAO = new userDAO(conn);

            // Retrieve registration details from the request
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String mobilePhone = request.getParameter("mobilePhone");
            String gender = request.getParameter("gender");

            // Just for checking the values
            System.out.println(email);
            System.out.println(ValidationUtils.isValidEmail(email));
            System.out.println(password);
            System.out.println(ValidationUtils.isValidPassword(password));
            System.out.println(mobilePhone);
            System.out.println(ValidationUtils.isValidPhone(mobilePhone));
            System.out.println(gender);
            System.out.println(ValidationUtils.isValidGender(gender));

            // Validate input fields
            if (!ValidationUtils.isValidEmail(email) || !ValidationUtils.isValidPassword(password) ||
                !ValidationUtils.isValidPhone(mobilePhone) || !ValidationUtils.isValidGender(gender)) {
                response.sendRedirect("register.jsp?error=validationFailed");
                return;
            }

            // Check for existing user
            user existingUser = UDAO.getUserByEmail(email);
            if (existingUser != null) {
                response.sendRedirect("register.jsp?error=duplicate");
                return;
            }

            // Create new user
            user newUser = new user();
            newUser.setEmail(email);
            newUser.setPassword(password); 
            newUser.setFirstName(firstName);
            newUser.setMiddleName(middleName);
            newUser.setLastName(lastName);
            newUser.setMobilePhone(mobilePhone);
            newUser.setGender(gender);
            newUser.setuType("Customer"); // Assuming default user type

            long createUserSuccess = UDAO.createUser(newUser);
            newUser.setuID(createUserSuccess);
            if (createUserSuccess != -1) {
                accesslogDAO accesslogDAO = new accesslogDAO(conn);
                accesslogDAO.logLogin(newUser);

                // Retrieve and store access logs in the session
                ResultSet accessLogsRs = accesslogDAO.getLogsForUser(newUser);
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
                session.setAttribute("logs", accessLogList);
                
                // Set user in the session and redirect to welcome page                
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
