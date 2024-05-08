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

public class UpdateProductServlet extends HttpServlet {
    private Connection conn;
    private productDAO PDAO;

    

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");
        try{
            PDAO = new productDAO(conn);
        } catch (SQLException ex) {
            System.out.println(ex);
        }   

        String email = request.getParameter("newEmail");
        String password = request.getParameter("newEmail");
        String firstName = request.getParameter("newEmail");
        String middleName = request.getParameter("newEmail");
        String lastName = request.getParameter("newEmail");
        String birthDate = request.getParameter("newEmail");
        String mobilePhone = request.getParameter("newEmail");
        String gender = request.getParameter("newEmail");
        String creationDate = request.getParameter("newEmail");
        String uType = request.getParameter("newEmail");
        long uID = Long.parseLong(request.getParameter("newEmail"));
        
    }
}
