import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import javax.validation.constraints.AssertTrue;

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
        conn.setAutoCommit(false);
    }

    @AfterEach
    public void teardown() throws SQLException{
        conn.rollback();
        conn.close();
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
        assertTrue(PDDAO.addProduct(9999, "TestOBJ", "InStock", "2024-01-01", 500, "A Test Thinggy", "Test", 500.99));
    }



    @Test
    public void createWrongEntry() throws SQLException, ClassNotFoundException{
        intPDAO();
        assertThrows(SQLException.class, () -> {
            PDDAO.addProduct(9999, "TestOBJ", "baDTExt", "2024-01-01", 500, "A Test Thinggy", "Test", 500.99);
        });
        
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
    public void searchTest() throws SQLException, ClassNotFoundException{
        intPDAO();
        ArrayList<product> test = PDDAO.searchProdBy("ProductName", "Smart");
        for(product i: test) {
            System.out.println(i.getName());
        }
        assertTrue(test.size()>0);
    }

    @Test
    public void genIDTest() throws SQLException, ClassNotFoundException{
        intPDAO();
        assertFalse(PDDAO.checkpID(11111111)); // does not exist
        assertTrue(PDDAO.checkpID(1111111130)); // does exist
    }
}