package Controller;

import model.payment;
import model.user;
import model.DAO.userDAO;
import model.DAO.paymentDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");
        if (conn == null) {
            response.getWriter().println("Database connection not available. Please check the connection settings.");
            return;
        }

        paymentDAO paymentDao = new paymentDAO(conn);
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createPayment(request, response, paymentDao);
                    break;
                case "read":
                    readPayment(request, response, paymentDao);
                    break;
                case "update":
                    updatePayment(request, response, paymentDao);
                    break;
                case "delete":
                    deletePayment(request, response, paymentDao);
                    break;
                case "displayAll":
                    displayAllPayments(request, response, paymentDao);
                    break;
                default:
                    response.getWriter().println("No valid action provided.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error processing request: " + e.getMessage());
        }
    }

    private void createPayment(HttpServletRequest request, HttpServletResponse response, paymentDAO paymentDao) throws IOException, ServletException {
        try {
            long paymentID = Long.parseLong(request.getParameter("paymentID"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String method = request.getParameter("method");
            String date = request.getParameter("date");
            String status = request.getParameter("status");
            String cardID = request.getParameter("cardID");

            payment payment = new payment(paymentID, amount, method, date, status, cardID);
            if (paymentDao.createPayment(payment)) {
                response.sendRedirect("paymentSuccess.jsp");
            } else {
                response.getWriter().println("Failed to create payment.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error creating payment: " + e.getMessage());
        }
    }

    private void readPayment(HttpServletRequest request, HttpServletResponse response, paymentDAO paymentDao) throws IOException, ServletException {
        try {
            long paymentID = Long.parseLong(request.getParameter("paymentID"));
            payment payment = paymentDao.readPayment(paymentID);
            if (payment != null) {
                request.setAttribute("payment", payment);
                request.getRequestDispatcher("/paymentDetails.jsp").forward(request, response);
            } else {
                response.getWriter().println("Payment not found.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error reading payment: " + e.getMessage());
        }
    }

    private void updatePayment(HttpServletRequest request, HttpServletResponse response, paymentDAO paymentDao) throws IOException {
        try {
            long paymentID = Long.parseLong(request.getParameter("paymentID"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String method = request.getParameter("method");
            String date = request.getParameter("date");
            String status = request.getParameter("status");
            String cardID = request.getParameter("cardID");

            payment existingPayment = new payment(paymentID, amount, method, date, status, cardID);
            if (paymentDao.updatePayment(existingPayment)) {
                response.sendRedirect("updateSuccess.jsp");
            } else {
                response.getWriter().println("Failed to update payment.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error updating payment: " + e.getMessage());
        }
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response, paymentDAO paymentDao) throws IOException {
        try {
            long paymentID = Long.parseLong(request.getParameter("paymentID"));
            if (paymentDao.deletePayment(paymentID)) {
                response.sendRedirect("deleteSuccess.jsp");
            } else {
                response.getWriter().println("Failed to delete payment.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error deleting payment: " + e.getMessage());
        }
    }

    private void displayAllPayments(HttpServletRequest request, HttpServletResponse response, paymentDAO paymentDao) throws ServletException, IOException {
        try {
            List<payment> payments = paymentDao.getAllPayments();
            request.setAttribute("payments", payments);
            request.getRequestDispatcher("/managePayment.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error fetching payments: " + e.getMessage());
        }
    }

    
}