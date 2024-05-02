import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import javax.validation.constraints.AssertFalse;

import java.sql.Connection;
import java.sql.SQLException;

import model.DAO.DBConnector;
import model.DAO.productDAO;
import model.product;


public class producttest {
    public DBConnector connector;
    public Connection conn;
    public productDAO PDDAO;

    public void intPDAO() throws SQLException,ClassNotFoundException{
        if(PDDAO == null) {
            connector = new DBConnector();
            conn = connector.openConnection();
            PDDAO = new productDAO(conn);
        }
    }

    @Test
    public void connTest() throws SQLException,ClassNotFoundException{
        intPDAO();
        assertNotNull(conn, "Connection does not exist");
    }

    @Test
    public void readtest() throws SQLException, ClassNotFoundException{
        intPDAO();
        ArrayList<product> testlist;
        testlist = PDDAO.fetchProducts();

        assertTrue(testlist.size()>=1, "Table is empty");
        
    }

    @Test
    public void createTest() throws SQLException, ClassNotFoundException{
        intPDAO();
        ArrayList<product> before = PDDAO.fetchProducts();
        PDDAO.addProduct(9999, "TestOBJ", "InStock", "2024-01-01", 500, "A Test Thinggy", "Test", 500.99);
        ArrayList<product> after = PDDAO.fetchProducts();
        PDDAO.removeProduct(9999);
        // product testprod = new product();
        // testprod.setpID(9999);
        // testprod.setName("TestOBJ");
        // testprod.setStatus("InStock");
        // testprod.setReleaseDate("2024-01-01");
        // testprod.setStock(500);
        // testprod.setDescription("A Test Thinggy");
        // testprod.setType("Test");
        // testprod.setPrice(500.99);

        // ArrayList<product> readdata = PDDAO.fetchProducts();


        assertTrue(before.size() < after.size());
    }


    @Test
    public void deleteTest() throws SQLException, ClassNotFoundException{
        intPDAO();
        PDDAO.addProduct(9999, "TestOBJ", "InStock", "2024-01-01", 500, "A Test Thinggy", "Test", 500.99);
        ArrayList<product> before = PDDAO.fetchProducts();
        PDDAO.removeProduct(9999);
        ArrayList<product> after = PDDAO.fetchProducts();
        assertTrue(before.size() > after.size());
    }

    @Test
    public void updateTest() throws SQLException, ClassNotFoundException{
        
    }
}