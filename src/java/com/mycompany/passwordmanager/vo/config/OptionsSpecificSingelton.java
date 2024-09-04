/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.vo.config;

import com.mycompany.passwordmanager.vo.types.IdenticalTitleType;
import com.mycompany.passwordmanager.vo.types.EncryptionType;
import com.mycompany.passwordmanager.vo.types.GroupNotExistingType;
import com.mycompany.passwordmanager.vo.types.HashType;

/**
 *
 * @author Tom7668h
 */
public class OptionsSpecificSingelton {

    private EncryptionType encryptionType;
    private HashType hashType;
    private IdenticalTitleType identicalTitleType;
    private GroupNotExistingType groupNotExistingType;

    private static OptionsSpecificSingelton INSTANCE;

    private OptionsSpecificSingelton() {

    }

    public static OptionsSpecificSingelton getInstance() {
        if (OptionsSpecificSingelton.INSTANCE == null) {
            OptionsSpecificSingelton.INSTANCE = new OptionsSpecificSingelton();
        }
        return OptionsSpecificSingelton.INSTANCE;
    }
    
    public static void setInstance(OptionsSpecificSingelton optionsSpecificSingelton) {
        INSTANCE = optionsSpecificSingelton;
    }

    public EncryptionType getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(EncryptionType encryptionType) {
        this.encryptionType = encryptionType;
    }

    public HashType getHashType() {
        return hashType;
    }

    public void setHashType(HashType hashType) {
        this.hashType = hashType;
    }

    public IdenticalTitleType getIdenticalTitleType() {
        return identicalTitleType;
    }

    public void setIdenticalTitleType(IdenticalTitleType identicalTitleType) {
        this.identicalTitleType = identicalTitleType;
    }

    public GroupNotExistingType getGroupNotExistingType() {
        return groupNotExistingType;
    }

    public void setGroupNotExistingType(GroupNotExistingType groupNotExistingType) {
        this.groupNotExistingType = groupNotExistingType;
    }

}
