package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtils {

    /**
     * Validates an email address.
     * 
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex) && email.length() <= 254;
    }

    /**
     * Validates a password.
     * 
     * @param password the password to validate
     * @return true if the password is not null, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return password != null;
    }

    /**
     * Validates a phone number.
     * 
     * @param phone the phone number to validate
     * @return true if the phone number matches the regex pattern, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        return phone != null && phone.matches(phoneRegex);
    }

    /**
     * Validates a gender.
     * 
     * @param gender the gender to validate
     * @return true if the gender is "Male", "Female", or "Other" (case insensitive), false otherwise
     */
    public static boolean isValidGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") ||
                                  gender.equalsIgnoreCase("Female") ||
                                  gender.equalsIgnoreCase("Other"));
    }

        /**
     * Validates a shipment address.
     * 
     * @param address the shipment address to validate
     * @return true if the address is valid, false otherwise
     */
    public static boolean isValidShipmentAddress(String address) {
        // Define a pattern for acceptable characters (letters, numbers, spaces, etc.)
        String addressRegex = "^[a-zA-Z0-9\\s,.-]+$";
        // Check if the shipment address contains characters outside the defined pattern
        return address != null && address.matches(addressRegex);
    }

    /**
     * Validates a card number.
     * 
     * @param cardNumber the card number to validate
     * @return true if the card number matches the regex pattern, false otherwise
     */
    public static boolean isValidCardNumber(String cardNumber) {
        String cardNumberRegex = "^[0-9]{13,19}$";
        return cardNumber != null && cardNumber.matches(cardNumberRegex);
    }

        /**
     * Validates a product name.
     * 
     * @param name the product name to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidProductName(String name) {
        // Define a pattern for acceptable characters (letters, numbers, spaces, etc.)
        String nameRegex = "^[a-zA-Z0-9\\s]+$";
        return name != null && name.matches(nameRegex);
    }

    /**
     * Validates a card holder name.
     * 
     * @param cardHolderName the card holder name to validate
     * @return true if the card holder name length is between 1 and 100 characters, false otherwise
     */
    public static boolean isValidCardHolderName(String cardHolderName) {
        return cardHolderName != null && cardHolderName.length() >= 1 && cardHolderName.length() <= 100;
    }

    /**
     * Validates a card expiry date.
     * 
     * @param cardExpiry the card expiry date to validate
     * @return true if the expiry date matches the regex pattern and is in the future, false otherwise
     */
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

    /**
     * Validates a card CVV.
     * 
     * @param cardCVV the card CVV to validate
     * @return true if the CVV matches the regex pattern, false otherwise
     */
    public static boolean isValidCardCVV(String cardCVV) {
        String cardCVVRegex = "^[0-9]{3,4}$";
        return cardCVV != null && cardCVV.matches(cardCVVRegex);
    }
}
