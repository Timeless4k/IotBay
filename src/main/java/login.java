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
            // Validating the user maybe? Need to be double checked
        boolean isValidUser = validateUser(email, password);
        
        if (isValidUser) {
            // Success, send people to the welcome page
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            response.sendRedirect("welcome.jsp");
        } else {
            // Failed
            response.sendRedirect("login.jsp?error=true");
        }
    }

    private boolean validateUser(String email, String password) {
        return "user@example.com".equals(email) && "password123".equals(password);
    }
}