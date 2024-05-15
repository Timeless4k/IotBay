package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.user;
import model.DAO.userDAO;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/DeleteProfileServlet")
public class DeleteProfileServlet extends HttpServlet {

    // Handles POST requests for deleting a user profile
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");
        if (conn == null) {
            // Log and send an error response if the database connection is not established
            System.err.println("DeleteProfileServlet: No database connection available.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not available");
            return;
        }

        user currentUser = (user) session.getAttribute("user");
        if (currentUser == null) {
            // Log and send an error response if the user is not found in the session
            System.err.println("DeleteProfileServlet: User not found in session.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User not found in session");
            return;
        }

        // Retrieve updated user details from the request
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");  
        String birthDate = request.getParameter("birthDate");
        String mobilePhone = request.getParameter("mobilePhone");

        // Update current user object with new details
        currentUser.setFirstName(firstName);
        currentUser.setMiddleName(middleName);
        currentUser.setLastName(lastName);
        currentUser.setPassword(password); 
        currentUser.setBirthDate(birthDate);
        currentUser.setMobilePhone(mobilePhone);

        try {
            userDAO userDao = new userDAO(conn);
            // Delete the user from the database
            boolean deleteSuccess = userDao.deleteUser(currentUser.getEmail());
            if (deleteSuccess) {
                conn.commit();  
                session.setAttribute("user", currentUser);  // Update session with new user data
                response.sendRedirect("account.jsp");  // Refresh the page with updated data
            } else {
                conn.rollback();  // Rollback in case of failure
                System.err.println("DeleteProfileServlet: Failed to update user data, transaction rolled back.");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update user data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();  // Ensure to rollback on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error during update");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}
