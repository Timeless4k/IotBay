package Controller;

import model.cart;
import model.DAO.cartDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

        try {
            long userID = ((Long) session.getAttribute("userID")).longValue();
            long productID = Long.parseLong(request.getParameter("productID"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            switch (action) {
                case "add":
                    cart newItem = new cart(userID, productID, quantity);
                    if (cartDao.addCart(newItem)) {
                        response.getWriter().print("success:" + cartDao.getCartItemCount(userID)); // Assuming such a method exists
                    } else {
                        response.getWriter().print("Failed to add item to cart.");
                    }
                    break;
                case "update":
                    cart updateItem = new cart(userID, productID, quantity);
                    if (cartDao.updateCartItem(updateItem)) {
                        response.getWriter().print("success:" + cartDao.getCartItemCount(userID));
                    } else {
                        response.getWriter().print("Failed to update cart item.");
                    }
                    break;
                case "remove":
                    if (cartDao.removeCart(userID, productID)) {
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
