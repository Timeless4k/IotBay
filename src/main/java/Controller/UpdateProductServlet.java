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

        String action = request.getParameter("action");

        if(action != null) {
            switch(action) {
                case "update":
                    updateProduct(request, response);
                    break;
                case "show":
                    showProduct(request, response); // redundant entry might delete
                    break;
                case "add":
                    addProduct(request, response);
                    break;
                default:
                    showProduct(request, response);
                    break;
            }
        }
    }

    public void showProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("products", PDAO.fetchProducts());
            request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
