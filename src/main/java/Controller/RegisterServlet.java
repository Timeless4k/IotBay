package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.userDAO;
import model.user;
import util.ValidationUtils;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = (Connection) session.getAttribute("acticonn");

        if (conn == null) {
            System.err.println("Database connection not established");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error");
            return;
        }

        try {
            userDAO UDAO = new userDAO(conn);

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String mobilePhone = request.getParameter("mobilePhone");
            String gender = request.getParameter("gender");

            System.out.println(email);
            System.out.println(ValidationUtils.isValidEmail(email));
            System.out.println(password);
            System.out.println(ValidationUtils.isValidPassword(password));
            System.out.println(mobilePhone);
            System.out.println(ValidationUtils.isValidPhone(mobilePhone));
            System.out.println(gender);
            System.out.println(ValidationUtils.isValidGender(gender));

            if (!ValidationUtils.isValidEmail(email) || !ValidationUtils.isValidPassword(password) ||
                !ValidationUtils.isValidPhone(mobilePhone) || !ValidationUtils.isValidGender(gender)) {
                response.sendRedirect("register.jsp?error=validationFailed");
                return;
            }

            user existingUser = UDAO.getUserByEmail(email);
            if (existingUser != null) {
                response.sendRedirect("register.jsp?error=duplicate");
                return;
            }

            user newUser = new user();
            newUser.setEmail(email);
            newUser.setPassword(password); // Remember to hash password before storing
            newUser.setFirstName(firstName);
            newUser.setMiddleName(middleName);
            newUser.setLastName(lastName);
            newUser.setMobilePhone(mobilePhone);
            newUser.setGender(gender);
            newUser.setuType("Customer"); // Assuming default user type

            boolean createUserSuccess = UDAO.createUser(newUser);
            if (createUserSuccess) {
                session.setAttribute("user", newUser);
                response.sendRedirect("welcome.jsp");
            } else {
                response.sendRedirect("register.jsp?error=true");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating user");
        }
    }
}
