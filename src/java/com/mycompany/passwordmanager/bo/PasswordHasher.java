/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.bo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Tom
 */
public class PasswordHasher {

    public static String hash(String password, String hashAlgorithem) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithem);
            final byte[] hashedPasswordBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < hashedPasswordBytes.length; i++) {
                stringBuilder.append(Integer.toString((hashedPasswordBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            final String hashedPassword = stringBuilder.toString();
            return hashedPassword;
        } catch (NoSuchAlgorithmException ex) {
            LogManager.getLogger(PasswordHasher.class).error("Hash Alogorithem is not available!", ex);
            
        } catch(Throwable th) {
            LogManager.getLogger(PasswordHasher.class).error("Password is null!", th);
        }
        return null;
    }
}
