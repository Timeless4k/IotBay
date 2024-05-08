package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.user;
import model.DAO.userDAO;

public class UserServlet extends HttpServlet {

    private Connection conn;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false); // Change here to check if session already exists
        Connection conn = (session != null) ? (Connection) session.getAttribute("acticonn") : null;

        if (conn == null) {
            response.getWriter().write("Database connection not available. Please check the connection settings.");
            return; // Early exit if no connection is available
        }

        try {
            userDAO userDao = new userDAO(conn);
            switch (action) {
                case "displayAll":
                    displayAllUsers(request, response, userDao);
                    break;
                case "create":
                    createUser(request, response, userDao);
                    break;
                case "update":
                    updateUser(request, response, userDao);
                    break;
                case "delete":
                    deleteUser(request, response, userDao);
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

    private void createUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws IOException, ServletException {
        try {
            user newUser = new user(
                0, // Assuming userID is auto-generated or not needed here
                request.getParameter("email"),
                request.getParameter("password"), // Make sure to hash the password in practice
                request.getParameter("firstName"),
                request.getParameter("middleName"),
                request.getParameter("lastName"),
                request.getParameter("birthDate"),
                request.getParameter("phone"),
                request.getParameter("gender"),
                request.getParameter("creationDate"),
                request.getParameter("userType")
            );
            if (userDao.createUser(newUser)) {
                response.sendRedirect("usermanagement.jsp");
            } else {
                response.getWriter().print("Failed to create user.");
            }
        } catch (Exception e) {
            response.getWriter().print("Error creating user: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    private void updateUser(HttpServletRequest request, HttpServletResponse response, userDAO userDao) throws IOException {
        try {
            String email = request.getParameter("email");
            user existingUser = userDao.getUserByEmail(email);
            if (existingUser != null) {
                existingUser.setFirstName(request.getParameter("firstName"));
                existingUser.setMiddleName(request.getParameter("middleName"));
                existingUser.setLastName(request.getParameter("lastName"));
                existingUser.setBirthDate(request.getParameter("birthDate"));
                existingUser.setMobilePhone(request.getParameter("phone"));
                existingUser.setGender(request.getParameter("gender"));
                existingUser.setCreationDate(request.getParameter("creationDate"));
                existingUser.setuType(request.getParameter("userType"));
                if (userDao.updateUser(existingUser)) {
                    response.sendRedirect("usermanagement.jsp");
                } else {
                    response.getWriter().print("Failed to update user.");
                }
            } else {
                response.getWriter().print("User not found.");
            }
        } catch (Exception e) {
            response.getWriter().print("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

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
