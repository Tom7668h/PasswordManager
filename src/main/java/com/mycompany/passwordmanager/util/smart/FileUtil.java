package com.mycompany.passwordmanager.util.smart;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Enterprise File Utility:
 * - Atomic write operations (.tmp + ATOMIC_MOVE) preventing vault corruption
 * - Automatic parent directory creation
 * - Automatic vault backups (.bak)
 * - Modern Java NIO Files API
 */
public class FileUtil {

    private static final Logger LOGGER = LogManager.getLogger(FileUtil.class);

    public static boolean writeFile(String content, String path) {
        return writeFile(content, path, StandardCharsets.UTF_8);
    }

    /**
     * Atomically writes content to disk: writes to a temporary file first
     * and atomically replaces target. Prevents file corruption if interrupted.
     */
    public static boolean writeFile(String content, String path, Charset charset) {
        if (content == null || path == null) {
            return false;
        }

        try {
            Path targetPath = Paths.get(path);
            Path parentDir = targetPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            Path tempPath = Paths.get(path + ".tmp");
            Files.writeString(tempPath, content, charset);

            try {
                Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException atomicEx) {
                // Fallback if filesystem does not support ATOMIC_MOVE
                Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return true;
        } catch (IOException ex) {
            LOGGER.error("Failed to atomically write file: {}", path, ex);
            return false;
        }
    }

    public static String readFile(String path) {
        return readFile(path, StandardCharsets.UTF_8);
    }

    public static String readFile(String path, Charset charset) {
        if (path == null) {
            return null;
        }

        try {
            Path targetPath = Paths.get(path);
            if (!Files.exists(targetPath) || Files.isDirectory(targetPath)) {
                return null;
            }
            return Files.readString(targetPath, charset);
        } catch (Exception ex) {
            LOGGER.error("Failed to read file: {}", path, ex);
            return null;
        }
    }

    /**
     * Creates an automated backup of a file with .bak extension before modifying it.
     */
    public static boolean createBackup(String path) {
        if (path == null) {
            return false;
        }

        try {
            Path sourcePath = Paths.get(path);
            if (!Files.exists(sourcePath)) {
                return false;
            }

            Path backupPath = Paths.get(path + ".bak");
            Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Created automatic vault backup at: {}", backupPath);
            return true;
        } catch (IOException ex) {
            LOGGER.warn("Failed to create vault backup for: {}", path, ex);
            return false;
        }
    }

    public static boolean deleteFile(String path) {
        if (path == null) {
            return false;
        }

        try {
            Path targetPath = Paths.get(path);
            return Files.deleteIfExists(targetPath);
        } catch (IOException ex) {
            LOGGER.error("Failed to delete file: {}", path, ex);
            return false;
        }
    }

    public static boolean createFile(String path) {
        if (path == null) {
            return false;
        }

        try {
            Path targetPath = Paths.get(path);
            Path parentDir = targetPath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            if (Files.exists(targetPath)) {
                return true;
            }
            Files.createFile(targetPath);
            return true;
        } catch (IOException ex) {
            LOGGER.error("Failed to create file: {}", path, ex);
            return false;
        }
    }

    public static boolean existsFile(String path) {
        if (path == null) {
            return false;
        }
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }
}
