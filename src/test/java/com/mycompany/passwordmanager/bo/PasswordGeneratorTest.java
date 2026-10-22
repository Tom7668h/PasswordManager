package com.mycompany.passwordmanager.bo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PasswordGenerator Security & Criteria Tests")
public class PasswordGeneratorTest {

    @ParameterizedTest
    @ValueSource(ints = {8, 12, 16, 24, 32, 64})
    @DisplayName("Should generate passwords of exact requested length")
    public void testPasswordLengths(int length) {
        String pwd = PasswordGenerator.generatePassword(length, true, true, true, true);
        assertNotNull(pwd);
        assertEquals(length, pwd.length());
    }

    @Test
    @DisplayName("Should include all requested character categories")
    public void testCharacterCategoriesIncluded() {
        // Run multiple times to verify consistency
        for (int i = 0; i < 20; i++) {
            String pwd = PasswordGenerator.generatePassword(16, true, true, true, true);
            boolean hasLower = pwd.chars().anyMatch(Character::isLowerCase);
            boolean hasUpper = pwd.chars().anyMatch(Character::isUpperCase);
            boolean hasDigit = pwd.chars().anyMatch(Character::isDigit);
            boolean hasSpecial = pwd.chars().anyMatch(c -> "!@#$%^&*()-_=+[]{}|;:,.<>?".indexOf(c) >= 0);

            assertTrue(hasLower, "Should contain lowercase character: " + pwd);
            assertTrue(hasUpper, "Should contain uppercase character: " + pwd);
            assertTrue(hasDigit, "Should contain numeric digit: " + pwd);
            assertTrue(hasSpecial, "Should contain special character: " + pwd);
        }
    }

    @Test
    @DisplayName("Digits-only password generation")
    public void testDigitsOnly() {
        String pin = PasswordGenerator.generatePassword(6, false, false, true, false);
        assertEquals(6, pin.length());
        assertTrue(pin.chars().allMatch(Character::isDigit));
    }

    @Test
    @DisplayName("Empty or zero length should return empty string")
    public void testZeroLength() {
        assertEquals("", PasswordGenerator.generatePassword(0, true, true, true, true));
        assertEquals("", PasswordGenerator.generatePassword(-5, true, true, true, true));
    }
}
