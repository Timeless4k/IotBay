package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.userDAO;
import model.user;


public class RegisterServlet extends HttpServlet {
    private Connection conn;
    private userDAO UDAO;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the DAO from the session or request
        HttpSession session = request.getSession();
        
        
        try {
            conn = (Connection) session.getAttribute("acticonn");
            UDAO = new userDAO(conn);
        } catch (SQLException ex) {
            System.err.println(ex);
        }


        // Retrieve form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String mobilePhone = request.getParameter("mobilePhone");
        String gender = request.getParameter("gender");

        // Create a User instance and set its properties
        user newUser = new user();
        newUser.setEmail(email);
        newUser.setPassword(password);  // Here you should hash the password before setting it
        newUser.setFirstName(firstName);
        newUser.setMiddleName(middleName);
        newUser.setLastName(lastName);
        newUser.setBirthDate(birthDate);
        newUser.setMobilePhone(mobilePhone);
        newUser.setGender(gender);
        newUser.setuType("Customer");  // Assuming new registrations are 'Customer' type by default
        newUser.setCreationDate(birthDate);  // Consider using the current date/time instead

        // Attempt to create user
        boolean createUserSuccess = UDAO.createUser(newUser);
        if (createUserSuccess) {
            session.setAttribute("user", newUser);
            response.sendRedirect("welcome.jsp");
        } else {
            response.sendRedirect("register.jsp?error=true");
        }
    }
}
