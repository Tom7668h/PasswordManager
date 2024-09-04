/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.vo.holder;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Tom7668h
 */
public class PasswordManagerContent {
    
    private ArrayList<PasswordEntry> passwordList;
    private ArrayList<String> groupList;

    public ArrayList<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<String> groupList) {
        this.groupList = groupList;
    }

    public ArrayList<PasswordEntry> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(ArrayList<PasswordEntry> passwordList) {
        this.passwordList = passwordList;
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
        final PasswordManagerContent other = (PasswordManagerContent) obj;
        if (!Objects.equals(this.passwordList, other.passwordList)) {
            return false;
        }
        return Objects.equals(this.groupList, other.groupList);
    }
    
    
}
