package model.DAO;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    private static final String USERNAME = System.getenv("iotbay"); // Environment variable for username
    private static final String PASSWORD = System.getenv("zvbq ktcu hevv mive"); // Environment variable for password

    public static void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        // Set properties for Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");    // Gmail SMTP Host
        props.put("mail.smtp.port", "587");               // TLS Port
        props.put("mail.smtp.auth", "true");              // Enable authentication
        props.put("mail.smtp.starttls.enable", "true");   // Enable STARTTLS

        // Create session with authenticator
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        // Prepare message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setText(body);

        // Send the message
        Transport.send(message);
        System.out.println("Email sent successfully!");
    }

    public static void main(String[] args) {
        try {
            sendEmail("recipient@example.com", "Test Mail", "Hello, this is a test email from IoTBay!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send the email.");
        }
    }
}
