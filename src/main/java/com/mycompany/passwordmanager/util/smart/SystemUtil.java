package com.mycompany.passwordmanager.util.smart;

/**
 * System and Clipboard utilities with security auto-clearing.
 */
public class SystemUtil {

    public static final int DEFAULT_CLIPBOARD_AUTO_CLEAR_SECONDS = 30;

    /**
     * Copies content to the system clipboard and schedules an auto-clear
     * after 30 seconds to prevent exposure of sensitive credentials.
     */
    public static void copyToClipboard(String content) {
        SecurityUtil.copyToClipboardWithAutoClear(content, DEFAULT_CLIPBOARD_AUTO_CLEAR_SECONDS);
    }

    /**
     * Copies content with a custom auto-clear timeout in seconds.
     */
    public static void copyToClipboard(String content, int timeoutSeconds) {
        SecurityUtil.copyToClipboardWithAutoClear(content, timeoutSeconds);
    }
}
