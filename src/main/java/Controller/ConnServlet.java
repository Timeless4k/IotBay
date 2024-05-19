package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.DBConnector;
public class ConnServlet extends HttpServlet {
    public DBConnector connector;
    public Connection conn;


    
    @Override
    public void init() {
        while(conn == null) {
            try {
                connector = new DBConnector();
                conn = connector.openConnection();
                System.out.println("establish connection");
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println(ex);
            }
        }
        
    }

    /**
        * Handles HTTP GET requests.
        *
        * @param request  the HttpServletRequest object representing the client's request
        * @param response the HttpServletResponse object representing the server's response
        * @throws IOException      if an I/O error occurs while processing the request
        * @throws ServletException if a servlet-specific error occurs while processing the request
        */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		session.setAttribute("acticonn", conn);
        System.out.println("Pass connection to session");
    }

    @Override
	public void destroy() {
		try {
			connector.closeConnection();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
