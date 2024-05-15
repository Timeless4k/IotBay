
// Just testing DAOs

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import model.DAO.DBConnector;

import model.DAO.shipmentDAO;

import model.shipment;

import java.util.List;

public class ShipmentTest {

    public DBConnector connector;
    public Connection conn;
    public shipmentDAO SDAO;

    public void intSDAO() throws SQLException,ClassNotFoundException{
        if(SDAO == null) {
            connector = new DBConnector();
            conn = connector.openConnection();
            SDAO = new shipmentDAO(conn);
        }
    }

    @Test
    public void connTest() throws SQLException,ClassNotFoundException{
        intSDAO();
        assertNotNull(conn, "Connection does not exist");
    }

    // @Test
    // public void createTest() throws SQLException, ClassNotFoundException{
    //     intSDAO();

    //     Shipment test = new Shipment("University of Technology Sydney", "14/5/2024", "FedEx");

    //     boolean check = SDAO.createShipment(test);
    //     conn.commit();
    //     assertTrue(check);
    // }


    @Test
    public void createTest() throws SQLException, ClassNotFoundException {
        intSDAO();

        shipment test = new shipment("1982159379976824584", "University of Technology Sydney", "2024-05-25", "FedEx");

        System.out.println("Creating shipment: " + test.toString());

        boolean check = SDAO.createShipment(test);
        // conn.commit(); // pushing data to db

        System.out.println("Shipment creation status: " + check);

        assertTrue(check);
    }
 

    @Test
    public void readTest() throws SQLException, ClassNotFoundException {
        intSDAO();

        List<shipment> test = SDAO.readShipment("1111111113");

        boolean check = test.size() > 0;

        assertTrue(check);
    }


    @Test
    public void updateTest() throws SQLException, ClassNotFoundException {
        intSDAO();

        shipment test = SDAO.updateShipment("1982159379976824584", "Address Testing 1", "2024-05-26", "FedEx");

        assertNotNull(test);
    }


    @Test
    public void deleteTest() throws SQLException, ClassNotFoundException {
        intSDAO();

        List<shipment> testTwo = SDAO.readShipment("1111111113");

        int before = testTwo.size();

        shipment test = new shipment("1982159379976824584", "Address Testing 1", "2024-05-26", "FedEx");

        SDAO.createShipment(test);

        SDAO.deleteShipment("1982159379976824584");

        List<shipment> testThree = SDAO.readShipment("1111111113");

        int after = testThree.size();

        assertTrue(before == after);
    }

}
