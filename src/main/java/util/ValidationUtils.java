package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex) && email.length() <= 254;
    }

    public static boolean isValidPassword(String password) {
        return password != null;
    }

    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        return phone != null && phone.matches(phoneRegex);
    }

    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") ||
                                  gender.equalsIgnoreCase("Female") ||
                                  gender.equalsIgnoreCase("Other"));
    }

    public static boolean isValidCardNumber(String cardNumber) {
        String cardNumberRegex = "^[0-9]{13,19}$";
        return cardNumber != null && cardNumber.matches(cardNumberRegex);
    }

    public static boolean isValidCardHolderName(String cardHolderName) {
        return cardHolderName != null && cardHolderName.length() >= 1 && cardHolderName.length() <= 100;
    }

    public static boolean isValidCardExpiry(String cardExpiry) {
        String cardExpiryRegex = "^(0[1-9]|1[0-2])/([0-9]{4})$";
        if (cardExpiry == null || !cardExpiry.matches(cardExpiryRegex)) {
            return false;
        }

        // Check if the expiry date is in the future
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        sdf.setLenient(false);
        try {
            Date expiryDate = sdf.parse(cardExpiry);
            return expiryDate.after(new Date());
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidCardCVV(String cardCVV) {
        String cardCVVRegex = "^[0-9]{3,4}$";
        return cardCVV != null && cardCVV.matches(cardCVVRegex);
    }
}
