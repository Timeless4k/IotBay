package Controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.MessagingException;
import java.io.IOException;
import model.DAO.EmailSender;
import model.DAO.LoginDAO;

public class LoginServlet extends HttpServlet {
    
    private LoginDAO loginDao; // Add a LoginDAO member variable

    @Override
    public void init() {
        // Initialize LoginDAO here using the ServletContext or a similar method
        // This assumes your DAO needs just a database connection or similar setup
        ServletContext context = getServletContext();
        this.loginDao = (LoginDAO) context.getAttribute("loginDao"); // Assuming it's stored in context
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            if (loginDao.validate(email, password)) { // Assuming validate method checks authentication
                // Generate and send a verification code if authentication is successful
                int verificationCode = generateVerificationCode();
                EmailSender.sendEmail(email, "Login Verification", "Your login was successful. Here is your verification code: " + verificationCode);
                response.sendRedirect("welcome.jsp");
            } else {
                // Authentication failed, redirect to login failure page
                response.sendRedirect("loginFailure.jsp"); // Redirect to a proper login failure page
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to an error page if email sending fails
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // General error handling
        }
    }

    private int generateVerificationCode() {
        return (int) (Math.random() * 900000) + 100000; // Generates a random 6-digit code
    }
}
