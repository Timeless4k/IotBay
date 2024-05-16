package Controller;

import model.order;
import model.DAO.orderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private orderDAO orderDAO;
    private Connection conn;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");
    
        if (orderDAO == null) {
            try {
                orderDAO = new orderDAO(conn);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        
        String action = request.getParameter("action");
        System.out.println("Deciding actions");
        if (action != null) {
            try {
                switch (action) {
                    case "add":
                        addOrder(request, response, session);
                        break;
                    case "delete":
                        deleteOrder(request, response);
                        break;
                    case "update":
                        updateOrder(request, response);
                        break;
                    case "search":
                        searchOrders(request, response, session);
                        break;
                    default:
                        listOrders(request, response, session); 
                        break;
                }
            } catch (SQLException ex) {
                throw new ServletException("Database access error.", ex);
            }
        }
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        conn = (Connection) session.getAttribute("acticonn");

        if (orderDAO == null) {
            try {
                orderDAO = new orderDAO(conn);
                System.out.println("added orderDAO to session");
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        System.out.println("Get request for orders.jsp");
        listOrders(request, response, session); 
    }    

    public void listOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        try {
            System.out.println("Fetching orders...");
            conn = (Connection) session.getAttribute("acticonn");
            if (orderDAO == null && conn != null) {
                orderDAO = new orderDAO(conn);
            }
            ArrayList<order> orders = orderDAO.fetchOrders();
            session.setAttribute("orders", orders);
            System.out.println("Orders fetched: " + orders.size());
            request.getRequestDispatcher("orders.jsp").include(request, response);
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }        

    private void searchOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ServletException, IOException {
        String searchType = request.getParameter("searchType");
        String searchQuery = request.getParameter("searchQuery");

        try {
            ArrayList<order> orders = orderDAO.searchOrderBy(searchType, searchQuery);
            session.setAttribute("orders", orders);
            System.out.println("SEARCHES ARE HAPPENING");
            System.out.println(orders.size());
            System.out.println(searchType);
            System.out.println(searchQuery);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        request.getRequestDispatcher("orders.jsp").include(request, response); // redirect to page
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        try {
            System.out.println("Adding order");
            String orderName = request.getParameter("orderName");
            String orderType = request.getParameter("orderType");
            long orderQuantity = Long.parseLong(request.getParameter("orderQuantity"));
            String orderDate = request.getParameter("orderDate");
    
            // Validation similar to orderDAO.createOrder method
            if (orderName == null || orderName.isEmpty()) {
                throw new SQLException("Order name cannot be null or empty");
            }
            if (orderType == null || orderType.isEmpty()) {
                throw new SQLException("Order type cannot be null or empty");
            }
            if (orderQuantity < 0) {
                throw new SQLException("Order quantity cannot be negative");
            }
            if (orderDate == null || orderDate.isEmpty()) {
                throw new SQLException("Order date cannot be null or empty");
            }
    
            boolean orderCreated = orderDAO.createOrder(orderName, orderType, orderQuantity, orderDate);
            if (orderCreated) {
                System.out.println("Order created successfully");
            } else {
                System.out.println("Failed to create order");
            }
    
            // Commit the transaction
            conn.commit();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Rollback Error: " + rollbackEx.getMessage());
            }
        } catch (NumberFormatException ex) {
            System.out.println("Number Format Error: " + ex.getMessage());
        }
    
        System.out.println("Redirecting to orders.jsp");
        response.sendRedirect("orders.jsp");
    }    

    public void updateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Updating order");
            
            long orderID = Long.parseLong(request.getParameter("id"));
            String orderName = request.getParameter("name");
            String orderType = request.getParameter("type");
            long orderQuantity = Long.parseLong(request.getParameter("quantity"));
            String orderDate = request.getParameter("date");
    
            if (orderName == null || orderName.isEmpty()) {
                throw new SQLException("Order name cannot be null or empty");
            }
            if (orderType == null || orderType.isEmpty()) {
                throw new SQLException("Order type cannot be null or empty");
            }
            if (orderQuantity < 0) {
                throw new SQLException("Order quantity cannot be negative");
            }
            if (orderDate == null || orderDate.isEmpty()) {
                throw new SQLException("Order date cannot be null or empty");
            }
    
            orderDAO.updateOrder(orderID, orderName, orderType, orderQuantity, orderDate);
            conn.commit();
    
            System.out.println("Redirecting to orderManagement.jsp");
            response.sendRedirect("orderManagement.jsp");
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Rollback Error: " + rollbackEx.getMessage());
            }
        } catch (NumberFormatException ex) {
            System.out.println("Number Format Error: " + ex.getMessage());
        }
    }    

    public void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Deleting order");
            long orderID = Long.parseLong(request.getParameter("id"));
    
            orderDAO.deleteOrder(orderID);
            conn.commit();
    
            System.out.println("Order deleted successfully");
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Rollback Error: " + rollbackEx.getMessage());
            }
        } catch (NumberFormatException ex) {
            System.out.println("Number Format Error: " + ex.getMessage());
        }
    
        System.out.println("Redirecting to orderManagement.jsp");
        response.sendRedirect("orderManagement.jsp");
    }    

}
