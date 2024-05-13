package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testIsValidEmail() {
        assertTrue(ValidationUtils.isValidEmail("email@example.com"), "@ Email is valid.");
        assertFalse(ValidationUtils.isValidEmail("email@example"), "Email missing domain is invalid.");
        assertFalse(ValidationUtils.isValidEmail("email@.com"), "Email with incomplete domain is invalid.");
        assertFalse(ValidationUtils.isValidEmail(null), "Null email is invalid.");
    }

    @Test
    void testIsValidPassword() {
        assertTrue(ValidationUtils.isValidPassword("Password@123"), "Password with upper, lower, digit, and special char is valid.");
        assertFalse(ValidationUtils.isValidPassword("pass"), "Short password is invalid.");
        assertFalse(ValidationUtils.isValidPassword("password"), "Password without upper, digit, and special character is invalid.");
        assertFalse(ValidationUtils.isValidPassword(null), "Null password is invalid.");
    }

    @Test
    void testIsValidName() {
        assertTrue(ValidationUtils.isValidName("John Doe"), "Proper name is valid.");
        assertFalse(ValidationUtils.isValidName("John@Doe"), "Name with special characters is invalid.");
        assertFalse(ValidationUtils.isValidName("1234"), "Name with digits is invalid.");
        assertFalse(ValidationUtils.isValidName(null), "Null name is invalid.");
        assertTrue(ValidationUtils.isValidName("O'Neil"), "Name with apostrophe is valid.");
    }

    @Test
    void testIsValidPhone() {
        assertTrue(ValidationUtils.isValidPhone("+123456789012"), "Phone number in international format is valid.");
        assertFalse(ValidationUtils.isValidPhone("12345"), "Short phone number is invalid.");
        assertFalse(ValidationUtils.isValidPhone("abcdefghijk"), "Phone number with letters is invalid.");
        assertFalse(ValidationUtils.isValidPhone(null), "Null phone number is invalid.");
    }
}
