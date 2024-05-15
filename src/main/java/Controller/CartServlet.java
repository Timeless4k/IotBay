package Controller;

import model.cart;
import model.DAO.cartDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            response.getWriter().write("Database connection not available.");
            return;
        }

        cartDAO cartDao = new cartDAO(conn);
        long userID = ((Long) session.getAttribute("userID")).longValue();
        long productID = Long.parseLong(request.getParameter("productID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            switch (action) {
                case "add":
                    cart newItem = new cart(userID, productID, quantity);
                    boolean added = cartDao.addCart(newItem);
                    if (added) {
                        response.getWriter().print("success:" + cartDao.getCartItemCount(userID));
                    } else {
                        response.getWriter().print("Failed to add item to cart.");
                    }
                    break;
                case "update":
                    cart updateItem = new cart(userID, productID, quantity);
                    boolean updated = cartDao.updateCartItem(updateItem);
                    if (updated) {
                        response.getWriter().print("success:" + cartDao.getCartItemCount(userID));
                    } else {
                        response.getWriter().print("Failed to update cart item.");
                    }
                    break;
                case "remove":
                    boolean removed = cartDao.removeCart(userID, productID);
                    if (removed) {
                        response.getWriter().print("success:" + cartDao.getCartItemCount(userID));
                    } else {
                        response.getWriter().print("Failed to remove item from cart.");
                    }
                    break;
                default:
                    response.getWriter().print("Invalid action.");
                    break;
            }
        } catch (Exception e) {
            response.getWriter().print("Error handling cart: " + e.getMessage());
            e.printStackTrace();
        }
    }
}