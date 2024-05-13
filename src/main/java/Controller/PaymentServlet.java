package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.card;
import model.payment;
import model.DAO.paymentDAO;
import model.DAO.cardDAO;

public class PaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            System.err.println("Database connection error: Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        try {
            cardDAO cardDao = new cardDAO(conn);  // Handle the exception that cardDAO might throw
            paymentDAO paymentDao = new paymentDAO(conn);

            // Retrieve payment details from the request
            long cardID = Long.parseLong(request.getParameter("cardID"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String method = request.getParameter("method"); // "Credit Card", "PayPal", etc.
            String date = request.getParameter("date"); // Today's date or as per the form input
            String status = "Pending"; // Initial status can be pending

            // Retrieve the card used
            card usedCard = cardDao.getCardById(cardID);

            // Create a new payment record
            payment newPayment = new payment(0, amount, method, date, status, String.valueOf(cardID));
            boolean isPaymentSuccessful = paymentDao.createPayment(newPayment);

            if (isPaymentSuccessful) {
                request.setAttribute("orderID", newPayment.getPaymentID());
                request.setAttribute("paymentMethod", method);
                request.setAttribute("lastFourDigits", usedCard.getCardNumber() % 10000); // Simplified for clarity
                request.getRequestDispatcher("/thankYou.jsp").forward(request, response);
            } else {
                response.sendRedirect("paymentError.jsp"); // Redirect to an error page or handle the error accordingly
            }
        } catch (NumberFormatException | SQLException e) {
            // Log the exception or handle it as necessary
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format or Database Error");
        }
    }
}
