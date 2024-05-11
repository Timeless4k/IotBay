package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import model.card;
import model.DAO.cardDAO;
import model.user;

public class CardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            System.err.println("Database connection error: Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser == null) {
            // Redirect to login page if user is not logged in
            response.sendRedirect("login.jsp");
            return;
        }

        cardDAO cardDao = null;
        try {
            cardDao = new cardDAO(conn);
        } catch (SQLException e) {
            System.err.println("Error creating cardDAO: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating cardDAO");
            return;
        }

        // Get card list for the logged-in user
        List<card> cardList = cardDao.getCardsForUser(loggedInUser.getuID());

        // Set cardList attribute in request scope
        request.setAttribute("cardList", cardList);

        // Forward the request to the JSP
        request.getRequestDispatcher("managePayment.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            System.err.println("Database connection error: Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser == null) {
            // Redirect to login page if user is not logged in
            response.sendRedirect("login.jsp");
            return;
        }

        cardDAO cardDao = null;
        try {
            cardDao = new cardDAO(conn);
        } catch (SQLException e) {
            System.err.println("Error creating cardDAO: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating cardDAO");
            return;
        }

        // Extract form data
        long cardNumber = Long.parseLong(request.getParameter("cardNumber"));
        String cardHolderName = request.getParameter("cardHolderName");
        String cardExpiry = request.getParameter("cardExpiry");
        int cardCVV = Integer.parseInt(request.getParameter("cardCVV"));

        // Create card object
        card newCard = new card();
        newCard.setCardNumber(cardNumber);
        newCard.setCardHolderName(cardHolderName);
        newCard.setCardExpiry(cardExpiry);
        newCard.setCardCVV(cardCVV);
        newCard.setUserID(loggedInUser.getuID());

        // Call DAO method to create card
        boolean success = cardDao.createCard(newCard);

        if (success) {
            // Redirect to cards page after successful creation
            response.sendRedirect("CardServlet");
        } else {
            // Handle error if card creation fails
            response.sendRedirect("error.jsp");
        }
    }
}
