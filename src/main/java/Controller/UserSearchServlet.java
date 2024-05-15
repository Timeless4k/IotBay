package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.user;
import model.DAO.userDAO;

public class UserSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests by forwarding them to doPost method.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles POST requests for searching users by full name and/or phone number.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");

        try {
            Connection conn = (Connection) request.getSession().getAttribute("acticonn");
            userDAO userDao = new userDAO(conn);

            // Check if either fullName or phoneNumber is provided
            if ((fullName != null && !fullName.isEmpty()) || (phoneNumber != null && !phoneNumber.isEmpty())) {
                // Perform the search based on provided fullName and/or phoneNumber
                List<user> searchResults = userDao.searchUsersByFullNameAndPhone(fullName, phoneNumber);
                if (searchResults != null && !searchResults.isEmpty()) {
                    // Set search results to be displayed
                    request.setAttribute("users", searchResults);
                    request.getRequestDispatcher("/usermanagement.jsp").forward(request, response);
                } else {
                    // No users found
                    request.getRequestDispatcher("/usernotfound.jsp").forward(request, response);
                }
            } else {
                // Handle case when both fullName and phoneNumber are empty
                request.setAttribute("message", "Please provide full name or phone number to search.");
                request.getRequestDispatcher("/usermanagement.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
