import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;

import model.DAO.DBConnector;
import model.DAO.productDAO;
import model.product;


public class producttest {
    public DBConnector connector;
    public Connection conn;
    public productDAO PDDAO;

    @Test
    public void readtest() throws SQLException, ClassNotFoundException{
        connector = new DBConnector();
        ArrayList<product> testlist;
        conn = connector.openConnection();
        PDDAO = new productDAO(conn);
        testlist = PDDAO.fetchProducts();

        assertTrue(testlist.size()>=1);
        
    }
}