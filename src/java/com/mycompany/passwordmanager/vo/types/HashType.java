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
public enum HashType {

    SHA1("SHA-1"),
    SHA224("SHA-224"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512"),
    MD2("MD2"),
    MD5("MD5");

    public final String hash;

    private HashType(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return this.hash;
    }

    public static Optional<HashType> get(String hash) {
        return Arrays.stream(HashType.values())
            .filter(env -> env.hash.equals(hash))
            .findFirst();
    }

}
