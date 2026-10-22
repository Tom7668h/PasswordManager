package com.mycompany.passwordmanager.bo.crypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AES-256-GCM & Legacy Cryptographic Tests")
public class TextEncrypterAESTest {

    private TextEncrypterAES encrypter;
    private final String SECRET_KEY = "MasterSecretKey2026!";
    private final String SAMPLE_TEXT = "SuperSecretPassword#1234_SensitiveData";

    @BeforeEach
    public void setUp() {
        encrypter = new TextEncrypterAES();
    }

    @Test
    @DisplayName("Should successfully encrypt and decrypt text using AES-256-GCM")
    public void testEncryptAndDecryptGCM() {
        String encrypted = encrypter.encrypt(SAMPLE_TEXT, SECRET_KEY);
        assertNotNull(encrypted);
        assertTrue(encrypted.startsWith("gcm:"), "Encrypted payload should carry gcm: prefix");

        String decrypted = encrypter.decrypt(encrypted, SECRET_KEY);
        assertEquals(SAMPLE_TEXT, decrypted);
    }

    @Test
    @DisplayName("Two encryptions of the same text must produce different ciphertexts (Random IV)")
    public void testRandomIVIndistinguishability() {
        String enc1 = encrypter.encrypt(SAMPLE_TEXT, SECRET_KEY);
        String enc2 = encrypter.encrypt(SAMPLE_TEXT, SECRET_KEY);

        assertNotNull(enc1);
        assertNotNull(enc2);
        assertNotEquals(enc1, enc2, "Each encryption must use a fresh random IV");

        assertEquals(SAMPLE_TEXT, encrypter.decrypt(enc1, SECRET_KEY));
        assertEquals(SAMPLE_TEXT, encrypter.decrypt(enc2, SECRET_KEY));
    }

    @Test
    @DisplayName("Decryption with wrong key must fail safely")
    public void testDecryptionWithWrongKey() {
        String encrypted = encrypter.encrypt(SAMPLE_TEXT, SECRET_KEY);
        String decrypted = encrypter.decrypt(encrypted, "WrongMasterKey999!");

        assertNull(decrypted, "Decryption with incorrect key should safely return null");
    }

    @Test
    @DisplayName("Tampered ciphertext must fail authentication (AEAD integrity protection)")
    public void testTamperDetection() {
        String encrypted = encrypter.encrypt(SAMPLE_TEXT, SECRET_KEY);
        // Tamper with payload
        char lastChar = encrypted.charAt(encrypted.length() - 1);
        char alteredChar = (lastChar == 'A') ? 'B' : 'A';
        String tampered = encrypted.substring(0, encrypted.length() - 1) + alteredChar;

        String decrypted = encrypter.decrypt(tampered, SECRET_KEY);
        assertNull(decrypted, "Altered ciphertext must fail GCM tag validation");
    }

    @Test
    @DisplayName("Null handling should be graceful")
    public void testNullSafety() {
        assertNull(encrypter.encrypt(null, SECRET_KEY));
        assertNull(encrypter.encrypt(SAMPLE_TEXT, null));
        assertNull(encrypter.decrypt(null, SECRET_KEY));
        assertNull(encrypter.decrypt("gcm:invalid", null));
    }
}
