package model.DAO;

import java.sql.*;
import java.time.LocalDate;
import model.order;

public class orderDAO {
    private Connection conn;

    public orderDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean createOrder(order order) throws SQLException {
        String sql = "INSERT INTO orders (orderID, userID, orderDate, orderStatus, totalAmount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, order.getOrderID());
            statement.setLong(2, order.getUserID());
            statement.setDate(3, Date.valueOf(order.getOrderDate()));
            statement.setString(4, order.getOrderStatus());
            statement.setDouble(5, order.getTotalAmount());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }    

    public order readOrder(long orderID) throws SQLException {
        String sql = "SELECT * FROM orders WHERE orderID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, orderID);
            ResultSet resultSet = statement.executeQuery();
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
            statement.executeUpdate();
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