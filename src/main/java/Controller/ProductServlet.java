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
public class ProductServlet extends HttpServlet{
    private Connection conn;
    private productDAO PDAO;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");
        try{
            PDAO = new productDAO(conn);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        
    }
}
