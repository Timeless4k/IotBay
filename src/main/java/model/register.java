package model;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class register extends HttpServlet {
    
    // This line defines a method named doPost that handles HTTP POST requests in a servlet
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            // Retrieve form data
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String birthDate = request.getParameter("birthDate");
            String mobilePhone = request.getParameter("mobilePhone");           
            
            // Create a User instance and set its properties
            user user = new user();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setMiddleName(middleName);
            user.setLastName(lastName);
            user.setBirthDate(birthDate);
            user.setMobilePhone(mobilePhone);

            // Create a session to store user data
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect user to welcome page 
            response.sendRedirect("welcome.jsp");

        } catch (ServletException e) {
            e.printStackTrace(); // Log ServletException
            // Handle ServletException
        } catch (IOException e) {
            e.printStackTrace(); // Log IOException
            // Handle IOException
        }
    }
}