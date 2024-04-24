package model.DAO;

import java.sql.SQLException;

public class DBConnector extends DB {
    public DBConnector() throws ClassNotFoundException, SQLException {
        Class.forName(driver);

        Properties dbProperties = new Properties();
        dbProperties.put("user")
    }
}
