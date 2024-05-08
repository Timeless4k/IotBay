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
            payment payment;
            long paymentID;
            switch (action) {
                case "create":
                    payment = new payment(Long.parseLong(request.getParameter("paymentID")),
                                          Double.parseDouble(request.getParameter("amount")),
                                          request.getParameter("method"),
                                          request.getParameter("date"),
                                          request.getParameter("status"));
                    paymentDao.createPayment(payment);
                    response.sendRedirect("paymentSuccess.jsp");
                    break;
                case "read":
                    paymentID = Long.parseLong(request.getParameter("paymentID"));
                    payment = paymentDao.readPayment(paymentID);
                    request.setAttribute("payment", payment);
                    request.getRequestDispatcher("paymentDetails.jsp").forward(request, response);
                    break;
                case "update":
                    payment = new payment(Long.parseLong(request.getParameter("paymentID")),
                                          Double.parseDouble(request.getParameter("amount")),
                                          request.getParameter("method"),
                                          request.getParameter("date"),
                                          request.getParameter("status"));
                    paymentDao.updatePayment(payment);
                    response.sendRedirect("updateSuccess.jsp");
                    break;
                case "delete":
                    paymentID = Long.parseLong(request.getParameter("paymentID"));
                    paymentDao.deletePayment(paymentID);
                    response.sendRedirect("deleteSuccess.jsp");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
