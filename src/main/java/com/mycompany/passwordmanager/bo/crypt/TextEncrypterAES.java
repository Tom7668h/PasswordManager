package com.mycompany.passwordmanager.bo.crypt;

import com.mycompany.passwordmanager.vo.config.OptionsSpecificSingelton;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Enterprise AES Encrypter:
 * - Default: AES-256-GCM (Authenticated Encryption with 12-byte IV and 128-bit Tag)
 * - Automatic fallback and backward compatibility for legacy AES/ECB entries.
 */
public class TextEncrypterAES implements TextEncrypter {

    private static final Logger LOGGER = LogManager.getLogger(TextEncrypterAES.class);
    private static final String GCM_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // 96 bits recommended for GCM
    private static final int GCM_TAG_LENGTH = 128; // 128-bit authentication tag
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String encrypt(String originalText, String key) {
        if (originalText == null || key == null) {
            return null;
        }

        try {
            SecretKeySpec secretKey = deriveKey(key);
            byte[] iv = new byte[GCM_IV_LENGTH];
            SECURE_RANDOM.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(GCM_TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

            byte[] cipherText = cipher.doFinal(originalText.getBytes(StandardCharsets.UTF_8));

            // Prefix with IV: [IV (12 bytes)] + [Ciphertext + Tag]
            byte[] combined = new byte[GCM_IV_LENGTH + cipherText.length];
            System.arraycopy(iv, 0, combined, 0, GCM_IV_LENGTH);
            System.arraycopy(cipherText, 0, combined, GCM_IV_LENGTH, cipherText.length);

            return "gcm:" + Base64.getEncoder().encodeToString(combined);
        } catch (Exception ex) {
            LOGGER.error("AES-GCM Encryption failed, falling back to standard cipher", ex);
            return encryptLegacy(originalText, key);
        }
    }

    @Override
    public String decrypt(String cryptedText, String key) {
        if (cryptedText == null || key == null) {
            return null;
        }

        // Modern AES-GCM format
        if (cryptedText.startsWith("gcm:")) {
            try {
                byte[] combined = Base64.getDecoder().decode(cryptedText.substring(4));
                if (combined.length < GCM_IV_LENGTH + 16) {
                    throw new IllegalArgumentException("Invalid GCM payload length.");
                }

                byte[] iv = new byte[GCM_IV_LENGTH];
                System.arraycopy(combined, 0, iv, 0, GCM_IV_LENGTH);

                byte[] cipherText = new byte[combined.length - GCM_IV_LENGTH];
                System.arraycopy(combined, GCM_IV_LENGTH, cipherText, 0, cipherText.length);

                SecretKeySpec secretKey = deriveKey(key);
                Cipher cipher = Cipher.getInstance(GCM_TRANSFORMATION);
                GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

                byte[] plainBytes = cipher.doFinal(cipherText);
                return new String(plainBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                LOGGER.error("Failed to decrypt AES-GCM payload", e);
                return null;
            }
        }

        // Legacy AES/ECB fallback
        return decryptLegacy(cryptedText, key);
    }

    private String encryptLegacy(String originalText, String key) {
        try {
            SecretKeySpec secretKeySpec = createLegacySecretKey(key);
            String encType = OptionsSpecificSingelton.getInstance().getEncryptionType() != null 
                    ? OptionsSpecificSingelton.getInstance().getEncryptionType().toString() 
                    : "AES";
            Cipher cipher = Cipher.getInstance(encType);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedText = Base64.getEncoder().encode(cipher.doFinal(originalText.getBytes(StandardCharsets.UTF_8)));
            return new String(encryptedText, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            LOGGER.error("Legacy AES Encryption error", ex);
            return null;
        }
    }

    private String decryptLegacy(String cryptedText, String key) {
        try {
            SecretKeySpec secretKeySpec = createLegacySecretKey(key);
            String encType = OptionsSpecificSingelton.getInstance().getEncryptionType() != null 
                    ? OptionsSpecificSingelton.getInstance().getEncryptionType().toString() 
                    : "AES";
            Cipher cipher = Cipher.getInstance(encType);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedText = cipher.doFinal(Base64.getDecoder().decode(cryptedText));
            return new String(decryptedText, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            LOGGER.error("Legacy AES Decryption error", ex);
            return null;
        }
    }

    private SecretKeySpec deriveKey(String key) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = sha256.digest(key.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 key derivation failed", e);
        }
    }

    private SecretKeySpec createLegacySecretKey(String key) {
        try {
            byte[] unhashedKeyAsByteArray = key.getBytes(StandardCharsets.UTF_8);
            String hashType = OptionsSpecificSingelton.getInstance().getHashType() != null 
                    ? OptionsSpecificSingelton.getInstance().getHashType().toString() 
                    : "SHA-256";
            MessageDigest md = MessageDigest.getInstance(hashType);
            byte[] hashedKeyAsByteArray = md.digest(unhashedKeyAsByteArray);
            if (hashType.contains("SHA")) {
                hashedKeyAsByteArray = Arrays.copyOf(hashedKeyAsByteArray, 16);
            }
            String encType = OptionsSpecificSingelton.getInstance().getEncryptionType() != null 
                    ? OptionsSpecificSingelton.getInstance().getEncryptionType().toString() 
                    : "AES";
            return new SecretKeySpec(hashedKeyAsByteArray, encType);
        } catch (Exception ex) {
            LOGGER.error("Failed to create legacy secret key", ex);
            return null;
        }
    }
}
