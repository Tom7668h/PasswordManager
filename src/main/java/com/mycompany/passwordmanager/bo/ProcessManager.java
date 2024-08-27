/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JOptionPane;

/**
 *
 * @author Tom7668h
 */
public class ProcessManager {

    static {
        TEXT_ENCRYPTER_AES = new TextEncrypterAES();
        GSON = new Gson();
    }

    final static TextEncrypterAES TEXT_ENCRYPTER_AES;
    final private static Gson GSON;
    private boolean passwordCorrect;

    public boolean isPasswordCorrect() {
        return passwordCorrect;
    }

    public PasswordManagerWrapper loginAndLoadFile(String filePath, String password, boolean loadConfig) {
        final String fileContentEncoded = FileUtil.readFile(filePath);
        if (fileContentEncoded == null) {
            JOptionPane.showMessageDialog(null, "File could not be read", "Read File", JOptionPane.PLAIN_MESSAGE);
        }
        final String fileContent = new String(Base64.getDecoder().decode(fileContentEncoded));
        final String passwordManagerCryptedString = fileContent.split(System.lineSeparator())[0];
        if (loadConfig) {
            final String optionsString = fileContent.split(System.lineSeparator())[1];
            OptionsSpecificSingelton.setInstance(GSON.fromJson(optionsString, OptionsSpecificSingelton.class));
        }
        final PasswordManagerCrypted masterFileCrypted = GSON.fromJson(passwordManagerCryptedString, PasswordManagerCrypted.class);
        if (PasswordHasher.hash(password, OptionsSpecificSingelton.getInstance().getHashType().toString()).equals(masterFileCrypted.getHashedPassword())) {
            passwordCorrect = true;
            final PasswordManagerWrapper passwordManagerWrapper = new PasswordManagerWrapper();
            final PasswordManagerContent passwordManagerContent = GSON.fromJson(TEXT_ENCRYPTER_AES.decrypt(masterFileCrypted.getCryptedPasswordManagerWrapper(), password), PasswordManagerContent.class);
            passwordManagerWrapper.setMasterPassword(password);
            passwordManagerWrapper.setPasswordManagerContent(passwordManagerContent);
            OptionsGeneralSingelton.getInstance().setFilePath(filePath);
            return passwordManagerWrapper;
        }
        return null;
    }

    public PasswordManagerWrapper registerAndCreateFile(String filePath, String password) {
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
        return passwordManagerWrapper;
    }

    public boolean saveFile(PasswordManagerWrapper passwordManagerWrapper, String filePath, boolean saveConfig) {
        final PasswordManagerCrypted passwordManagerCrypted = new PasswordManagerCrypted();
        final String encryptedPasswordManagerWrapper = TEXT_ENCRYPTER_AES.encrypt(GSON.toJson(passwordManagerWrapper.getPasswordManagerContent()), passwordManagerWrapper.getMasterPassword());
        passwordManagerCrypted.setHashedPassword(PasswordHasher.hash(passwordManagerWrapper.getMasterPassword(), OptionsSpecificSingelton.getInstance().getHashType().toString()));
        passwordManagerCrypted.setCryptedPasswordManagerWrapper(encryptedPasswordManagerWrapper);
        String fileContent = GSON.toJson(passwordManagerCrypted);
        if (saveConfig) {
            fileContent += System.lineSeparator() + GSON.toJson(OptionsSpecificSingelton.getInstance());
        }
        fileContent = Base64.getEncoder().encodeToString(fileContent.getBytes());
        return FileUtil.writeFile(fileContent, filePath);
    }

    public PasswordManagerWrapper loadFile(String filePath, String password) {
        final String fileContentEncoded = FileUtil.readFile(filePath);
        if (fileContentEncoded == null) {
            JOptionPane.showMessageDialog(null, "File could not be read", "Read File", JOptionPane.PLAIN_MESSAGE);
        }
        final String fileContent = new String(Base64.getDecoder().decode(fileContentEncoded));
        final String passwordManagerCryptedString = fileContent.split(System.lineSeparator())[0];
        final PasswordManagerCrypted masterFileCrypted = GSON.fromJson(passwordManagerCryptedString, PasswordManagerCrypted.class);
        if (masterFileCrypted == null) {
            return null;
        }
        final PasswordManagerWrapper passwordManagerWrapper = new PasswordManagerWrapper();
        final PasswordManagerContent passwordManagerContent = GSON.fromJson(TEXT_ENCRYPTER_AES.decrypt(masterFileCrypted.getCryptedPasswordManagerWrapper(), password), PasswordManagerContent.class);
        passwordManagerWrapper.setMasterPassword(password);
        passwordManagerWrapper.setPasswordManagerContent(passwordManagerContent);
        return passwordManagerWrapper;
    }

    private void createDefaultOptionsSpecific() {
        OptionsSpecificSingelton.getInstance().setEncryptionType(EncryptionType.AES);
        OptionsSpecificSingelton.getInstance().setHashType(HashType.SHA512);
        OptionsSpecificSingelton.getInstance().setIdenticalTitleType(IdenticalTitleType.USE_EXISTING_ENTRY);
        OptionsSpecificSingelton.getInstance().setGroupNotExistingType(GroupNotExistingType.CREATE_GROUP);
    }

}
