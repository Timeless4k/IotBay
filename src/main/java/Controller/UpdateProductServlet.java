package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.productDAO;
import util.ValidationUtils;

public class UpdateProductServlet extends HttpServlet {
    private Connection conn;
    private productDAO PDAO;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");

        if (PDAO == null) {
            try {
                PDAO = new productDAO(conn);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addProduct(request, response, session);
                    break;
                case "delete":
                    deleteProduct(request, response); // might be implemented later
                    break;
                case "update":
                    updateProduct(request, response); // might be implemented later
                    break;
                default:
                    request.getRequestDispatcher("productmanagement.jsp").include(request, response);
                    break;
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");

        if (PDAO == null) {
            try {
                PDAO = new productDAO(conn);
                System.out.println("added PDAO to session");
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        showProduct(request, response, session);
    }

    public void showProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        try {
            session.setAttribute("products", PDAO.fetchProducts());
            request.getRequestDispatcher("productmanagement.jsp").include(request, response);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long pID = Long.parseLong(request.getParameter("id"));
            String pName = request.getParameter("name");
            String pStatus = request.getParameter("status");
            String pReleaseDate = request.getParameter("releaseDate");
            long pStockLevel = Long.parseLong(request.getParameter("stockLevel"));
            String pDescription = request.getParameter("description");
            String pType = request.getParameter("type");
            double pPrice = Double.parseDouble(request.getParameter("price"));

            if (!ValidationUtils.isValidProductName(pName)) {
                request.setAttribute("validationError", "Invalid product name");
                request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
                return;
            }

            PDAO.updateProduct(pID, pName, pStatus, pReleaseDate, pStockLevel, pDescription, pType, pPrice);
            conn.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
            request.setAttribute("errorMessage", "Database operation failed. Please try again later.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        response.sendRedirect("UpdateProductServlet");
    }

    public long genID() {
        Random random = new Random();
        long pID;
        boolean isuniqueId = false;
        do {
            pID = Math.abs(1000000000000000L + random.nextLong());
            try {
                isuniqueId = PDAO.checkpID(pID);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } while (!isuniqueId);
        return pID;
    }

    public void addProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    throws ServletException, IOException {
        try {
            String pName = request.getParameter("name");
            String pStatus = request.getParameter("status");
            String pReleaseDate = request.getParameter("releaseDate");
            long pStockLevel = Long.parseLong(request.getParameter("stockLevel"));
            String pDescription = request.getParameter("description");
            String pType = request.getParameter("type");
            double pPrice = Double.parseDouble(request.getParameter("price"));

            if (!ValidationUtils.isValidProductName(pName)) {
                request.setAttribute("validationError", "Invalid product name");
                request.getRequestDispatcher("productmanagement.jsp").forward(request, response);
                return;
            }

            PDAO.addProduct(genID(), pName, pStatus, pReleaseDate, pStockLevel, pDescription, pType, pPrice);
            conn.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
            request.setAttribute("errorMessage", "Database operation failed. Please try again later.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        response.sendRedirect("UpdateProductServlet");
    }

    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long delpID = Long.parseLong(request.getParameter("pID"));
            PDAO.removeProduct(delpID);
            conn.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        response.sendRedirect("UpdateProductServlet");
    }
}
