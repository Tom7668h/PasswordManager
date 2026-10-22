package com.mycompany.passwordmanager.bo;

import com.mycompany.passwordmanager.vo.holder.PasswordEntry;
import com.mycompany.passwordmanager.vo.holder.PasswordManagerWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProcessManager Vault Lifecycle & Security Tests")
public class ProcessManagerTest {

    private Path tempDir;
    private ProcessManager processManager;
    private final String MASTER_PASS = "VaultMaster2026!#Key";

    @BeforeEach
    public void setUp() throws IOException {
        tempDir = Files.createTempDirectory("pm_process_test");
        processManager = new ProcessManager();
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    @DisplayName("Should register, save entries, and login successfully")
    public void testVaultLifecycle() {
        String vaultPath = tempDir.resolve("my_vault.pmvault").toString();

        // 1. Register & Create Vault
        PasswordManagerWrapper created = processManager.registerAndCreateFile(vaultPath, MASTER_PASS);
        assertNotNull(created);
        assertEquals(MASTER_PASS, created.getMasterPassword());

        // 2. Add sample password entry
        PasswordEntry entry = new PasswordEntry();
        entry.setTitle("AWS Production Console");
        entry.setUserName("root_admin");
        entry.setPassword("AWS_Super_Secret_2026!");
        entry.setUrl("https://aws.amazon.com");
        entry.setGroup("Cloud");
        created.getPasswordManagerContent().getPasswordList().add(entry);

        // 3. Save Vault
        boolean saved = processManager.saveFile(created, vaultPath, true);
        assertTrue(saved, "Vault should save successfully");

        // 4. Login and verify entry decrypted
        PasswordManagerWrapper loaded = processManager.loginAndLoadFile(vaultPath, MASTER_PASS, true);
        assertNotNull(loaded, "Login with correct master password must succeed");
        assertTrue(processManager.isPasswordCorrect());
        assertEquals(1, loaded.getPasswordManagerContent().getPasswordList().size());

        PasswordEntry retrieved = loaded.getPasswordManagerContent().getPasswordList().get(0);
        assertEquals("AWS Production Console", retrieved.getTitle());
        assertEquals("AWS_Super_Secret_2026!", retrieved.getPassword());
    }

    @Test
    @DisplayName("Login with wrong password must fail safely without throwing exceptions")
    public void testWrongPasswordRejection() {
        String vaultPath = tempDir.resolve("wrong_pass_vault.pmvault").toString();
        processManager.registerAndCreateFile(vaultPath, MASTER_PASS);

        PasswordManagerWrapper failed = processManager.loginAndLoadFile(vaultPath, "IncorrectPassword!", true);
        assertNull(failed, "Login with incorrect password must return null");
    }

    @Test
    @DisplayName("Null and non-existent file handling must be safe")
    public void testNullAndMissingFileSafety() {
        assertNull(processManager.loginAndLoadFile(null, MASTER_PASS, false));
        assertNull(processManager.loginAndLoadFile(tempDir.resolve("non_existent.pmvault").toString(), MASTER_PASS, false));
        assertFalse(processManager.saveFile(null, null, false));
    }
}
