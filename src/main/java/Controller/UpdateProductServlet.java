package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.productDAO;
import model.product;
import java.util.Random;


public class UpdateProductServlet extends HttpServlet {
    private Connection conn;
    private productDAO PDAO;

    /**
     * Handles the HTTP POST request. This method is responsible for processing the form data
     * submitted by the client and performing the corresponding action based on the "action"
     * parameter.
     *
     * @param request  the HttpServletRequest object that contains the request parameters and attributes
     * @param response the HttpServletResponse object that contains the response to be sent back to the client
     * @throws ServletException if there is a servlet-related problem
     * @throws IOException      if there is an I/O problem
     */
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
        

        String action = request.getParameter("action");
        System.out.println("Deciding actions");
        if(action != null) {
            switch(action) {
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

    /**
     * Handles HTTP GET requests for updating a product.
     * This instanciates the productDAO object and adds it to the session before running the showProduct method.
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


        if(PDAO == null) {
            try{
                PDAO = new productDAO(conn);
                System.out.println("added PDAO to session");
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        System.out.println("Get request for productmanagement.jsp");
        showProduct(request, response, session);
    }

    /**
     * Retrieves the list of products from the database, stores it to an attribute and displays them on the product management page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param session  the HttpSession object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    public void showProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        try {
            System.out.println("Showing product");
            session.setAttribute("products", PDAO.fetchProducts());
            request.getRequestDispatcher("productmanagement.jsp").include(request, response);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Updates a product based on the provided information using the productDAO.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object for sending the response
     * @throws ServletException if there is a servlet error
     * @throws IOException      if there is an I/O error
     */
    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Updating product");
            long pID = Long.parseLong(request.getParameter("id"));
            String pName = request.getParameter("name");
            String pStatus = request.getParameter("status");
            String pReleaseDate = request.getParameter("releaseDate");
            long pStockLevel = Long.parseLong(request.getParameter("stockLevel"));
            String pDescription = request.getParameter("description");
            String pType = request.getParameter("type");
            double pPrice = Double.parseDouble(request.getParameter("price"));
            PDAO.updateProduct(pID, pName, pStatus, pReleaseDate, pStockLevel, pDescription, pType, pPrice);
            conn.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        System.out.println("Redirecting to productmanagement.jsp");
        response.sendRedirect("UpdateProductServlet");
    }

    /**
     * Generates a unique ID for a product.
     * 
     * @return The generated unique ID.
     */
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

    /**
     * Adds a new product to the database using the productDAO based off data from the session.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object for sending the response
     * @param session  the HttpSession object for managing session data
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    public void addProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    throws ServletException, IOException {
        try {
            System.out.println("Adding product");
            String pName = request.getParameter("name");
            String pStatus = request.getParameter("status");
            String pReleaseDate = request.getParameter("releaseDate");
            long pStockLevel = Long.parseLong(request.getParameter("stockLevel"));
            String pDescription = request.getParameter("description");
            String pType = request.getParameter("type");
            double pPrice = Double.parseDouble(request.getParameter("price"));
            PDAO.addProduct(genID(), pName, pStatus, pReleaseDate, pStockLevel, pDescription, pType, pPrice);
            conn.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        System.out.println("Redirecting to productmanagement.jsp");
        response.sendRedirect("UpdateProductServlet");
    }

    /**
     * Deletes a product from the database based on the provided product ID.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object used to send the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Deleting product");
            long delpID = Long.parseLong(request.getParameter("pID"));
            PDAO.removeProduct(delpID);
            conn.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        System.out.println("Redirecting to productmanagement.jsp");
        response.sendRedirect("UpdateProductServlet");
    }
}
