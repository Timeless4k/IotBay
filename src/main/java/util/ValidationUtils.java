package util;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex) && email.length() <= 254;
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$";
        return password != null && password.matches(passwordRegex);
    }

    public static boolean isValidName(String name) {
        String nameRegex = "^[a-zA-Z\\s'-]+$";
        return name != null && name.matches(nameRegex);
    }

    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^\\+?\\d{10,15}$"; // International format, up to 15 digits
        return phone != null && phone.matches(phoneRegex);
    }

    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") ||
                                  gender.equalsIgnoreCase("Female") ||
                                  gender.equalsIgnoreCase("Other"));
    }
}
