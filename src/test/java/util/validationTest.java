package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testIsValidEmail() { //#T0013 
        assertTrue(ValidationUtils.isValidEmail("email@example.com"), "@ Email is valid.");
        assertFalse(ValidationUtils.isValidEmail("email@example"), "Email missing domain is invalid.");
        assertFalse(ValidationUtils.isValidEmail("email@.com"), "Email with incomplete domain is invalid.");
        assertFalse(ValidationUtils.isValidEmail(null), "Null email is invalid.");
    }

    @Test
    void testIsValidPassword() { //#T0014 
        assertTrue(ValidationUtils.isValidPassword("Password@123"), "Password with upper, lower, digit, and special char is valid.");
        assertFalse(ValidationUtils.isValidPassword(null), "Null password is invalid.");
    }

    @Test
    void testIsValidPhone() { //#T0016 
        assertFalse(ValidationUtils.isValidPhone("12345"), "phone number is invalid.");
        assertFalse(ValidationUtils.isValidPhone("abcdefghijk"), "Phone number with letters is invalid.");
        assertFalse(ValidationUtils.isValidPhone(null), "Null phone number is invalid.");
    }

    @Test
    void testIsValidGender() { //#T0017
        assertTrue(ValidationUtils.isValidGender("Male"), "Gender 'Male' is valid.");
        assertTrue(ValidationUtils.isValidGender("Female"), "Gender 'Female' is valid.");
        assertTrue(ValidationUtils.isValidGender("Other"), "Gender 'Other' is valid.");
        assertTrue(ValidationUtils.isValidGender("male"), "Gender 'male' (case insensitive) is valid.");
        assertFalse(ValidationUtils.isValidGender("Unknown"), "Gender 'Unknown' is invalid.");
        assertFalse(ValidationUtils.isValidGender(null), "Null gender is invalid.");
    }

    @Test
    void testIsValidCardNumber() { //#T0018
        assertTrue(ValidationUtils.isValidCardNumber("1234567890123"), "13 digit card number is valid.");
        assertTrue(ValidationUtils.isValidCardNumber("1234567890123456789"), "19 digit card number is valid.");
        assertFalse(ValidationUtils.isValidCardNumber("123456789012"), "12 digit card number is invalid.");
        assertFalse(ValidationUtils.isValidCardNumber("12345678901234567890"), "20 digit card number is invalid.");
        assertFalse(ValidationUtils.isValidCardNumber("abcdefghijk"), "Card number with letters is invalid.");
        assertFalse(ValidationUtils.isValidCardNumber(null), "Null card number is invalid.");
    }

    @Test
    void testIsValidCardHolderName() { //#T0019
        assertTrue(ValidationUtils.isValidCardHolderName("John Doe"), "Card holder name 'John Doe' is valid.");
        assertTrue(ValidationUtils.isValidCardHolderName("A"), "Single character card holder name is valid.");
        assertFalse(ValidationUtils.isValidCardHolderName(""), "Empty card holder name is invalid.");
        assertFalse(ValidationUtils.isValidCardHolderName(null), "Null card holder name is invalid.");
        
        StringBuilder longNameBuilder = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            longNameBuilder.append("A");
        }
        String longName = longNameBuilder.toString();
        assertFalse(ValidationUtils.isValidCardHolderName(longName), "Card holder name longer than 100 characters is invalid.");
    }

    @Test
    void testIsValidCardExpiry() { //#T0020
        assertTrue(ValidationUtils.isValidCardExpiry("12/2024"), "Valid future expiry date '12/2024' is valid.");
        assertFalse(ValidationUtils.isValidCardExpiry("13/2024"), "Invalid month '13' in expiry date is invalid.");
        assertFalse(ValidationUtils.isValidCardExpiry("00/2024"), "Invalid month '00' in expiry date is invalid.");
        assertFalse(ValidationUtils.isValidCardExpiry("12/2020"), "Past expiry date '12/2020' is invalid.");
        assertFalse(ValidationUtils.isValidCardExpiry("12/20"), "Short year format '12/20' is invalid.");
        assertFalse(ValidationUtils.isValidCardExpiry("122024"), "Missing '/' in expiry date '122024' is invalid.");
        assertFalse(ValidationUtils.isValidCardExpiry(null), "Null expiry date is invalid.");
    }

    @Test
    void testIsValidCardCVV() { //#T0021
        assertTrue(ValidationUtils.isValidCardCVV("123"), "3 digit CVV '123' is valid.");
        assertTrue(ValidationUtils.isValidCardCVV("1234"), "4 digit CVV '1234' is valid.");
        assertFalse(ValidationUtils.isValidCardCVV("12"), "2 digit CVV '12' is invalid.");
        assertFalse(ValidationUtils.isValidCardCVV("12345"), "5 digit CVV '12345' is invalid.");
        assertFalse(ValidationUtils.isValidCardCVV("abc"), "CVV with letters 'abc' is invalid.");
        assertFalse(ValidationUtils.isValidCardCVV(null), "Null CVV is invalid.");
    }
}
