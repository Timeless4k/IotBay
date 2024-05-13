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

public class RemoveProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        try {
            productDAO pDao = new productDAO(conn);
            long pID = Long.parseLong(request.getParameter("productID"));
            pDao.removeProduct(pID);
            response.sendRedirect("productRemovedSuccess.jsp");
        } catch (SQLException e) {
            throw new ServletException("SQL error while initializing productDAO or removing product", e);
        }
    }
}