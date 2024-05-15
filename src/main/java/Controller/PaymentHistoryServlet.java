package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.payment;
import model.DAO.paymentDAO;
import model.user;

public class PaymentHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display payment history for the logged-in user.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Retrieve the logged-in user from the session
        user loggedInUser = (user) session.getAttribute("user");
        Connection conn = (Connection) session.getAttribute("acticonn");

        // Redirect to login page if user is not logged in
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Send error if database connection is not established
        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not established");
            return;
        }

        try {
            paymentDAO paymentDao = new paymentDAO(conn);
            List<payment> payments;

            // Fetch parameters for searching payments
            String searchID = request.getParameter("searchID");
            String searchDate = request.getParameter("searchDate");

            // Search payments by ID and/or date if parameters are provided
            if ((searchID != null && !searchID.isEmpty()) || (searchDate != null && !searchDate.isEmpty())) {
                payments = paymentDao.searchPayments(
                    loggedInUser.getuID(),
                    searchID.isEmpty() ? null : Long.parseLong(searchID),
                    searchDate
                );
            } else {
                // Fetch all payments for the logged-in user if no search parameters are provided
                payments = paymentDao.getPaymentsForUser(loggedInUser.getuID());
            }

            // Set the payments as a request attribute and forward to the JSP page
            request.setAttribute("payments", payments);
            request.getRequestDispatcher("/paymentHistory.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
