import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DAO.DBConnector;
import model.DAO.orderDAO;
import model.order;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class OrderDAOTest {

    private orderDAO ODAO;
    private Connection conn;
    public DBConnector connector;

    public void intODAO() throws SQLException, ClassNotFoundException{
        if(ODAO == null) {
            connector = new DBConnector();
            conn = connector.openConnection();
            ODAO = new orderDAO(conn);
        }
        conn.setAutoCommit(false);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        conn.rollback();
        conn.close();
    }

    @Test
    public void connTest() throws SQLException,ClassNotFoundException{
        intODAO();
        assertNotNull(conn, "Connection does not exist");
    }

}