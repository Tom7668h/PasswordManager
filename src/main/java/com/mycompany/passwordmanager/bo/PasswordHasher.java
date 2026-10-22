package com.mycompany.passwordmanager.bo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Enterprise Password Hashing and Key Derivation:
 * - Standard legacy hash (SHA-256, SHA-512)
 * - Salted PBKDF2WithHmacSHA256 (NIST/BSI recommendations)
 */
public class PasswordHasher {

    private static final Logger LOGGER = LogManager.getLogger(PasswordHasher.class);
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static final int DEFAULT_PBKDF2_ITERATIONS = 600000; // Modern OWASP/NIST recommendation
    public static final int DEFAULT_KEY_LENGTH = 256; // 256 bits

    /**
     * Legacy message digest hashing (SHA-256, SHA-512, etc.)
     */
    public static String hash(String password, String hashAlgorithm) {
        if (password == null || hashAlgorithm == null) {
            return null;
        }

        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
            final byte[] hashedPasswordBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            final StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Hash Algorithm '{}' is not available!", hashAlgorithm, ex);
        } catch (Throwable th) {
            LOGGER.error("Unexpected error during hashing", th);
        }
        return null;
    }

    /**
     * Generates a cryptographically secure 16-byte random salt.
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Derives a secure key/hash using PBKDF2WithHmacSHA256.
     * Output format: pbkdf2:[iterations]:[Base64-salt]:[Base64-hash]
     */
    public static String hashPBKDF2(char[] password, byte[] salt, int iterations, int keyLength) {
        try {
            KeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return "pbkdf2:" + iterations + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            LOGGER.error("PBKDF2 derivation failed", e);
            return null;
        }
    }

    /**
     * Verifies a password against a stored PBKDF2 hash string.
     */
    public static boolean verifyPBKDF2(char[] password, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("pbkdf2:")) {
            return false;
        }

        try {
            String[] parts = storedHash.split(":");
            if (parts.length != 4) {
                return false;
            }

            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[3]);

            KeySpec spec = new PBEKeySpec(password, salt, iterations, expectedHash.length * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] computedHash = factory.generateSecret(spec).getEncoded();

            return MessageDigest.isEqual(expectedHash, computedHash); // Constant-time comparison
        } catch (Exception e) {
            LOGGER.error("PBKDF2 verification failed", e);
            return false;
        }
    }
}
