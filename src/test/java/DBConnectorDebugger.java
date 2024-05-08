import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;

import model.DAO.DBConnector;

public class DBConnectorDebugger {
    public DBConnector connector;
    public Connection conn;

    @Test
    public void connTest() throws SQLException,ClassNotFoundException{
        connector = new DBConnector();
        conn = connector.openConnection();
        assertNotNull(conn, "Connection does not exist, is server running and populated?");
    }

}
