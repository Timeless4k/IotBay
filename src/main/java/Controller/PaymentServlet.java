package Controller;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.payment;
import model.DAO.paymentDAO;


public class PaymentServlet extends HttpServlet {

    // protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //     HttpSession session = request.getSession();
    //     Connection conn = (Connection) session.getAttribute("acticonn");

    //     if (conn == null) {
    //         System.err.println("Database connection error: Database connection not established");
    //         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
    //         return;
    //     }

    //     paymentDAO paymentDao = new paymentDAO(conn);

    //     // Example for adding a payment
    //     double amount = Double.parseDouble(request.getParameter("amount"));
    //     String method = request.getParameter("method");
    //     String cardID = request.getParameter("cardID");
    //     long userID = ((user) session.getAttribute("user")).getuID();  // Assumes user is stored in session

    //     payment newPayment = new payment(0, amount, method, LocalDate.now().toString(), "Pending", cardID);

    //     if (paymentDao.createPayment(newPayment)) {
    //         response.sendRedirect("success.jsp");
    //     } else {
    //         response.sendRedirect("error.jsp");
    //     }
    // }
}
