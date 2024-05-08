package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.payment;
import model.DAO.paymentDAO;
import java.sql.Connection;

public class PaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
        paymentDAO paymentDao = new paymentDAO(conn);

        try {
            switch (action) {
                case "create":
                    // Gather info from request and create a payment
                    break;
                case "read":
                    // Display specific payment details
                    break;
                case "update":
                    // Update payment details
                    break;
                case "delete":
                    // Delete a payment record
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
