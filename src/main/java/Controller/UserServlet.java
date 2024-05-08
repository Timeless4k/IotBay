package Controller;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.user;
import model.DAO.userDAO;

public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try (Connection conn = (Connection) getServletContext().getAttribute("DBConnection")) {
            userDAO userDao = new userDAO(conn);

            switch (action) {
                case "register":
                    // Collect all user parameters from the request
                    Long userId = Long.parseLong(request.getParameter("userId")); // Manually assign user ID
                    String email = request.getParameter("email");
                    String password = request.getParameter("password"); // Hash this password before setting
                    String firstName = request.getParameter("firstName");
                    String middleName = request.getParameter("middleName");
                    String lastName = request.getParameter("lastName");
                    String bday = request.getParameter("birthday");
                    String userType = request.getParameter("userType");
                    String phone = request.getParameter("phone");
                    String gender = request.getParameter("gender");
                    String creationDate = request.getParameter("creationDate"); // Format and set correctly

                    // Create and populate user object
                    user newUser = new user(userId, email, password, firstName, middleName, lastName, bday, phone, gender, creationDate, userType);
                    boolean success = userDao.createUser(newUser);
                    if (success) {
                        response.sendRedirect("userCreated.jsp"); // Redirect to success page
                    } else {
                        response.sendRedirect("errorPage.jsp"); // Redirect to error page
                    }
                    break;

                case "delete":
                    String deleteEmail = request.getParameter("email");
                    boolean deleted = userDao.deleteUser(deleteEmail);
                    if (deleted) {
                        response.sendRedirect("userDeleted.jsp");
                    } else {
                        response.sendRedirect("errorPage.jsp");
                    }
                    break;

                // Implement other cases like update and read
            }
        } catch (Exception e) {
            throw new ServletException("DB Connection problem.", e);
        }
    }
}
