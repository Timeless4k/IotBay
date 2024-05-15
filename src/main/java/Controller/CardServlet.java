package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.card;
import model.DAO.cardDAO;
import model.user;
import util.ValidationUtils;

public class CardServlet extends HttpServlet {

    private Connection conn;
    private cardDAO cardDao;
    private user loggedInUser; // Declare loggedInUser as a class-level variable

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            // Initialize conn here before creating cardDAO
            conn = (Connection) session.getAttribute("acticonn");
            if (conn == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not established");
                return;
            }
            
            cardDao = new cardDAO(conn); // Initialize cardDao
            List<card> cardList = cardDao.getCardsForUser(loggedInUser.getuID());
            
            if (cardList != null) {
                // Forward the retrieved payment methods to the JSP for display
                request.setAttribute("cardList", cardList);
                request.getRequestDispatcher("payment.jsp").forward(request, response);
            } else {
                // Handle the case where cardList is null (e.g., database error)
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving payment methods");
            }
        } catch (SQLException e) {
            // Handle any database errors
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    /**
     * Handles both GET and POST requests.
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not established");
            return;
        }

        loggedInUser = (user) session.getAttribute("user"); // Assign loggedInUser when handling the request

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            cardDao = new cardDAO(conn);
            String action = request.getParameter("action");

            if (action == null) {
                action = "displayAll";
            }

            switch (action) {
                case "displayAll":
                    displayAllCards(request, response);
                    break;
                case "edit":
                    editCard(request, response);
                    break;
                case "delete":
                    deleteCard(request, response);
                    break;
                case "create":
                    createCard(request, response);
                    break;
                
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all cards associated with the logged-in user.
     */
    private void displayAllCards(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<card> cardList = cardDao.getCardsForUser(loggedInUser.getuID());
        request.setAttribute("cardList", cardList);
        request.getRequestDispatcher("payment.jsp").forward(request, response);
    }
    
    /**
     * Edits the details of an existing card.
     */
    private void editCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        try {
            long cardID = Long.parseLong(request.getParameter("cardID"));
            String cardNumber = request.getParameter("cardNumber");
            String cardHolderName = request.getParameter("cardHolderName");
            String cardExpiry = request.getParameter("cardExpiry");
            String cardCVV = request.getParameter("cardCVV");
    
            // Validate inputs
            if (!ValidationUtils.isValidCardNumber(cardNumber)) {
                response.getWriter().print("Invalid card number format.");
                return;
            }
            if (!ValidationUtils.isValidCardHolderName(cardHolderName)) {
                response.getWriter().print("Invalid card holder name.");
                return;
            }
            if (!ValidationUtils.isValidCardCVV(cardCVV)) {
                response.getWriter().print("Invalid card CVV. Must be 3 or 4 digits.");
                return;
            }
    
            // Retrieve the logged-in user from the session
            user loggedInUser = (user) request.getSession().getAttribute("user");
            if (loggedInUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            long userID = loggedInUser.getuID();
    
            // Logging to check received parameters
            System.out.println("Received parameters - cardID: " + cardID + ", cardNumber: " + cardNumber + ", cardHolderName: " + cardHolderName + ", cardExpiry: " + cardExpiry + ", cardCVV: " + cardCVV);
    
            card cardToUpdate = new card(cardID, Long.parseLong(cardNumber), cardHolderName, cardExpiry, Integer.parseInt(cardCVV), userID);
    
            // Update the card in the database
            cardDAO cardDao = new cardDAO((Connection) request.getSession().getAttribute("acticonn"));
    
            boolean success = cardDao.updateCard(cardToUpdate);
    
            if (success) {
                // Redirect to the displayAll action to show the updated card list
                response.sendRedirect("CardServlet?action=displayAll");
            } else {
                response.sendRedirect("error.jsp"); // Handle update failure
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp"); // Handle parsing errors
        }
    }

    /**
     * Deletes an existing card by its ID.
     */
    private void deleteCard(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        long cardId = Long.parseLong(request.getParameter("cardId"));
        card deleteCard = cardDao.getCardById(cardId);
        if (deleteCard != null && deleteCard.getUserID() == loggedInUser.getuID()) {
            boolean success = cardDao.deleteCard(cardId);
            if (success) {
                response.sendRedirect("CardServlet?action=displayAll");
            } else {
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("error.jsp");
        }
    }  

    /**
     * Converts a date string in MM/yyyy format to the last day of that month in yyyy-MM-dd format.
     */
    private String convertToLastDayOfMonth(String expiry) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(originalFormat.parse(expiry)); // Parse the date

            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Last day
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
            return targetFormat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new card with the provided details and saves it to the database.
     */
    private void createCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String cardNumber = request.getParameter("cardNumber");
        String cardHolderName = request.getParameter("cardHolderName");
        String cardExpiry = request.getParameter("cardExpiry"); // The expiry date from the form
        String cardCVV = request.getParameter("cardCVV");
    
        HttpSession session = request.getSession();
        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        long userID = loggedInUser.getuID();
    
        // Validate inputs
        if (!ValidationUtils.isValidCardNumber(cardNumber)) {
            response.getWriter().print("Invalid card number format.");
            return;
        }
        if (!ValidationUtils.isValidCardHolderName(cardHolderName)) {
            response.getWriter().print("Invalid card holder name.");
            return;
        }
        if (!ValidationUtils.isValidCardExpiry(cardExpiry)) {
            response.getWriter().print("Invalid card expiry date format. Use MM/yyyy or date is in the past.");
            return;
        }
        if (!ValidationUtils.isValidCardCVV(cardCVV)) {
            response.getWriter().print("Invalid card CVV. Must be 3 or 4 digits.");
            return;
        }
    
        try {
            conn.setAutoCommit(false);
    
            // Convert the expiry date to the last day of the specified month/year
            String formattedExpiryDate = convertToLastDayOfMonth(cardExpiry);
            if (formattedExpiryDate == null) {
                throw new Exception("Invalid date format");
            }
    
            card newCard = new card(0, Long.parseLong(cardNumber), cardHolderName, formattedExpiryDate, Integer.parseInt(cardCVV), userID);
    
            boolean success = cardDao.createCard(newCard);
    
            if (success) {
                conn.commit();
                // Fetch updated list of cards to reflect the new addition
                response.sendRedirect("CardServlet?action=displayAll"); // Redirect to avoid form resubmission
            } else {
                conn.rollback();
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            conn.rollback();
            System.err.println("Error creating card: " + e.getMessage());
            response.sendRedirect("error.jsp");
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
