package com.mycompany.passwordmanager.bo;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cryptographically Secure Password Generator
 */
public class PasswordGenerator {

    public static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generatePassword(int length, boolean useLowercase, boolean useUppercase, boolean useDigits, boolean useSpecialCharacters) {
        if (length <= 0) {
            return "";
        }

        final List<Character> passwordChars = new ArrayList<>(length);
        final StringBuilder pool = new StringBuilder();

        // Ensure at least one character from each selected category
        if (useLowercase) {
            pool.append(LOWERCASE_CHARACTERS);
            passwordChars.add(LOWERCASE_CHARACTERS.charAt(SECURE_RANDOM.nextInt(LOWERCASE_CHARACTERS.length())));
        }
        if (useUppercase) {
            pool.append(UPPERCASE_CHARACTERS);
            passwordChars.add(UPPERCASE_CHARACTERS.charAt(SECURE_RANDOM.nextInt(UPPERCASE_CHARACTERS.length())));
        }
        if (useDigits) {
            pool.append(DIGITS);
            passwordChars.add(DIGITS.charAt(SECURE_RANDOM.nextInt(DIGITS.length())));
        }
        if (useSpecialCharacters) {
            pool.append(SPECIAL_CHARACTERS);
            passwordChars.add(SPECIAL_CHARACTERS.charAt(SECURE_RANDOM.nextInt(SPECIAL_CHARACTERS.length())));
        }

        if (pool.length() == 0) {
            return "";
        }

        // Fill remaining length
        while (passwordChars.size() < length) {
            passwordChars.add(pool.charAt(SECURE_RANDOM.nextInt(pool.length())));
        }

        // Shuffle so required characters aren't in predictable positions
        Collections.shuffle(passwordChars, SECURE_RANDOM);

        final StringBuilder result = new StringBuilder(length);
        for (char c : passwordChars) {
            result.append(c);
        }

        return result.toString();
    }
}
