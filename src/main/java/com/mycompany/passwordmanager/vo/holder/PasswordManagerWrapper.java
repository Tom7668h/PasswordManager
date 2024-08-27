/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.vo.holder;

import java.util.Objects;

/**
 *
 * @author Tom7668h
 */
public class PasswordManagerWrapper {

    private String masterPassword;
    private PasswordManagerContent passwordManagerContent;

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public PasswordManagerContent getPasswordManagerContent() {
        return passwordManagerContent;
    }

    public void setPasswordManagerContent(PasswordManagerContent passwordEntryList) {
        this.passwordManagerContent = passwordEntryList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PasswordManagerWrapper other = (PasswordManagerWrapper) obj;
        if (!Objects.equals(this.masterPassword, other.masterPassword)) {
            return false;
        }
        return Objects.equals(this.passwordManagerContent, other.passwordManagerContent);
    }
}
