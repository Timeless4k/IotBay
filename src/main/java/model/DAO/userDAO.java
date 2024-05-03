package model.DAO;

import java.util.ArrayList;
import java.sql.*;
import model.user;

public class userDAO {
    private Connection conn; // store active connection here
    public userDAO(Connection connection) throws SQLException {
		this.conn = connection;
		conn.setAutoCommit(true);
	}
}
	