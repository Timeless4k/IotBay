
// Just testing DAOs

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import model.DAO.DBConnector;

import model.DAO.shipmentDAO;

import model.shipment;

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

}
