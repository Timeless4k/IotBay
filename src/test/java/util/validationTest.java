package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testIsValidEmail(){ //#T0013 
        assertTrue(ValidationUtils.isValidEmail("email@example.com"), "@ Email is valid.");
        assertFalse(ValidationUtils.isValidEmail("email@example"), "Email missing domain is invalid.");
        assertFalse(ValidationUtils.isValidEmail("email@.com"), "Email with incomplete domain is invalid.");
        assertFalse(ValidationUtils.isValidEmail(null), "Null email is invalid.");
    }

    @Test
    void testIsValidPassword(){ //#T0014 
        assertTrue(ValidationUtils.isValidPassword("Password@123"), "Password with upper, lower, digit, and special char is valid.");
        assertFalse(ValidationUtils.isValidPassword(null), "Null password is invalid.");
    }

    @Test
    void testIsValidName(){ //#T0015 
        assertTrue(ValidationUtils.isValidName("John Doe"), "namevalid.");
        assertFalse(ValidationUtils.isValidName("John@Doe"), "Name with special characters is invalid.");
        assertFalse(ValidationUtils.isValidName("1234"), "Name with digits is invalid.");
        assertFalse(ValidationUtils.isValidName(null), "Null name is invalid.");
    }

    @Test
    void testIsValidPhone(){ //#T0016 
        assertFalse(ValidationUtils.isValidPhone("12345"), "phone number is invalid.");
        assertFalse(ValidationUtils.isValidPhone("abcdefghijk"), "Phone number with letters is invalid.");
        assertFalse(ValidationUtils.isValidPhone(null), "Null phone number is invalid.");
    }
}
