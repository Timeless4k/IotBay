package model;
import java.sql.*;

public class LoginDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/iotbaydb"; // Database URL
    private String jdbcUsername = "root"; // Database username
    private String jdbcPassword = "password"; // Database password

    private static final String LOGIN_QUERY = "SELECT email, password_hash FROM users WHERE email = ?";

    public LoginDAO() {

    }

    public boolean authenticateUser(String email, String password) {
        // Connection and PreparedStatement are AutoCloseable, use try-with-resources to ensure closure
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {
            
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password_hash");
                return checkPassword(password, storedHash); // Assuming checkPassword method is implemented to verify hashed passwords
            }
        } catch (SQLException e) {
            System.out.println("Database error during user authentication");
            e.printStackTrace();
        }
        return false; // User not found or db error, authentication failed
    }

    private boolean checkPassword(String password, String storedHash) {
        // Implement password hash verification logic here
        // Example: return BCrypt.checkpw(password, storedHash);
        return true; // Temporary placeholder for demonstration
    }
}
