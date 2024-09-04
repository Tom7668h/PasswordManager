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
public enum LookAndFeelType {

    DARK("Dark Mode"),
    LIGHT("Light Mode");

    public final String lookAndFeel;

    private LookAndFeelType(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

    @Override
    public String toString() {
        return this.lookAndFeel;
    }

    public static Optional<LookAndFeelType> get(String lookAndFeel) {
        return Arrays.stream(LookAndFeelType.values())
                .filter(env -> env.lookAndFeel.equals(lookAndFeel))
                .findFirst();
    }

}
