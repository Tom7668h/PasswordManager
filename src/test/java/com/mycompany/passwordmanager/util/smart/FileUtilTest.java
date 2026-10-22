package com.mycompany.passwordmanager.util.smart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FileUtil Atomic & Resilience Tests")
public class FileUtilTest {

    private Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        tempDir = Files.createTempDirectory("pm_fileutil_test");
    }

    @AfterEach
    public void tearDown() throws IOException {
        // cleanup temp files
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    @DisplayName("Atomic write and read roundtrip")
    public void testAtomicWriteAndRead() {
        String testPath = tempDir.resolve("test_vault.pmvault").toString();
        String content = "EncryptedVaultContentPayload12345";

        boolean written = FileUtil.writeFile(content, testPath);
        assertTrue(written, "File should be written atomically");
        assertTrue(FileUtil.existsFile(testPath));

        String read = FileUtil.readFile(testPath);
        assertEquals(content, read);
    }

    @Test
    @DisplayName("Should auto-create parent directories if missing")
    public void testAutoCreateParentDirectories() {
        String nestedPath = tempDir.resolve("deep").resolve("nested").resolve("dir").resolve("vault.pmvault").toString();
        boolean created = FileUtil.createFile(nestedPath);
        assertTrue(created);
        assertTrue(FileUtil.existsFile(nestedPath));
    }

    @Test
    @DisplayName("Should create automated backup copy (.bak)")
    public void testCreateBackup() {
        String testPath = tempDir.resolve("original.txt").toString();
        FileUtil.writeFile("InitialContent", testPath);

        boolean backedUp = FileUtil.createBackup(testPath);
        assertTrue(backedUp);

        String backupPath = testPath + ".bak";
        assertTrue(FileUtil.existsFile(backupPath));
        assertEquals("InitialContent", FileUtil.readFile(backupPath));
    }

    @Test
    @DisplayName("Null handling should be graceful without throwing")
    public void testNullSafety() {
        assertFalse(FileUtil.writeFile(null, null));
        assertNull(FileUtil.readFile(null));
        assertFalse(FileUtil.createBackup(null));
        assertFalse(FileUtil.createFile(null));
        assertFalse(FileUtil.deleteFile(null));
    }
}
