/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.passwordmanager.vo.types;

import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author Tom7668h
 */
public enum EncryptionType {

    AES("AES");

    public final String encrytion;

    private EncryptionType(String encrytion) {
        this.encrytion = encrytion;
    }

    @Override
    public String toString() {
        return this.encrytion;
    }

    public static Optional<EncryptionType> get(String encrytion) {
        return Arrays.stream(EncryptionType.values())
                .filter(env -> env.encrytion.equals(encrytion))
                .findFirst();
    }

}
