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


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String fullName = request.getParameter("fullName");
        String phoneNumber = request.getParameter("phoneNumber");


        if ((fullName != null && !fullName.isEmpty()) || (phoneNumber != null && !phoneNumber.isEmpty())) {
            try {
                Connection conn = (Connection) request.getSession().getAttribute("acticonn");
                userDAO userDao = new userDAO(conn);
                List<user> searchResults = userDao.searchUsersByFullNameAndPhone(fullName, phoneNumber);
                request.setAttribute("users", searchResults);
                request.getRequestDispatcher("/usermanagement.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            }
        } else {
            // Handle case when both fullName and phoneNumber are empty
            response.getWriter().write("Please provide full name or phone number to search.");
        }
    }
}



