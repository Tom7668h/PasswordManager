package com.mycompany.passwordmanager.bo;

import com.mycompany.passwordmanager.vo.types.HashType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PasswordHasher & PBKDF2 Key Derivation Tests")
public class PasswordHasherTest {

    private final String PASSWORD = "MyTestPassword";

    @Test
    @DisplayName("Should hash with SHA-512")
    public void testHashSha512() {
        String result = PasswordHasher.hash(PASSWORD, HashType.SHA512.toString());
        assertNotNull(result);
        assertEquals(128, result.length(), "SHA-512 hex string should be 128 chars");
        assertEquals("6c1e1bd5f8d2d053d3cb4152a3a6464e977039f824500b5d60735fcb59dc1f8056f267e99214d7008ecc45e910d4ee0e729bfe793cee4a772b6c6781e355c175", result);
    }

    @Test
    @DisplayName("Should hash with SHA-256")
    public void testHashSha256() {
        String result = PasswordHasher.hash(PASSWORD, HashType.SHA256.toString());
        assertNotNull(result);
        assertEquals(64, result.length(), "SHA-256 hex string should be 64 chars");
        assertEquals("992943d895cd42e946b14f2504df478898a5b466dc614ea5b542031058d1fa10", result);
    }

    @Test
    @DisplayName("Should hash with MD5")
    public void testHashMd5() {
        String result = PasswordHasher.hash(PASSWORD, HashType.MD5.toString());
        assertNotNull(result);
        assertEquals("72418435a5ebb5949ccaa42f55bc15f3", result);
    }

    @Test
    @DisplayName("Should generate salt and derive PBKDF2 hash")
    public void testPBKDF2Hashing() {
        byte[] salt = PasswordHasher.generateSalt();
        assertNotNull(salt);
        assertEquals(16, salt.length);

        char[] passChars = PASSWORD.toCharArray();
        String hashStr = PasswordHasher.hashPBKDF2(passChars, salt, 10000, 256);

        assertNotNull(hashStr);
        assertTrue(hashStr.startsWith("pbkdf2:10000:"));

        // Verify correct password
        assertTrue(PasswordHasher.verifyPBKDF2(passChars, hashStr));

        // Verify incorrect password
        assertFalse(PasswordHasher.verifyPBKDF2("WrongPassword!".toCharArray(), hashStr));
    }

    @Test
    @DisplayName("Null handling in hashing")
    public void testNullHandling() {
        assertNull(PasswordHasher.hash(null, "SHA-256"));
        assertNull(PasswordHasher.hash(PASSWORD, "INVALID_ALGO_XYZ"));
    }
}
