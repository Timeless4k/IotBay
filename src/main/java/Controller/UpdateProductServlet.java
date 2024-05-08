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

    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthDate;
    private String mobilePhone;
    private String gender;
    private String creationDate;
    private String uType;
    private long uID;

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

        
    }
}
