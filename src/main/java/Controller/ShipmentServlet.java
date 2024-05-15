
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
import util.ValidationUtils;

import java.sql.Connection;
import java.sql.SQLException;  

import javax.servlet.http.HttpSession;

import java.util.List;

import model.user;

import java.io.PrintWriter;

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
                    // serverInputValidation(request, response);
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
                case "readForCurrentOrder":
                    readForCurrentOrder(request, response);
                    break;
                default:
                    // Handle unknown action
                    break;
            }
        }
    }


    // // Function to check for weird symbols in the shipment address
    // private boolean containsWeirdSymbols(String shipmentAddress) {
    //     // Define a pattern for acceptable characters (letters, numbers, spaces, etc.)
    //     String regex = "^[a-zA-Z0-9\\s,.-]+$";
    //     // Check if the shipment address contains characters outside the defined pattern
    //     return !shipmentAddress.matches(regex);
    // }

    
    // private void serverInputValidation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //     // Retrieve the connection from the session
    //     HttpSession session = request.getSession();
    //     Connection connection = (Connection) session.getAttribute("acticonn");
    
    //     // Retrieve shipment details from request parameters
    //     String shipmentAddress = request.getParameter("shipmentAddress");
    //     String shipmentDate = request.getParameter("shipmenttDate");
    //     String shipmentMethod = request.getParameter("shipmentMethod");
    
    //     // Print statement before validation
    //     System.out.println("Before validation");
    
    //     if (connection != null) {
    //         try {
    //             SDAO = new shipmentDAO(connection);
    //             // Generate a unique shipment ID
    //             String shipmentID = SDAO.generateUniqueShipmentID();
    
    //             // Debugging statement for shipmentID
    //             System.out.println("shipmentID: " + shipmentID);
    
    //             // Debugging statement for shipmentAddress
    //             System.out.println("shipmentAddress: " + shipmentAddress);
    
    //             // Debugging statement for shipmentDate
    //             System.out.println("shipmentDate: " + shipmentDate);
    
    //             // Debugging statement for shipmentMethod
    //             System.out.println("shipmentMethod: " + shipmentMethod);
    
    //             // Check for weird symbols in the shipment address
    //             boolean containsWeirdSymbols = containsWeirdSymbols(shipmentAddress);
    
    //             // Debugging statement for containsWeirdSymbols method result
    //             System.out.println("containsWeirdSymbols: " + containsWeirdSymbols);
    
    //             // Validate shipment details
    //             if (shipmentID == null || shipmentID.isEmpty() ||
    //                 shipmentAddress == null || shipmentAddress.isEmpty() ||
    //                 shipmentDate == null || shipmentDate.isEmpty() ||
    //                 shipmentMethod == null || shipmentMethod.isEmpty() ||
    //                 containsWeirdSymbols) {
    //                 // Set an attribute to indicate the validation error
    //                 request.setAttribute("validationError", "Invalid shipment details");
    //                 // Forward the request back to the same page
    //                 request.getRequestDispatcher("shipmentConfirmation.jsp").forward(request, response);
    //                 return; // Exit the method
    //             } else {
    //                 // Print statement after validation
    //                 System.out.println("After validation");
    
    //                 // If validation passes, create the shipment
    //                 createShipment(request, response, shipmentID, shipmentAddress, shipmentDate, shipmentMethod);
    //             }
    //         } catch (SQLException e) {
    //             // Handle database operation failure
    //             e.printStackTrace(); // Log the exception or handle it according to your application's error handling strategy
    //             // Redirect or forward to an error page 
    //             response.sendRedirect("error.jsp");
    //         }
    //     } else {
    //         // If the connection is null, throw a ServletException
    //         throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
    //     }
    // }
    

   private void createShipment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String shipmentAddress = request.getParameter("shipmentAddress");
        String shipmentContactInfoEmail = request.getParameter("shipmentContactInfo_Email");
        String shipmentContactInfoPhoneNumber = request.getParameter("shipmentContactInfo_PhoneNumber");
        String shipmentDate = request.getParameter("shipmenttDate");
        String shipmentMethod = request.getParameter("shipmentMethod");

        if (!ValidationUtils.isValidShipmentAddress(shipmentAddress)) {
            request.setAttribute("validationError", "Invalid shipment address");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Connection connection = (Connection) session.getAttribute("acticonn");

        if (connection != null) {
            try {
                SDAO = new shipmentDAO(connection);
                shipment shipment = new shipment(shipmentAddress, shipmentContactInfoEmail, shipmentContactInfoPhoneNumber, shipmentDate);
                SDAO.createShipment(shipment);
                response.sendRedirect("shipmentConfirmation.jsp");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
        }
    }


    // private void readShipment(HttpServletRequest request, HttpServletResponse response)
    //     throws ServletException, IOException {
    //     // Retrieve the connection from the session
    //     HttpSession session = request.getSession();
    //     Connection connection = (Connection) session.getAttribute("acticonn");

    //     // Retrieve the user ID from the session
    //     // You have to get the session out of the request first, not straight from the request itself
    //     user user = (user) session.getAttribute("user");
    //     long userId = user.getuID();
    //     String userIdString = String.valueOf(userId);        
    //     // Print statement to check if user ID is retrieved correctly
    //     System.out.println("(READ SHIPMENT) User ID: " + userId);
        
    //     // Print statement to check if connection is retrieved correctly
    //     System.out.println("(READ SHIPMENT) Connection: " + connection);

    //     // Check if the connection is not null
    //     if (connection != null) {
    //         try {
    //             // Create a new ShipmentDAO instance with the connection, for database operations
    //             SDAO = new shipmentDAO(connection);
                
    //             // Print statement to indicate DAO instance creation
    //             System.out.println("(READ SHIPMENT) ShipmentDAO instance created.");

    //             // Call the DAO method to retrieve shipment data from the database based on the user ID
    //             List<shipment> shipments = SDAO.readShipment(userIdString);
                
    //             // Print statement to check if shipments are retrieved correctly
    //             System.out.println("(READ SHIPMENT) Number of shipments retrieved: " + shipments.size());

    //             // Set the shipments attribute in the request scope
    //             request.setAttribute("shipments", shipments);

    //             // Forward the request to the JSP to display the shipment data
    //             request.getRequestDispatcher("shipmentDetails.jsp").forward(request, response);
    //         } catch (SQLException e) {
    //             // Handle database operation failure
    //             e.printStackTrace(); // Log the exception or handle it according to your application's error handling strategy
    //             // Redirect or forward to an error page 
    //             response.sendRedirect("error.jsp");
    //         }
    //     } else {
    //         // If the connection is null, throw a ServletException
    //         throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
    //     }
    // }


    protected void readShipment(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Retrieve the connection from the session
        HttpSession session = request.getSession();
        Connection connection = (Connection) session.getAttribute("acticonn");

        // Retrieve the user ID from the session
        // You have to get the session out of the request first, not straight from the request itself
        user user = (user) session.getAttribute("user");
        long userId = user.getuID();
        String userIdString = String.valueOf(userId);        
        // Print statement to check if user ID is retrieved correctly
        System.out.println("(READ SHIPMENT) User ID: " + userId);
        
        // Print statement to check if connection is retrieved correctly
        System.out.println("(READ SHIPMENT) Connection: " + connection);

        // Check if the connection is not null
        if (connection != null) {
            try {
                // Create a new ShipmentDAO instance with the connection, for database operations
                SDAO = new shipmentDAO(connection);
                
                // Print statement to indicate DAO instance creation
                System.out.println("(READ SHIPMENT) ShipmentDAO instance created.");

                // Call the DAO method to retrieve shipment data from the database based on the user ID
                List<shipment> shipments = SDAO.readShipment(userIdString);
                
                // Print statement to check if shipments are retrieved correctly
                System.out.println("(READ SHIPMENT) Number of shipments retrieved: " + shipments.size());

                // Manually construct the JSON response
                StringBuilder json = new StringBuilder();
                json.append("[");
                for (int i = 0; i < shipments.size(); i++) {
                    shipment shipment = shipments.get(i);
                    json.append("{");
                    json.append("\"shipmentID\":").append(shipment.getShipmentID()).append(",");
                    json.append("\"shipmentAddress\":\"").append(shipment.getShipmentAddress()).append("\",");
                    json.append("\"shipmentDate\":\"").append(shipment.getShipmentDate()).append("\",");
                    json.append("\"shipmentMethod\":\"").append(shipment.getShipmentMethod()).append("\"");
                    json.append("}");
                    if (i < shipments.size() - 1) {
                        json.append(",");
                    }
                }
                json.append("]");

                // Set content type to indicate JSON response
                response.setContentType("application/json");
                
                // Get PrintWriter to write JSON response
                PrintWriter out = response.getWriter();
                
                // Write JSON response to PrintWriter
                out.println(json.toString());
                
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


    // select shipping id from orders, where user id matches


    // private void readShipment(HttpServletRequest request, HttpServletResponse response)
    //     throws ServletException, IOException {
    //     // Retrieve the connection from the session
    //     HttpSession session = request.getSession();
    //     Connection connection = (Connection) session.getAttribute("acticonn");

    //     // Retrieve the user ID from the session
    //     user user = (user) session.getAttribute("user");
    //     long userId = user.getuID();
    //     String userIdString = String.valueOf(userId);

    //     // Check if the connection is not null
    //     if (connection != null) {
    //         try {
    //             // Create a new ShipmentDAO instance with the connection, for database operations
    //             SDAO = new shipmentDAO(connection);

    //             // Call the DAO method to retrieve shipment data from the database based on the user ID
    //             List<shipment> shipments = SDAO.readShipment(userIdString);

    //             // Serialize the shipments list to JSON format
    //             String jsonShipments = new Gson().toJson(shipments);

    //             // Set content type of the response to indicate JSON data
    //             response.setContentType("application/json");

    //             // Write the JSON data to the response output stream
    //             response.getWriter().write(jsonShipments);
    //         } catch (SQLException e) {
    //             // Handle database operation failure
    //             e.printStackTrace(); // Log the exception or handle it according to your application's error handling strategy
    //             // Redirect or forward to an error page
    //             response.sendRedirect("error.jsp");
    //         }
    //     } else {
    //         // If the connection is null, throw a ServletException
    //         throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
    //     }
    // }


    protected void readForCurrentOrder(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection = (Connection) session.getAttribute("acticonn");

        // user user = (user) session.getAttribute("user");
        // long userId = user.getuID();
        // String userIdString = String.valueOf(userId);

        // Read current shipmentID
        String shipmentID = (String) session.getAttribute("currentShipmentID");
        System.out.println("(READ FOR CURRENT ORDER, SHIPMENT) Current Shipment ID: " + shipmentID);

        if (connection != null) {
            try {
                shipmentDAO SDAO = new shipmentDAO(connection);
                shipment shipment = SDAO.readForCurrentOrder(shipmentID);
                
                if (shipment != null) {
                    // Construct JSON response
                    StringBuilder json = new StringBuilder();
                    json.append("[");
                    json.append("{");
                    json.append("\"shipmentID\":").append(shipment.getShipmentID()).append(",");
                    json.append("\"shipmentAddress\":\"").append(shipment.getShipmentAddress()).append("\",");
                    json.append("\"shipmentDate\":\"").append(shipment.getShipmentDate()).append("\",");
                    json.append("\"shipmentMethod\":\"").append(shipment.getShipmentMethod()).append("\"");
                    json.append("}");
                    json.append("]");                    

                    // Set content type to indicate JSON response
                    response.setContentType("application/json");

                    // Get PrintWriter to write JSON response
                    PrintWriter out = response.getWriter();

                    // Write JSON response to PrintWriter
                    out.println(json.toString());
                } else {
                    // Shipment not found, handle accordingly
                    response.sendRedirect("error.jsp");
                }
            } catch (SQLException e) {
                // Handle exceptions
                e.printStackTrace(); // Log the exception or handle it according to your application's error handling strategy
                response.sendRedirect("error.jsp");
            }
        } else {
            throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
        }
    }


    // protected void readForCurrentOrder(HttpServletRequest request, HttpServletResponse response)
    //     throws ServletException, IOException {
    //     // Retrieve the connection from the session
    //     HttpSession session = request.getSession();
    //     Connection connection = (Connection) session.getAttribute("acticonn");

    //     // Retrieve the user ID from the session
    //     // You have to get the session out of the request first, not straight from the request itself
    //     user user = (user) session.getAttribute("user");
    //     long userId = user.getuID();
    //     String userIdString = String.valueOf(userId);        
    //     // Print statement to check if user ID is retrieved correctly
    //     System.out.println("(READ SHIPMENT) User ID: " + userId);
        
    //     // Print statement to check if connection is retrieved correctly
    //     System.out.println("(READ SHIPMENT) Connection: " + connection);

    //     // Check if the connection is not null
    //     if (connection != null) {
    //         try {
    //             // Create a new ShipmentDAO instance with the connection, for database operations
    //             SDAO = new shipmentDAO(connection);
                
    //             // Print statement to indicate DAO instance creation
    //             System.out.println("(READ SHIPMENT) ShipmentDAO instance created.");

    //             // Call the DAO method to retrieve shipment data from the database based on the user ID
    //             SDAO.readForCurrentOrder(userIdString);
                
    //             // Manually construct the JSON response
                // StringBuilder json = new StringBuilder();
                // json.append("[");
                // json.append("{");
                // json.append("\"shipmentID\":").append(shipment.getShipmentID()).append(",");
                // json.append("\"shipmentAddress\":\"").append(shipment.getShipmentAddress()).append("\",");
                // json.append("\"shipmentDate\":\"").append(shipment.getShipmentDate()).append("\",");
                // json.append("\"shipmentMethod\":\"").append(shipment.getShipmentMethod()).append("\"");
                // json.append("}");
                // json.append("]");

    //             // Set content type to indicate JSON response
    //             response.setContentType("application/json");
                
    //             // Get PrintWriter to write JSON response
    //             PrintWriter out = response.getWriter();
                
    //             // Write JSON response to PrintWriter
    //             out.println(json.toString());
                
    //         } catch (SQLException e) {
    //             // Handle database operation failure
    //             e.printStackTrace(); // Log the exception or handle it according to your application's error handling strategy
    //             // Redirect or forward to an error page 
    //             response.sendRedirect("error.jsp");
    //         }
    //     } else {
    //         // If the connection is null, throw a ServletException
    //         throw new ServletException("Connection attribute 'acticonn' is null. Check your session setup.");
    //     }
    // }


    // private void updateShipment(HttpServletRequest request, HttpServletResponse response)
    //         throws ServletException, IOException {

    // }

    private void updateShipment(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection = (Connection) session.getAttribute("acticonn");

        // Retrieve shipment details from the request parameters
        // String shipmentId = request.getParameter("shipmentId");

        String shipmentId = (String) session.getAttribute("currentShipmentID");
        System.out.println("(UPDATE SHIPMENT) Current Shipment ID: " + shipmentId);

        String shipmentAddress = request.getParameter("shipmentAddress");
        String shipmentDate = request.getParameter("shipmentDate");
        String shipmentMethod = request.getParameter("shipmentMethod");

        // Update shipment details in the database
        try {
            shipmentDAO SDAO = new shipmentDAO(connection);
            SDAO.updateShipment(shipmentId, shipmentAddress, shipmentDate, shipmentMethod);

            connection.commit();

            response.sendRedirect("payment.jsp");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            // Optionally, forward to an error page
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }


    // private void deleteShipment(HttpServletRequest request, HttpServletResponse response)
    //         throws ServletException, IOException {

    // }


    private void deleteShipment(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection connection = (Connection) session.getAttribute("acticonn");

        String shipmentId = (String) session.getAttribute("currentShipmentID");
        System.out.println("(DELETE SHIPMENT) Current Shipment ID: " + shipmentId);    
        
        // Update shipment details in the database
        try {
            shipmentDAO SDAO = new shipmentDAO(connection);
            SDAO.deleteShipment(shipmentId);

            connection.commit();

            response.sendRedirect("shipment.jsp");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            // Optionally, forward to an error page
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
