/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.bo;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Tom7668h
 */
public class PasswordGenerator {

    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\\\",.<>?/";

    public static String generatePassword(int length, boolean useLowercase, boolean useUppercase, boolean useDigits, boolean useSpecialCharacters) {
        final String characterPattern = buildCharacterPattern(useLowercase, useUppercase, useDigits, useSpecialCharacters);
        if(characterPattern.length() <= 0) {
            return null;
        }
        final Random random = new SecureRandom();
        String password = "";
        for (int character = 0; character < length; character++) {
            final int randomIndex = random.nextInt(characterPattern.length());
            password += (characterPattern.charAt(randomIndex));
        }
        return password;
    }

    private static String buildCharacterPattern(boolean useLowercase, boolean useUppercase, boolean useDigits, boolean useSpecialCharacters) {
        final StringBuilder characters = new StringBuilder();
        if (useLowercase) {
            characters.append(LOWERCASE_CHARACTERS);
        }
        if (useUppercase) {
            characters.append(UPPERCASE_CHARACTERS);
        }
        if (useDigits) {
            characters.append(DIGITS);
        }
        if (useSpecialCharacters) {
            characters.append(SPECIAL_CHARACTERS);
        }
        return characters.toString();
    }
}
