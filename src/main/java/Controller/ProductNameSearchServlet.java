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

public class ProductNameSearchServlet extends HttpServlet {
    private Connection conn;
    private productDAO PDAO;
    private String type;
    private String query;


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");

        type = (String) session.getAttribute("sType");
        query = (String) session.getAttribute("sQuery");

        try{
            PDAO = new productDAO(conn);
            session.setAttribute("productList", PDAO.searchProdBy(type, query));
        } catch (SQLException ex) {
            System.out.println(ex);
        }   

        // request.getRequestDispatcher("product.jsp").include(request, response); // redirect to page
    }
}
