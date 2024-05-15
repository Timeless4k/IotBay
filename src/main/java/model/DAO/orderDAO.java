package model.DAO;

import java.sql.*;
import java.util.ArrayList;
import model.order;
import java.util.Random;

public class orderDAO {

    private Connection conn;

    private PreparedStatement fetchOrder;
    private PreparedStatement createOrder;
    private PreparedStatement updateOrder;
    private PreparedStatement deleteOrder;
    private PreparedStatement searchOrderID;
    private PreparedStatement searchOrderDate;
    private PreparedStatement checkOrderID;

    private String readQuery = "SELECT * FROM orders";
    private String CreateQuery = "INSERT INTO orders VALUES (?,?,?,?,?)";
    private String UpdateQuery = "UPDATE orders SET OrderName = ?, OrderType = ?, OrderQuantity = ?, OrderDate = ? WHERE OrderID=?";
    private String DeleteQuery = "DELETE FROM orders WHERE OrderID=?";
    private String SearchQueryID = "SELECT * FROM orders WHERE OrderID LIKE ? ";
    private String SearchQueryDate = "SELECT * FROM orders WHERE OrderDate LIKE ? ";
    private String CheckOrderIDQuery = "SELECT COUNT(*) FROM orders WHERE OrderID = ?";

    /* Initialisation of orderDAO, Check if the connection works, if it throws Exception, error occurs */
    public orderDAO(Connection connection) throws SQLException {
        this.conn = connection;
        conn.setAutoCommit(false);

        fetchOrder = conn.prepareStatement(readQuery);
        createOrder = conn.prepareStatement(CreateQuery);
        deleteOrder = conn.prepareStatement(DeleteQuery);
        updateOrder = conn.prepareStatement(UpdateQuery);
        searchOrderID = conn.prepareStatement(SearchQueryID);
        searchOrderDate = conn.prepareStatement(SearchQueryDate);
        checkOrderID = conn.prepareStatement(CheckOrderIDQuery);
    }

    /* Unique Order ID Generation */
    public long randomOrderID(){
        return Math.abs(new Random().nextLong());
    }

    /* Unique Order ID Check */
    public long uniqueOrderID() {
        long orderID = 0;
        boolean isUnique = false;
        while (!isUnique) {
            orderID = randomOrderID();
            String checkSql = "SELECT COUNT(*) FROM orders WHERE OrderID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                pstmt.setLong(1, orderID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        if (count == 0) {
                            isUnique = true;
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error checking order ID uniqueness: " + e.getMessage());
            }
        }
        return orderID;
    }

    /* Check if Order ID exists */
    public boolean orderIDExists(long orderID) throws SQLException {
        checkOrderID.setLong(1, orderID);
        ResultSet rs = checkOrderID.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    /* Retrieving order with Order ID */
    public order getOrder(long orderID) throws SQLException {
        fetchOrder.setLong(1, orderID);
        ResultSet rs = fetchOrder.executeQuery();
        order o = new order();
        while (rs.next()) {
            o.setOrderID(rs.getLong(1));
            o.setOrderName(rs.getString(2));
            o.setOrderType(rs.getString(3));
            o.setOrderQuantity(rs.getLong(4));
            o.setOrderDate(rs.getString(5));
        }
        return o;
    }

    /* Fetching the existed data that are already sets */
    public ArrayList<order> fetchOrders() throws SQLException {
        ResultSet rs = fetchOrder.executeQuery();

        ArrayList<order> orders = new ArrayList<>(); // The ArrayList that holds orders
        while (rs.next()) { // Retrieving variables from db query
            long orderID = rs.getLong(1);
            String orderName = rs.getString(2);
            String orderType = rs.getString(3);
            long orderQuantity = rs.getLong(4);
            String orderDate = rs.getString(5);

            order o = new order();
            o.setOrderID(orderID);
            o.setOrderName(orderName);
            o.setOrderType(orderType);
            o.setOrderQuantity(orderQuantity);
            o.setOrderDate(orderDate);

            orders.add(o); // Adding object to end of orders array
        }

        return orders;
    }

    /* Creating a new order in the database, if it's returning true, the order successfully created. */
    public boolean createOrder(long orderID, String orderName, String orderType, long orderQuantity, String orderDate) throws SQLException {
        String sql = "INSERT INTO orders (OrderID, OrderName, OrderType, OrderQuantity, OrderDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, orderID);
            pstmt.setString(2, orderName);
            pstmt.setString(3, orderType);
            pstmt.setLong(4, orderQuantity);
            pstmt.setString(5, orderDate);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Create order failed: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Transaction rollback failed: " + ex.getMessage());
            }
        }
        return false;
    }    

    /* Updating a new order in the database base off. */
    public void updateOrder(long orderID, String orderName, String orderType, long orderQuantity, String orderDate) throws SQLException {
        if (!orderIDExists(orderID)) {
            throw new SQLException("Order ID " + orderID + " does not exist.");
        }

        updateOrder.setLong(5, orderID);
        updateOrder.setString(1, orderName);
        updateOrder.setString(2, orderType);
        updateOrder.setLong(3, orderQuantity);
        updateOrder.setString(4, orderDate);

        updateOrder.executeUpdate();
    }

    /* Deleting an order from the DB based off orderID */
    public void deleteOrder(long orderID) throws SQLException{
        deleteOrder.setLong(1, orderID);
        deleteOrder.executeUpdate();
    }

    /* Searching with filtering ID and the date */
    public ArrayList<order> searchOrderBy(String type, String query) throws SQLException {
        ResultSet rs;

        if (type.equals("OrderID")) {
            searchOrderID.setString(1, "%" + query + "%");
            rs = searchOrderID.executeQuery();
        } else if (type.equals("OrderDate")) {
            searchOrderDate.setString(1, "%" + query + "%");
            rs = searchOrderDate.executeQuery();
        } else {
            throw new SQLException("Invalid search type: " + type);
        }

        ArrayList<order> orders = new ArrayList<>(); // ArrayList to hold orders
        while (rs.next()) { // go to next item in rs table then run
            long orderID = rs.getLong(1); //retrieve variables from db query
            String orderName = rs.getString(2);
            String orderType = rs.getString(3);
            long orderQuantity = rs.getLong(4);
            String orderDate = rs.getString(5);

            order o = new order();
            o.setOrderID(orderID);
            o.setOrderName(orderName);
            o.setOrderType(orderType);
            o.setOrderQuantity(orderQuantity);
            o.setOrderDate(orderDate);

            orders.add(o); // add object to end of orders array
        }

        return orders;
    }
}