package model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.cart;

public class cartDAO {
    private Connection conn;

    public cartDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addCart(cart item) throws SQLException {
        String sql = "INSERT INTO cart (userID, productID, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, item.getUserID());
            statement.setLong(2, item.getProductID());
            statement.setInt(3, item.getQuantity());
            statement.executeUpdate();
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateCartItem(cart item) throws SQLException {
        String sql = "UPDATE cart SET quantity = ? WHERE userID = ? AND productID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, item.getQuantity());
            statement.setLong(2, item.getUserID());
            statement.setLong(3, item.getProductID());
            statement.executeUpdate();
            return statement.executeUpdate() > 0;
        }
    }

    public boolean removeCart(long userID, long productID) throws SQLException {
        String sql = "DELETE FROM cart WHERE userID = ? AND productID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, userID);
            statement.setLong(2, productID);
            statement.executeUpdate();
            return statement.executeUpdate() > 0;
        }
    }

    public List<cart> getCart(long userID) throws SQLException {
        List<cart> items = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE userID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                items.add(new cart(
                    resultSet.getLong("userID"),
                    resultSet.getLong("productID"),
                    resultSet.getInt("quantity")
                ));
            }
            return items;
        }
    }

    public int getCartItemCount(long userID) throws SQLException {
        String sql = "SELECT SUM(quantity) AS total FROM cart WHERE userID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, userID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total"); // Ensure the column fetched matches the SUM alias
            }
            return 0; // Return 0 if no items are found
        }
    }
}