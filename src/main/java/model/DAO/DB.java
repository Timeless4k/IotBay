package model.DAO;

import java.sql.Connection;
public abstract class DB {
    protected String dbschema = "IotBay";
    protected String dbuser = "root";
    protected String dbpass = "Password123456";
    protected String driver = "com.mysql.cj.jdbc.Driver";
    protected String dbURL = "jdbc:mysql://localhost:3306/";
    protected Connection conn;
}
