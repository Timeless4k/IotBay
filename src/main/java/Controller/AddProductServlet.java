package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.productDAO;

public class AddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        try {
            productDAO pDao = new productDAO(conn);
            long pID = Long.parseLong(request.getParameter("productID"));
            String name = request.getParameter("productName");
            String status = request.getParameter("productStatus");
            String releaseDate = request.getParameter("productReleaseDate");
            long stockLevel = Long.parseLong(request.getParameter("productStockLevel"));
            String description = request.getParameter("productDescription");
            String type = request.getParameter("productType");
            double price = Double.parseDouble(request.getParameter("productPrice"));

            if (pDao.addProduct(pID, name, status, releaseDate, stockLevel, description, type, price)) {
                response.sendRedirect("productAddedSuccess.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to add product.");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error while initializing productDAO or adding product", e);
        }
    }
}