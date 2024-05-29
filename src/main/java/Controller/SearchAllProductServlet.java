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
public class SearchAllProductServlet extends HttpServlet{
    private Connection conn;
    private productDAO PDAO;

    /**
     * Handles HTTP GET requests. Retrieves a list of products from the database and stores it in the session attribute "productList".
     * 
     * @param request  the HttpServletRequest object that contains the request information
     * @param response the HttpServletResponse object that contains the response information
     * @throws ServletException if there is a servlet-related problem
     * @throws IOException      if there is an I/O problem
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");
        try{
            PDAO = new productDAO(conn);
            session.setAttribute("productList", PDAO.fetchProducts());
        } catch (SQLException ex) {
            System.out.println(ex);
        }   

        // request.getRequestDispatcher("product.jsp").include(request, response); // redirect to page
    }
}
