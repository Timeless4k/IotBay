
// package Controller;

// import java.io.IOException; 
// // import java.util.List; 
// import javax.servlet.ServletException; 
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import model.Shipment; 
// import model.DAO.ShipmentDAO;

// import java.sql.Connection;
// // import java.sql.DriverManager;
// import java.sql.SQLException;  

// import javax.servlet.http.HttpSession;

// @WebServlet("/ShipmentServlet")
// public class ShipmentServlet extends HttpServlet {
//     private static final long serialVersionUID = 1L;

//     // private Connection connection;
//     private ShipmentDAO SDAO;

//     // @Override
//     // public void init() throws ServletException {
//     //     // Don't try to access session data, because it cannot reach the session's data if the servlet is initialised before any requests are processed
//     // } 

//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         String action = request.getParameter("action");
//         if (action != null) {
//             switch (action) {
//                 case "create":
//                     createShipment(request, response);
//                     break;
//                 case "update":
//                     updateShipment(request, response);
//                     break;
//                 case "delete":
//                     deleteShipment(request, response);
//                     break;
//                 default:
//                     // Handle unknown action
//                     break;
//             }
//         }
//     }

//     protected void doGet(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         String action = request.getParameter("action");
//         if (action != null) {
//             switch (action) {
//                 case "read":
//                     readShipment(request, response);
//                     break;
//                 default:
//                     // Handle unknown action
//                     break;
//             }
//         }
//     }


//     private void createShipment(HttpServletRequest request, HttpServletResponse response)
//         throws ServletException, IOException {
//         // Retrieve shipment details from request parameters
//         String shipmentAddress = request.getParameter("shipmentAddress");
//         String shipmentContactInfoEmail = request.getParameter("shipmentContactInfo_Email");
//         String shipmentContactInfoPhoneNumber = request.getParameter("shipmentContactInfo_PhoneNumber");
//         String shipmentDate = request.getParameter("shipmenttDate");
//         String shipmentMethod = request.getParameter("shipmentMethod");

//         // Retrieve the connection from the session
//         HttpSession session = request.getSession();
//         Connection connection = (Connection) session.getAttribute("acticonn");
 
//         // Check if the connection is not null
//         if (connection != null) {
//             try {
//                 // Create a new ShipmentDAO instance with the connection, for database operations
//                 SDAO = new ShipmentDAO(connection);

//                 // Create a Shipment object
//                 Shipment shipment = new Shipment(shipmentAddress, shipmentContactInfoEmail, shipmentContactInfoPhoneNumber, shipmentDate, shipmentMethod);

//                 // Call the DAO method to insert the shipment into the database
//                 SDAO.createShipment(shipment);

//                 // Redirect or forward to a success page
//                 response.sendRedirect("payment.jsp");
//             } catch (SQLException e) {
//                 // Handle database operation failure
//                 e.printStackTrace(); // Log the exception or handle it according to your application's error handling strategy
//                 // Redirect or forward to an error page 
//                 response.sendRedirect("error.jsp");
//             }
//         } else {
//             // If the connection is null, throw a ServletException
//             throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
//         }
//     }


//     private void readShipment(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {

//     }


//     private void updateShipment(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {

//     }


//     private void deleteShipment(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {

//     }

// }











package Controller;

import java.io.IOException; 
import javax.servlet.ServletException; 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.shipment; 
import model.DAO.shipmentDAO;

import java.sql.Connection;
import java.sql.SQLException;  

import javax.servlet.http.HttpSession;

@WebServlet("/ShipmentServlet")
public class ShipmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // private Connection connection;
    private shipmentDAO SDAO;

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
        // Retrieve shipment details from request parameters
        String shipmentAddress = request.getParameter("shipmentAddress");
        String shipmentDate = request.getParameter("shipmenttDate");
        String shipmentMethod = request.getParameter("shipmentMethod");

        // Retrieve the connection from the session
        HttpSession session = request.getSession();
        Connection connection = (Connection) session.getAttribute("acticonn");
 
        // Check if the connection is not null
        if (connection != null) {
            try {
                // Create a new ShipmentDAO instance with the connection, for database operations
                SDAO = new shipmentDAO(connection);

                // Create a Shipment object
                shipment shipment = new shipment(shipmentAddress, shipmentDate, shipmentMethod);

                // Call the DAO method to insert the shipment into the database
                SDAO.createShipment(shipment);

                connection.commit();

                // Redirect or forward to a success page
                response.sendRedirect("CardServlet?action=displayAll");
            } catch (SQLException e) {
                // Handle database operation failure
                e.printStackTrace(); // Log the exception or handle it according to your application's error handling strategy
                // Redirect or forward to an error page 
                response.sendRedirect("error.jsp");
            }
        } else {
            // If the connection is null, throw a ServletException
            throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
        }
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
