package model;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        

        // // Validating the user maybe? Need to be double checked
        // // You can't validate currently, because we are not storing the user data into a database, hence we can't check previous user's data
        // boolean isValidUser = validateUser(email, password);
        

        // // Cannot do the below currently, as there is no database to check the user's details against.
        // if (isValidUser) {
        //     // Success, send people to the welcome page
        //     HttpSession session = request.getSession();
        //     session.setAttribute("email", email);
        //     response.sendRedirect("welcome.jsp");
        // } else {
        //     // Failed
        //     response.sendRedirect("login.jsp?error=true");
        // }


        // Create a User instance and set its properties
        user user = new user();
        user.setEmail(email);
        user.setPassword(password);

        // Create a session to store user data
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Redirect user to welcome page 
        response.sendRedirect("welcome.jsp");
    }

    // // Cannot do the below currently, as there is no database to check the user's details against.
    // private boolean validateUser(String email, String password) {
    //     return "user@example.com".equals(email) && "password123".equals(password);
    // }
}