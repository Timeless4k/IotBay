package Controller;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.MessagingException;
import java.io.IOException;
import model.DAO.EmailSender;
import model.LoginDAO;

public class LoginServlet extends HttpServlet {

    private LoginDAO loginDao = new LoginDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (loginDao.authenticateUser(email, password)) {
            try {
                // Generate and send a verification code if authentication is successful
                int verificationCode = generateVerificationCode();
                EmailSender.sendEmail(email, "Login Verification", "Your login was successful. Here is your verification code: " + verificationCode);
                response.sendRedirect("loginSuccess.jsp");
            } catch (MessagingException e) {
                e.printStackTrace();
                response.sendRedirect("emailError.jsp"); // Redirect to an error page if email sending fails
            }
        } else {
            // Authentication failed, redirect to login failure page
            response.sendRedirect("loginFailed.jsp");
        }
    }

    private int generateVerificationCode() {
        return (int) (Math.random() * 900000) + 100000; // Generates a random 6-digit code
    }
}
