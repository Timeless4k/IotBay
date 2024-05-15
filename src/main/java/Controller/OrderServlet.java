package Controller;

import model.order;
import model.DAO.orderDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            response.getWriter().write("Database connection not available. Please check the connection settings.");
            return;
        }

        orderDAO orderDao = new orderDAO(conn);
        try {
            switch (action) {
                case "create":
                    createOrder(request, response, orderDao);
                    break;
                case "read":
                    readOrder(request, response, orderDao);
                    break;
                case "update":
                    updateOrder(request, response, orderDao);
                    break;
                case "delete":
                    deleteOrder(request, response, orderDao);
                    break;
                default:
                    response.getWriter().print("Invalid action.");
                    break;
            }
        } catch (Exception e) {
            response.getWriter().print("Error handling order: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response, orderDAO orderDao) throws IOException {
        try {
            long orderID = Long.parseLong(request.getParameter("orderID"));
            long userID = Long.parseLong(request.getParameter("userID"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            String status = request.getParameter("status");
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
    
            order newOrder = new order(orderID, userID, date, status, totalAmount);
            if (orderDao.createOrder(newOrder)) {
                response.sendRedirect("orderSuccess.jsp");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("Failed to create order.");
            }
        } catch (NumberFormatException | SQLException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid input or server error: " + e.getMessage());
        }
    }    

    private void readOrder(HttpServletRequest request, HttpServletResponse response, orderDAO orderDao) throws IOException, ServletException {
        try {
            long orderID = Long.parseLong(request.getParameter("orderID"));
            order existingOrder = orderDao.readOrder(orderID);
            if (existingOrder != null) {
                request.setAttribute("order", existingOrder);
                request.getRequestDispatcher("/orderSummary.jsp").forward(request, response);
            } else {
                response.getWriter().println("Order not found.");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error while reading order", e);
        }
    }    

    private void updateOrder(HttpServletRequest request, HttpServletResponse response, orderDAO orderDao) throws IOException, ServletException {
        try {
            long orderID = Long.parseLong(request.getParameter("orderID"));
            order existingOrder = orderDao.readOrder(orderID);
            if (existingOrder != null) {
                existingOrder.setUserID(Long.parseLong(request.getParameter("userID")));
                existingOrder.setOrderDate(java.sql.Date.valueOf(request.getParameter("date")).toLocalDate());
                existingOrder.setOrderStatus(request.getParameter("status"));
                existingOrder.setTotalAmount(Double.parseDouble(request.getParameter("totalAmount")));
                
                if (orderDao.updateOrder(existingOrder)) {
                    response.sendRedirect("orderUpdateSuccess.jsp");
                } else {
                    response.getWriter().println("Failed to update order.");
                }
            } else {
                response.getWriter().println("Order not found.");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error while updating order", e);
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response, orderDAO orderDao) throws IOException, ServletException {
        try {
            long orderID = Long.parseLong(request.getParameter("orderID"));
            if (orderDao.deleteOrder(orderID)) {
                response.sendRedirect("orderDeleteSuccess.jsp");
            } else {
                response.getWriter().println("Failed to delete order.");
            }
        } catch (SQLException e) {
            throw new ServletException("SQL error while deleting order", e);
        }
    }
}