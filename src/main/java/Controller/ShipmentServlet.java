
// A Servlet is responsible for handling HTTP requests and generating HTTP responses in a Java web application.

// For example: When a user submits a form in a web application, the data typically goes to a Servlet. 
// The Servlet can then work with a DAO (Data Access Object) file to interact with the database, such as 
// storing the form data, retrieving information, updating records, or performing other database-related operations.

// When a user submits a form in a web application, the data is sent as an HTTP request to the server. 
// The servlet on the server-side then processes this HTTP request, extracts the form data, performs any 
// necessary actions (such as saving to a database or processing the data), and sends an HTTP response back to the client.



package Controller;

import java.io.IOException; // For handling IOExceptions that can occur during input/output operations
import java.util.List; // For working with lists and collections
import javax.servlet.ServletException; // For handling ServletExceptions that can occur during servlet processing
import javax.servlet.annotation.WebServlet; // For using the WebServlet annotation to specify servlet mappings
import javax.servlet.http.HttpServlet; // For extending HttpServlet, which provides methods for handling HTTP requests
import javax.servlet.http.HttpServletRequest; // For accessing HTTP-specific servlet request information
import javax.servlet.http.HttpServletResponse; // For sending HTTP-specific servlet response information back to the client

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


    // Used to handle HTTP POST requests. This type of request is often used when submitting forms or 
    // sending data to a server, such as when creating or updating resources.
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieves the value of the "action" parameter from the request.
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


    // Used to handle HTTP GET requests. This type of request is commonly used for retrieving data 
    // from a server, such as when reading or fetching resources.
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieves the value of the "action" parameter from the request.
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
        // Extract shipment details from request parameters
        // Validate and process the data
        // Save the shipment details using DAO
        // Redirect or forward to appropriate page
    }


    private void readShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the customer ID from the session or request parameters
        // Fetch the list of shipment details for the customer from DAO
        // Set the list as an attribute in request scope for JSP rendering
        // Forward to a JSP page to display the shipment details
    }


    private void updateShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract updated shipment details from request parameters
        // Validate and process the data
        // Update the shipment details using DAO
        // Redirect or forward to appropriate page
    }


    private void deleteShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract shipment ID from request parameters
        // Delete the shipment details using DAO
        // Redirect or forward to appropriate page
    }

}
