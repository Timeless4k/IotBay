
package Controller;

import java.io.IOException; 
import java.util.List; 
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.shipmentDAO;

@WebServlet("/ShipmentServlet")
public class ShipmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DAO instance for database operations
    private shipmentDAO shipmentDAO;

    public void init() {
        // Initialize your DAO instance
        shipmentDAO = new shipmentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "create":
                    createShipment(request, response);
                    break;
                case "update":
                    updateShipment(request, response);
                    break;
                case "delete":
                    deleteShipment(request, response);
                    break;
                default:
                    // Handle unknown action
                    break;
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "read":
                    readShipment(request, response);
                    break;
                default:
                    // Handle unknown action
                    break;
            }
        }
    }

    private void createShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    private void readShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    private void updateShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    private void deleteShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
