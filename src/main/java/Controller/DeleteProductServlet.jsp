package Controller;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.productDAO;

public class DeleteProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        try {
            productDAO pDao = new productDAO(conn);
            long productID = Long.parseLong(request.getParameter("productID"));
            
            if (pDao.removeProduct(productID)) {
                response.sendRedirect("productRemovedSuccess.jsp"); // Redirect to a success page
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to remove product.");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error while removing product", e);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID format.");
        }
    }
}