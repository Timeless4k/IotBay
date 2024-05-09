package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.productDAO;
import model.product;

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

        type = (String) request.getParameter("SearchType");
        query = (String) request.getParameter("SearchQuery");

        try{
            PDAO = new productDAO(conn);
            ArrayList<product> test = PDAO.searchProdBy(type, query);
            session.setAttribute("productList", test);
            System.out.println("SEARCHES ARE HAPPENING");
            System.out.println(test.size());
            System.out.println(type);
            System.out.println(query);
        } catch (SQLException ex) {
            System.out.println(ex);
        }   

        request.getRequestDispatcher("product.jsp").include(request, response); // redirect to page
    }
}
