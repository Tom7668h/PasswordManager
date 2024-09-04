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
public enum IdenticalTitleType {

    USE_EXISTING_ENTRY("Use Existing Entry"),
    USE_IMPORTED_ENTRY("Use Imported Entry");


    public final String identicalTitleType;

    private IdenticalTitleType(String control) {
        this.identicalTitleType = control;
    }

    @Override
    public String toString() {
        return this.identicalTitleType;
    }
    
    public static Optional<IdenticalTitleType> get(String identicalTitleType) {
        return Arrays.stream(IdenticalTitleType.values())
                .filter(env -> env.identicalTitleType.equals(identicalTitleType))
                .findFirst();
    }

}
