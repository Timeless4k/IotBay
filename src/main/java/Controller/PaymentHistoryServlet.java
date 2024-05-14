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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        user loggedInUser = (user) session.getAttribute("user");
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not established");
            return;
        }

        try {
            paymentDAO paymentDao = new paymentDAO(conn);
            List<payment> payments;

            // Fetch parameters for searching
            String searchID = request.getParameter("searchID");
            String searchDate = request.getParameter("searchDate");

            if ((searchID != null && !searchID.isEmpty()) || (searchDate != null && !searchDate.isEmpty())) {
                payments = paymentDao.searchPayments(
                    loggedInUser.getuID(),
                    searchID.isEmpty() ? null : Long.parseLong(searchID),
                    searchDate
                );
            } else {
                payments = paymentDao.getPaymentsForUser(loggedInUser.getuID());
            }

            request.setAttribute("payments", payments);
            request.getRequestDispatcher("/paymentHistory.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
