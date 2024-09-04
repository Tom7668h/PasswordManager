/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.bo.crypt;

import com.mycompany.passwordmanager.vo.config.OptionsSpecificSingelton;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Tom7668h
 */
public class TextEncrypterAES implements TextEncrypter {

    // INFO: Folgende Hash Algorithmen sind möglich: SHA-1, SHA-224, SHA-256, SHA-384, SHA-512, MD2, MD5
    private final String ENCODING = "UTF-8";

    public String encrypt(String originalText, String key) {
        try {
            SecretKeySpec secretKeySpec = createSecretKey(key);
            Cipher cipher = Cipher.getInstance(OptionsSpecificSingelton.getInstance().getEncryptionType().toString());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedText = Base64.getEncoder().encode(cipher.doFinal(originalText.getBytes()));
            return new String(encryptedText);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException ex) {
            Logger.getLogger(TextEncrypterAES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String decrypt(String cryptedText, String key) {
        try {
            SecretKeySpec secretKeySpec = createSecretKey(key);
            Cipher cipher = Cipher.getInstance(OptionsSpecificSingelton.getInstance().getEncryptionType().toString());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedText = cipher.doFinal(Base64.getDecoder().decode(cryptedText));
            return new String(decryptedText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(TextEncrypterAES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private SecretKeySpec createSecretKey(String key) {
        try {
            byte[] unhashedKeyAsByteArray = (key).getBytes(ENCODING);
            MessageDigest md = MessageDigest.getInstance(OptionsSpecificSingelton.getInstance().getHashType().toString());
            byte[] hashedKeyAsByteArray = md.digest(unhashedKeyAsByteArray);
            // INFO: Byte Array auf 16 Bytes kürzen beu SHA-* Algorithmen NICHT bei MD* Algorithmen!
            if (OptionsSpecificSingelton.getInstance().getHashType().toString().contains("SHA")) {
                hashedKeyAsByteArray = Arrays.copyOf(hashedKeyAsByteArray, 16);
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(hashedKeyAsByteArray, OptionsSpecificSingelton.getInstance().getEncryptionType().toString());
            return secretKeySpec;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(TextEncrypterAES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
