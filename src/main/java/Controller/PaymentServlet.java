package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.payment;
import model.DAO.paymentDAO;
import model.user;

public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private paymentDAO paymentDao;
    private user loggedInUser;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not established");
            return;
        }

        loggedInUser = (user) session.getAttribute("user");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            paymentDao = new paymentDAO(conn);
            // Assuming you have a method to process payment data
            payment payment = processPayment(request);
            boolean success = paymentDao.createPayment(payment);

            if (success) {
                // Forward to the confirmation page
                request.setAttribute("payment", payment);
                request.setAttribute("user", loggedInUser);
                request.getRequestDispatcher("confirmation.jsp").forward(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // Method to process payment data (replace with your actual logic)
    private payment processPayment(HttpServletRequest request) {
        double amount = 0.0; // Default value if parsing fails
        String method = request.getParameter("method");
        String date = request.getParameter("date");
        String status = "Approved"; // Assuming the status is set as Completed by default
        String cardID = request.getParameter("cardID"); // Assuming you have a way to retrieve card ID
        
        // Check if amount parameter is not null
        String amountParam = request.getParameter("amount");
        if (amountParam != null && !amountParam.trim().isEmpty()) {
            try {
                amount = Double.parseDouble(amountParam);
            } catch (NumberFormatException e) {
                // Handle parsing error
                e.printStackTrace();
                // You may want to log the error or handle it in a different way
            }
        }
        
        // Create a new Payment object
        return new payment(0, amount, method, date, status, cardID);
    }
    
}
