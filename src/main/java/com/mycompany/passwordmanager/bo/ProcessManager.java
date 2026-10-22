package com.mycompany.passwordmanager.bo;

import com.google.gson.Gson;
import com.mycompany.passwordmanager.bo.crypt.TextEncrypterAES;
import com.mycompany.passwordmanager.util.smart.FileUtil;
import com.mycompany.passwordmanager.vo.holder.PasswordManagerCrypted;
import com.mycompany.passwordmanager.vo.holder.PasswordManagerContent;
import com.mycompany.passwordmanager.vo.holder.PasswordManagerWrapper;
import com.mycompany.passwordmanager.vo.config.OptionsGeneralSingelton;
import com.mycompany.passwordmanager.vo.config.OptionsSpecificSingelton;
import com.mycompany.passwordmanager.vo.types.EncryptionType;
import com.mycompany.passwordmanager.vo.types.GroupNotExistingType;
import com.mycompany.passwordmanager.vo.types.HashType;
import com.mycompany.passwordmanager.vo.types.IdenticalTitleType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Enterprise Vault Process & Session Manager
 */
public class ProcessManager {

    private static final Logger LOGGER = LogManager.getLogger(ProcessManager.class);
    final static TextEncrypterAES TEXT_ENCRYPTER_AES = new TextEncrypterAES();
    final private static Gson GSON = new Gson();
    private boolean passwordCorrect;

    public boolean isPasswordCorrect() {
        return passwordCorrect;
    }

    public PasswordManagerWrapper loginAndLoadFile(String filePath, String password, boolean loadConfig) {
        if (filePath == null || password == null) {
            return null;
        }

        final String fileContentEncoded = FileUtil.readFile(filePath);
        if (fileContentEncoded == null || fileContentEncoded.isBlank()) {
            LOGGER.error("File could not be read or is empty: {}", filePath);
            return null;
        }

        try {
            final String fileContent = new String(Base64.getDecoder().decode(fileContentEncoded), StandardCharsets.UTF_8);
            final String[] lines = fileContent.split("\\r?\\n");
            if (lines.length == 0 || lines[0].isBlank()) {
                return null;
            }

            final String passwordManagerCryptedString = lines[0];

            if (loadConfig && lines.length > 1 && !lines[1].isBlank()) {
                try {
                    final String optionsString = lines[1];
                    OptionsSpecificSingelton loadedOptions = GSON.fromJson(optionsString, OptionsSpecificSingelton.class);
                    if (loadedOptions != null) {
                        OptionsSpecificSingelton.setInstance(loadedOptions);
                    }
                } catch (Exception e) {
                    LOGGER.warn("Failed to parse embedded specific options, keeping defaults", e);
                }
            }

            final PasswordManagerCrypted masterFileCrypted = GSON.fromJson(passwordManagerCryptedString, PasswordManagerCrypted.class);
            if (masterFileCrypted == null || masterFileCrypted.getHashedPassword() == null) {
                return null;
            }

            // Verify password using constant-time comparison
            String computedHash = PasswordHasher.hash(password, OptionsSpecificSingelton.getInstance().getHashType().toString());
            boolean hashMatches = false;

            if (computedHash != null && MessageDigest.isEqual(computedHash.getBytes(StandardCharsets.UTF_8), masterFileCrypted.getHashedPassword().getBytes(StandardCharsets.UTF_8))) {
                hashMatches = true;
            } else if (masterFileCrypted.getHashedPassword().startsWith("pbkdf2:")) {
                hashMatches = PasswordHasher.verifyPBKDF2(password.toCharArray(), masterFileCrypted.getHashedPassword());
            }

            if (hashMatches) {
                passwordCorrect = true;
                final PasswordManagerWrapper passwordManagerWrapper = new PasswordManagerWrapper();
                String decryptedJson = TEXT_ENCRYPTER_AES.decrypt(masterFileCrypted.getCryptedPasswordManagerWrapper(), password);
                if (decryptedJson == null) {
                    LOGGER.error("Failed to decrypt vault content");
                    return null;
                }

                final PasswordManagerContent passwordManagerContent = GSON.fromJson(decryptedJson, PasswordManagerContent.class);
                passwordManagerWrapper.setMasterPassword(password);
                passwordManagerWrapper.setPasswordManagerContent(passwordManagerContent != null ? passwordManagerContent : new PasswordManagerContent());
                OptionsGeneralSingelton.getInstance().setFilePath(filePath);
                return passwordManagerWrapper;
            } else {
                LOGGER.warn("Invalid master password attempt for file: {}", filePath);
            }
        } catch (Exception ex) {
            LOGGER.error("Failed to process login and load vault", ex);
        }

        return null;
    }

    public PasswordManagerWrapper registerAndCreateFile(String filePath, String password) {
        if (filePath == null || password == null) {
            return null;
        }

        createDefaultOptionsSpecific();
        if (!FileUtil.createFile(filePath)) {
            return null;
        }

        final PasswordManagerWrapper passwordManagerWrapper = new PasswordManagerWrapper();
        final PasswordManagerContent passwordManagerContent = new PasswordManagerContent();
        passwordManagerContent.setGroupList(new ArrayList<>());
        passwordManagerContent.setPasswordList(new ArrayList<>());
        passwordManagerWrapper.setMasterPassword(password);
        passwordManagerWrapper.setPasswordManagerContent(passwordManagerContent);
        OptionsGeneralSingelton.getInstance().setFilePath(filePath);

        // Immediately write empty initialized structure
        saveFile(passwordManagerWrapper, filePath, true);
        return passwordManagerWrapper;
    }

    public boolean saveFile(PasswordManagerWrapper passwordManagerWrapper, String filePath, boolean saveConfig) {
        if (passwordManagerWrapper == null || filePath == null || passwordManagerWrapper.getMasterPassword() == null) {
            return false;
        }

        try {
            // Create automated backup of previous version if it exists
            FileUtil.createBackup(filePath);

            final PasswordManagerCrypted passwordManagerCrypted = new PasswordManagerCrypted();
            final String jsonContent = GSON.toJson(passwordManagerWrapper.getPasswordManagerContent());
            final String encryptedPasswordManagerWrapper = TEXT_ENCRYPTER_AES.encrypt(jsonContent, passwordManagerWrapper.getMasterPassword());

            String hashType = OptionsSpecificSingelton.getInstance().getHashType() != null 
                    ? OptionsSpecificSingelton.getInstance().getHashType().toString() 
                    : "SHA-256";
            passwordManagerCrypted.setHashedPassword(PasswordHasher.hash(passwordManagerWrapper.getMasterPassword(), hashType));
            passwordManagerCrypted.setCryptedPasswordManagerWrapper(encryptedPasswordManagerWrapper);

            StringBuilder fileContent = new StringBuilder(GSON.toJson(passwordManagerCrypted));
            if (saveConfig) {
                fileContent.append("\n").append(GSON.toJson(OptionsSpecificSingelton.getInstance()));
            }

            String encodedContent = Base64.getEncoder().encodeToString(fileContent.toString().getBytes(StandardCharsets.UTF_8));
            return FileUtil.writeFile(encodedContent, filePath);
        } catch (Exception e) {
            LOGGER.error("Failed to save vault file: {}", filePath, e);
            return false;
        }
    }

    public PasswordManagerWrapper loadFile(String filePath, String password) {
        return loginAndLoadFile(filePath, password, false);
    }

    public static void createDefaultOptionsSpecific() {
        OptionsSpecificSingelton.getInstance().setEncryptionType(EncryptionType.AES);
        OptionsSpecificSingelton.getInstance().setGroupNotExistingType(GroupNotExistingType.CREATE_GROUP);
        OptionsSpecificSingelton.getInstance().setHashType(HashType.SHA256);
        OptionsSpecificSingelton.getInstance().setIdenticalTitleType(IdenticalTitleType.USE_EXISTING_ENTRY);
    }
}
