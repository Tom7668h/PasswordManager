/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.passwordmanager.run;

import com.mycompany.passwordmanager.gui.LoginDialog;
import com.mycompany.passwordmanager.gui.PasswordManagerFrame;
import com.mycompany.passwordmanager.gui.RegisterDialog;
import com.mycompany.passwordmanager.util.Appearance;
import com.mycompany.passwordmanager.util.OptionsManager;
import com.mycompany.passwordmanager.vo.config.OptionsGeneralSingelton;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Tom7668h
 */
public class RunPasswordManager {

    public static void main(String[] args) {
        LogManager.getLogger(PasswordManagerFrame.class).info("PasswordManager started");
        OptionsManager.loadGeneralOptions();
        Appearance.setLookAndFeel(OptionsGeneralSingelton.getInstance().getLookAndFeel());
        if (OptionsGeneralSingelton.getInstance().getFilePath().isBlank()) {
            runMyPasswordManagerRegister();
        } else {
            runMyPasswordManagerLogin();
        }
    }

    public static void runMyPasswordManagerLogin() {
        final LoginDialog loginDialog = new LoginDialog(null, true);
        loginDialog.setVisible(true);
        if (loginDialog.isLoginSuccessful() && loginDialog.getPasswordManager() != null) {
            final PasswordManagerFrame passwordManagerDialog = new PasswordManagerFrame(loginDialog.getPasswordManager());
            passwordManagerDialog.setVisible(true);
        }
    }

    public static void runMyPasswordManagerRegister() {
        final RegisterDialog registerDialog = new RegisterDialog(null, true);
        registerDialog.setVisible(true);
        if (registerDialog.isRegistrationSuccessful() && registerDialog.getPasswordManager() != null) {
            final PasswordManagerFrame passwordManagerDialog = new PasswordManagerFrame(registerDialog.getPasswordManager());
            passwordManagerDialog.setVisible(true);
        }
    }
}
