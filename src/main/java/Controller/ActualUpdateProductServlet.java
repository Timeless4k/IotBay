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


public class ActualUpdateProductServlet extends HttpServlet {
    private Connection conn;
    private productDAO PDAO;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");
        if(PDAO == null) {
            try{
                PDAO = new productDAO(conn);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        product rproduct = new product();
        Long pID = Long.parseLong(request.getParameter("pID"));
        try {
            rproduct = PDAO.getProduct(pID);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        session.setAttribute("requestedProduct", rproduct);
        request.getRequestDispatcher("productupdatepage.jsp").include(request, response);
    }

}
