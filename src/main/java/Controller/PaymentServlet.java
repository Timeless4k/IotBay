package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.card;
import model.payment;
import model.DAO.cardDAO;
import model.DAO.paymentDAO;
import model.user;

public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private paymentDAO paymentDao;
    private user loggedInUser;

    /**
     * Handles POST requests for processing payments.
     */
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
            payment payment = processPayment(request);
    
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentTime.format(formatter);
            payment.setDate(formattedDateTime);
    
            boolean success = paymentDao.createPayment(payment);
            if (success) {
                card cardUsed = fetchCardInformation(payment.getCardID());
    
                session.setAttribute("payment", payment);
                session.setAttribute("user", loggedInUser);
                session.setAttribute("card", cardUsed);
                session.setAttribute("currentTime", formattedDateTime);
    
                response.sendRedirect("PaymentServlet?action=confirm");
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    /**
     * Handles GET requests for confirming payments.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("confirm".equals(action)) {
            HttpSession session = request.getSession(false); // Get existing session, don't create a new one
            if (session != null) {
                // Retrieve attributes; don't remove them immediately
                payment payment = (payment) session.getAttribute("payment");
                user user = (user) session.getAttribute("user");
                card card = (card) session.getAttribute("card");
                String currentTime = (String) session.getAttribute("currentTime");
    
                if (payment != null && user != null && card != null) {
                    request.setAttribute("payment", payment);
                    request.setAttribute("user", user);
                    request.setAttribute("card", card);
                    request.setAttribute("currentTime", currentTime);
    
                    // Forward to the JSP
                    request.getRequestDispatcher("confirmation.jsp").forward(request, response);
                } else {
                    // Data missing, likely due to a refresh after session clear, redirect to a safe page
                    response.sendRedirect("summary.jsp"); // Redirect to a summary or information page
                }
            } else {
                // No session, user likely not logged in or session expired
                response.sendRedirect("login.jsp");
            }
        } else {
            // Handle other GET requests or redirect to a safe landing page
            response.sendRedirect("index.jsp");
        }
    }

    /**
     * Fetches card information by card ID.
     */
    private card fetchCardInformation(String cardID) throws SQLException {
        cardDAO cardDao = new cardDAO(conn);
        return cardDao.getCardById(Long.parseLong(cardID));
    }

    /**
     * Processes payment details from the request.
     */
    private payment processPayment(HttpServletRequest request) {
        double amount = 0.0;
        String method = "Card";
        String date = request.getParameter("date");
        String status = "Approved";
        String cardID = request.getParameter("cardID");

        String amountParam = request.getParameter("amount");
        if (amountParam != null && !amountParam.trim().isEmpty()) {
            try {
                amount = Double.parseDouble(amountParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new payment(0, amount, method, date, status, cardID);
    }
}
