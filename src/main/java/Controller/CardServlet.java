package Controller;

import model.card;
import model.DAO.cardDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CardServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");
        
        if (conn == null) {
            response.getWriter().write("Database connection not available. Please check the connection settings.");
            return;
        }

        cardDAO cardDao = new cardDAO(conn);

        try {
            switch (action) {
                case "create":
                    createCard(request, response, cardDao);
                    break;
                case "update":
                    updateCard(request, response, cardDao);
                    break;
                case "delete":
                    deleteCard(request, response, cardDao);
                    break;
                default:
                    response.getWriter().print("Invalid action.");
                    break;
            }
        } catch (Exception e) {
            response.getWriter().print("Error handling card: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createCard(HttpServletRequest request, HttpServletResponse response, cardDAO cardDao) throws IOException, ServletException {
        long cardNumber = Long.parseLong(request.getParameter("cardNumber"));
        String cardHolderName = request.getParameter("cardHolderName");
        String cardExpiry = request.getParameter("cardExpiry");
        int cardCVV = Integer.parseInt(request.getParameter("cardCVV"));
        long userID = Long.parseLong(request.getParameter("userID"));

        card newCard = new card();
        newCard.setCardNumber(cardNumber);
        newCard.setCardHolderName(cardHolderName);
        newCard.setCardExpiry(cardExpiry);
        newCard.setCardCVV(cardCVV);
        newCard.setUserID(userID);

        try {
            cardDao.createCard(newCard);
            response.sendRedirect("managePayment.jsp");
        } catch (SQLException e) {
            throw new ServletException("SQL error while creating card", e);
        }
    }

    private void updateCard(HttpServletRequest request, HttpServletResponse response, cardDAO cardDao) throws IOException, ServletException, SQLException {
        long cardID = Long.parseLong(request.getParameter("cardID"));
        card existingCard = cardDao.getCardById(cardID);
        if (existingCard != null) {
            existingCard.setCardNumber(Long.parseLong(request.getParameter("cardNumber")));
            existingCard.setCardHolderName(request.getParameter("cardHolderName"));
            existingCard.setCardExpiry(request.getParameter("cardExpiry"));
            existingCard.setCardCVV(Integer.parseInt(request.getParameter("cardCVV")));
            cardDao.updateCard(existingCard);
        }
        response.sendRedirect("managePayment.jsp");
    }

    private void deleteCard(HttpServletRequest request, HttpServletResponse response, cardDAO cardDao) throws IOException, ServletException, SQLException {
        long cardID = Long.parseLong(request.getParameter("cardID"));
        cardDao.deleteCard(cardID);
        response.sendRedirect("managePayment.jsp");
    }
}
