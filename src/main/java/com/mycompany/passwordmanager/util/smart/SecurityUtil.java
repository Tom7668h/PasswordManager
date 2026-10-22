package com.mycompany.passwordmanager.util.smart;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Enterprise security utilities:
 * - Password Entropy and Strength Calculation
 * - In-Memory Sensitive Data Zeroization (char[] wiping)
 * - Auto-Clearing System Clipboard
 * - Cryptographically Secure Random Token Generation
 */
public class SecurityUtil {

    private static final Logger LOGGER = LogManager.getLogger(SecurityUtil.class);
    private static final ScheduledExecutorService CLIPBOARD_EXECUTOR = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "Clipboard-AutoClear-Thread");
        t.setDaemon(true);
        return t;
    });
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public enum StrengthLevel {
        VERY_WEAK, WEAK, MEDIUM, STRONG, VERY_STRONG
    }

    public static class PasswordStrength {
        private final int score; // 0 to 100
        private final double entropyBits;
        private final StrengthLevel level;
        private final List<String> feedback;

        public PasswordStrength(int score, double entropyBits, StrengthLevel level, List<String> feedback) {
            this.score = score;
            this.entropyBits = entropyBits;
            this.level = level;
            this.feedback = feedback;
        }

        public int getScore() {
            return score;
        }

        public double getEntropyBits() {
            return entropyBits;
        }

        public StrengthLevel getLevel() {
            return level;
        }

        public List<String> getFeedback() {
            return feedback;
        }

        public boolean isStrong() {
            return level == StrengthLevel.STRONG || level == StrengthLevel.VERY_STRONG;
        }
    }

    /**
     * Calculates Shannon entropy and password score with recommendations.
     */
    public static PasswordStrength calculateStrength(String password) {
        if (password == null || password.isEmpty()) {
            return new PasswordStrength(0, 0, StrengthLevel.VERY_WEAK, List.of("Passwort darf nicht leer sein."));
        }

        int poolSize = 0;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        if (hasLower) poolSize += 26;
        if (hasUpper) poolSize += 26;
        if (hasDigit) poolSize += 10;
        if (hasSpecial) poolSize += 33;

        double entropy = password.length() * (Math.log(poolSize > 0 ? poolSize : 1) / Math.log(2));
        List<String> feedback = new ArrayList<>();

        if (password.length() < 12) {
            feedback.add("Mindestens 12 Zeichen empfohlen.");
        }
        if (!hasLower) feedback.add("Kleinbuchstaben hinzufügen.");
        if (!hasUpper) feedback.add("Großbuchstaben hinzufügen.");
        if (!hasDigit) feedback.add("Ziffern hinzufügen.");
        if (!hasSpecial) feedback.add("Sonderzeichen hinzufügen.");

        // Calculate score (0-100)
        int score = 0;
        score += Math.min(password.length() * 4, 40); // up to 40 for length
        if (hasLower) score += 15;
        if (hasUpper) score += 15;
        if (hasDigit) score += 15;
        if (hasSpecial) score += 15;

        // Deductions for repeated patterns or short passwords
        if (password.length() < 8) score = Math.min(score, 25);
        if (password.length() < 6) score = Math.min(score, 10);

        StrengthLevel level;
        if (score >= 80 && entropy >= 60) {
            level = StrengthLevel.VERY_STRONG;
        } else if (score >= 60 && entropy >= 45) {
            level = StrengthLevel.STRONG;
        } else if (score >= 40 && entropy >= 30) {
            level = StrengthLevel.MEDIUM;
        } else if (score >= 20) {
            level = StrengthLevel.WEAK;
        } else {
            level = StrengthLevel.VERY_WEAK;
        }

        return new PasswordStrength(score, entropy, level, feedback);
    }

    /**
     * Securely wipes sensitive character arrays from memory (Zeroization).
     */
    public static void wipe(char[] sensitiveData) {
        if (sensitiveData != null) {
            Arrays.fill(sensitiveData, '\0');
        }
    }

    /**
     * Securely wipes sensitive byte arrays from memory.
     */
    public static void wipe(byte[] sensitiveData) {
        if (sensitiveData != null) {
            Arrays.fill(sensitiveData, (byte) 0);
        }
    }

    /**
     * Copies content to the system clipboard and securely clears it after a timeout.
     * Prevents leaving confidential passwords in the clipboard indefinitely.
     */
    public static void copyToClipboardWithAutoClear(String content, int timeoutSeconds) {
        if (content == null || content.isEmpty()) {
            return;
        }

        try {
            final StringSelection stringSelection = new StringSelection(content);
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
            LOGGER.info("Password copied to clipboard. Auto-clear scheduled in {} seconds.", timeoutSeconds);

            CLIPBOARD_EXECUTOR.schedule(() -> {
                try {
                    if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                        String current = (String) clipboard.getData(DataFlavor.stringFlavor);
                        if (content.equals(current)) {
                            clipboard.setContents(new StringSelection(""), null);
                            LOGGER.info("Clipboard auto-cleared securely.");
                        }
                    }
                } catch (Exception e) {
                    LOGGER.debug("Could not auto-clear clipboard: {}", e.getMessage());
                }
            }, timeoutSeconds, TimeUnit.SECONDS);

        } catch (Exception e) {
            LOGGER.error("Failed to copy to clipboard", e);
        }
    }

    /**
     * Generates a cryptographically secure random token (e.g. for master recovery keys).
     */
    public static String generateSecureToken(int byteLength) {
        byte[] bytes = new byte[byteLength];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
