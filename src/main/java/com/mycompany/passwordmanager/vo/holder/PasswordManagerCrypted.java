/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.vo.holder;

/**
 *
 * @author Tom7668h
 */
public class PasswordManagerCrypted {
    
    private String hashedPassword;
    private String cryptedPasswordManagerWrapper;

    public String getCryptedPasswordManagerWrapper() {
        return cryptedPasswordManagerWrapper;
    }

    public void setCryptedPasswordManagerWrapper(String cryptedPasswordEntryList) {
        this.cryptedPasswordManagerWrapper = cryptedPasswordEntryList;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    
}
