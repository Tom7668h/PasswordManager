package com.mycompany.passwordmanager.util.smart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SecurityUtil Entropy, Zeroization & Clipboard Tests")
public class SecurityUtilTest {

    @Test
    @DisplayName("Should detect weak and very weak passwords")
    public void testWeakPasswordDetection() {
        SecurityUtil.PasswordStrength weak = SecurityUtil.calculateStrength("123456");
        assertFalse(weak.isStrong());
        assertFalse(weak.getFeedback().isEmpty());

        SecurityUtil.PasswordStrength veryWeak = SecurityUtil.calculateStrength("123");
        assertEquals(SecurityUtil.StrengthLevel.VERY_WEAK, veryWeak.getLevel());
    }

    @Test
    @DisplayName("Should classify complex passphrases as strong or very strong")
    public void testStrongPasswordDetection() {
        SecurityUtil.PasswordStrength strong = SecurityUtil.calculateStrength("kL9#mP2$xQ7!vR4%zW8&");
        assertTrue(strong.isStrong());
        assertTrue(strong.getScore() >= 80);
        assertTrue(strong.getEntropyBits() > 60);
    }

    @Test
    @DisplayName("Zeroization must wipe sensitive character arrays")
    public void testMemoryZeroizationChars() {
        char[] sensitive = new char[]{'M', 'a', 's', 't', 'e', 'r', 'P', 'a', 's', 's'};
        SecurityUtil.wipe(sensitive);
        for (char c : sensitive) {
            assertEquals('\0', c, "Sensitive char must be zeroized in RAM");
        }
    }

    @Test
    @DisplayName("Zeroization must wipe sensitive byte arrays")
    public void testMemoryZeroizationBytes() {
        byte[] sensitive = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        SecurityUtil.wipe(sensitive);
        for (byte b : sensitive) {
            assertEquals((byte) 0, b, "Sensitive byte must be zeroized in RAM");
        }
    }

    @Test
    @DisplayName("Secure token generation must produce unique tokens of requested length")
    public void testSecureTokenGeneration() {
        String token1 = SecurityUtil.generateSecureToken(32);
        String token2 = SecurityUtil.generateSecureToken(32);
        assertNotNull(token1);
        assertNotNull(token2);
        assertNotEquals(token1, token2);
    }
}
