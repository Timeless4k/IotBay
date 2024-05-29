package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.user;
import model.DAO.userDAO;
import util.ValidationUtils;

public class UserServlet extends HttpServlet {

    private Connection conn;
    private userDAO userDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws ServletException, IOException {
        try {
            String searchEmail = request.getParameter("searchUser");
            user foundUser = userDao.getUserByEmail(searchEmail);
            if (foundUser != null) {
                // Set found user to be updated
                request.setAttribute("updateUser", foundUser);
                // Forward to the usermanagement.jsp to display update form
                request.getRequestDispatcher("/usermanagement.jsp").forward(request, response);
            } else {
                // User not found
                response.getWriter().print("User not found.");
            }
        } catch (Exception e) {
            response.getWriter().print("Error searching user: " + e.getMessage());
            e.printStackTrace();
        }
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }
    
    /**
     * Handles both GET and POST requests.
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "displayAll";
        }
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");
   
        if (conn == null) {
            response.getWriter().write("Database connection not available. Please check the connection settings.");
            return;
        }
   
        try {
            userDAO = new userDAO(conn);
            switch (action) {
                case "displayAll":
                    displayAllUsers(request, response, userDAO);
                    break;
                case "create":
                    createUser(request, response, userDAO);
                    break;
                case "update":
                    updateUser(request, response, userDAO);
                    break;
                case "delete":
                    deleteUser(request, response, userDAO);
                    break;
                case "activate":
                    activateUser(request, response, userDAO);
                    break;
                case "deactivate":
                    deactivateUser(request, response, userDAO);
                    break;
                case "search":
                    searchUser(request, response, userDAO);
                    break;
                default:
                    response.getWriter().print("No valid action provided.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("Error processing request: " + e.getMessage());
        }
    }

    /**
     * Displays all users by fetching them from the database and forwarding to usermanagement.jsp.
     */
    private void displayAllUsers(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws ServletException, IOException {
        try {
            List<user> users = userDao.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/usermanagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("Error fetching users: " + e.getMessage());
        }
    }

    /**
     * Activates a user by their userId.
     */
    private void activateUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws IOException, ServletException {
        String userId = request.getParameter("userId");
        if (userId != null && !userId.isEmpty()) {
            try {
                if (userDao.activateUser(userId)) {
                    response.sendRedirect("usermanagement.jsp");
                } else {
                    response.getWriter().print("Failed to activate user.");
                }
            } catch (Exception e) {
                response.getWriter().print("Error activating user: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            response.getWriter().print("User ID is missing.");
        }
    }

    /**
     * Deactivates a user by their userId.
     */
    private void deactivateUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws IOException, ServletException {
        String userId = request.getParameter("userId");
        if (userId != null && !userId.isEmpty()) {
            try {
                if (userDao.deactivateUser(userId)) {
                    response.sendRedirect("usermanagement.jsp");
                } else {
                    response.getWriter().print("Failed to deactivate user.");
                }
            } catch (Exception e) {
                response.getWriter().print("Error deactivating user: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            response.getWriter().print("User ID is missing.");
        }
    }

    /**
     * Creates a new user with the provided details and saves them to the database.
     */
    private void createUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws IOException, ServletException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");

            // Validate inputs
            if (!ValidationUtils.isValidEmail(email)) {
                response.getWriter().print("Invalid email format.");
                return;
            }
            if (!ValidationUtils.isValidPassword(password)) {
                response.getWriter().print("Password cannot be empty.");
                return;
            }
            if (!ValidationUtils.isValidPhone(phone)) {
                response.getWriter().print("Invalid phone number. Must be 10 digits.");
                return;
            }
            if (!ValidationUtils.isValidGender(gender)) {
                response.getWriter().print("Invalid gender. Must be 'Male', 'Female', or 'Other'.");
                return;
            }

            user newUser = new user(
                0, // Assuming userID is auto-generated or not needed here
                email,
                password, // Make sure to hash the password in practice
                request.getParameter("firstName"),
                request.getParameter("middleName"),
                request.getParameter("lastName"),
                request.getParameter("birthDate"),
                phone,
                gender,
                request.getParameter("creationDate"),
                request.getParameter("userType"),
                true // Assuming new users are activated by default
            );

            if (userDao.createUser(newUser) != -1) {
                response.sendRedirect("usermanagement.jsp");
            } else {
                response.getWriter().print("Failed to create user.");
            }
        } catch (Exception e) {
            response.getWriter().print("Error creating user: " + e.getMessage());
            e.printStackTrace();
        }
    }
   
    /**
     * Updates an existing user with the provided details and saves them to the database.
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws IOException, ServletException {
        try {
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");

            // Validate inputs
            if (!ValidationUtils.isValidEmail(email)) {
                response.getWriter().print("Invalid email format.");
                return;
            }
            if (!ValidationUtils.isValidGender(gender)) {
                response.getWriter().print("Invalid gender. Must be 'Male', 'Female', or 'Other'.");
                return;
            }

            user existingUser = userDao.getUserByEmail(email);
            if (existingUser != null) {
                // Update user object with new values from the request
                existingUser.setFirstName(request.getParameter("firstName"));
                existingUser.setMiddleName(request.getParameter("middleName"));
                existingUser.setLastName(request.getParameter("lastName"));
                existingUser.setBirthDate(request.getParameter("birthDate"));
                existingUser.setMobilePhone(phone);
                existingUser.setGender(gender);
                existingUser.setCreationDate(request.getParameter("creationDate"));
                existingUser.setuType(request.getParameter("userType"));

                // Call updateUser method in userDAO
                if (userDao.updateUser(existingUser)) {
                    // Update successful, redirect to user management page
                    response.sendRedirect("UserServlet?action=displayAll");
                } else {
                    // Update failed
                    response.getWriter().print("Failed to update user.");
                }
            } else {
                // User not found
                response.getWriter().print("User not found.");
            }
        } catch (Exception e) {
            // Exception occurred during update
            response.getWriter().print("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

     /**
     * Deletes a user by their email.
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws IOException {
        try {
            if (userDao.deleteUser(request.getParameter("email"))) {
                response.sendRedirect("usermanagement.jsp");
            } else {
                response.getWriter().print("Failed to delete user.");
            }
        } catch (Exception e) {
            response.getWriter().print("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
