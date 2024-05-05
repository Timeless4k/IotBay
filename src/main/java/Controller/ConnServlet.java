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
        try {
            connector = new DBConnector();
            conn = connector.openConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		session.setAttribute("acticonn", conn);
		request.getRequestDispatcher("index.jsp").include(request, response);
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
