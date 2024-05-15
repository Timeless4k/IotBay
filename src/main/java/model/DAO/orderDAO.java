package model.DAO;

import model.order;
import model.cart;
import java.sql.*;
import java.util.List;

public class orderDAO {
    private Connection conn;

    public orderDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean createOrder(order newOrder) throws SQLException {
        String orderSql = "INSERT INTO orders (orderID, userID, orderDate, orderStatus, totalAmount, shippingAddress, paymentMethod) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items (orderID, productID, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement orderStmt = conn.prepareStatement(orderSql);
             PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
            
            conn.setAutoCommit(false);
            
            // insert order
            orderStmt.setLong(1, newOrder.getOrderID());
            orderStmt.setLong(2, newOrder.getUserID());
            orderStmt.setDate(3, Date.valueOf(newOrder.getOrderDate()));
            orderStmt.setString(4, newOrder.getOrderStatus());
            orderStmt.setDouble(5, newOrder.getTotalAmount());
            orderStmt.setString(6, newOrder.getShippingAddress());
            orderStmt.setString(7, newOrder.getPaymentMethod());
            orderStmt.executeUpdate();
            
            //insert orderitems
            for (cart item : newOrder.getItems()) {
                itemStmt.setLong(1, newOrder.getOrderID());
                itemStmt.setLong(2, item.getProductID());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public order readOrder(long orderID) throws SQLException {
        String sql = "SELECT * FROM orders WHERE orderID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new order(
                    resultSet.getLong("orderID"),
                    resultSet.getLong("userID"),
                    resultSet.getDate("orderDate").toLocalDate(),
                    resultSet.getString("orderStatus"),
                    resultSet.getDouble("totalAmount")
                );
            }
            return null;
        }
    }

    public boolean updateOrder(order order) throws SQLException {
        String sql = "UPDATE orders SET userID = ?, orderDate = ?, orderStatus = ?, totalAmount = ? WHERE orderID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, order.getUserID());
            statement.setDate(2, Date.valueOf(order.getOrderDate()));
            statement.setString(3, order.getOrderStatus());
            statement.setDouble(4, order.getTotalAmount());
            statement.setLong(5, order.getOrderID());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteOrder(long orderID) throws SQLException {
        String sql = "DELETE FROM orders WHERE orderID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, orderID);
            return statement.executeUpdate() > 0;
        }
    }

    public boolean cancelOrder(long orderID) throws SQLException {
        String sql = "UPDATE orders SET orderStatus = 'cancelled' WHERE orderID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, orderID);
            return statement.executeUpdate() > 0;
        }
    }
}