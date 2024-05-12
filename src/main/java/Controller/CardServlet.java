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
                request.getRequestDispatcher("managePayment.jsp").forward(request, response);
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

    private void displayAllCards(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<card> cardList = cardDao.getCardsForUser(loggedInUser.getuID());
        request.setAttribute("cardList", cardList);
        request.getRequestDispatcher("managePayment.jsp").forward(request, response);
    }

    private void editCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        long cardId = Long.parseLong(request.getParameter("cardId"));
        card editCard = cardDao.getCardById(cardId);
        if (editCard != null && editCard.getUserID() == loggedInUser.getuID()) {
            request.setAttribute("editCard", editCard);
            request.getRequestDispatcher("editCard.jsp").forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }

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

    private void createCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        long cardNumber = Long.parseLong(request.getParameter("cardNumber"));
        String cardHolderName = request.getParameter("cardHolderName");
        String cardExpiry = request.getParameter("cardExpiry"); // The expiry date from the form
        int cardCVV = Integer.parseInt(request.getParameter("cardCVV"));
    
        HttpSession session = request.getSession();
        user loggedInUser = (user) session.getAttribute("user");
        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        long userID = loggedInUser.getuID();
    
        try {
            conn.setAutoCommit(false);
    
            // Convert the expiry date to the last day of the specified month/year
            String formattedExpiryDate = convertToLastDayOfMonth(cardExpiry);
            if (formattedExpiryDate == null) {
                throw new Exception("Invalid date format");
            }
    
            card newCard = new card(0, cardNumber, cardHolderName, formattedExpiryDate, cardCVV, userID);
    
            boolean success = cardDao.createCard(newCard);
    
            if (success) {
                conn.commit();
                response.sendRedirect("CardServlet?action=displayAll");
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
