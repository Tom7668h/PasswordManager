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
public enum GroupNotExistingType {

    CREATE_GROUP("Create Group"),
    REGISTER_EMPTY_GROUP("Register Empty Group");

    public final String groupNotExistingType;

    private GroupNotExistingType(String control) {
        this.groupNotExistingType = control;
    }

    @Override
    public String toString() {
        return this.groupNotExistingType;
    }

    public static Optional<GroupNotExistingType> get(String groupNotExistingType) {
        return Arrays.stream(GroupNotExistingType.values())
                .filter(env -> env.groupNotExistingType.equals(groupNotExistingType))
                .findFirst();
    }

}
