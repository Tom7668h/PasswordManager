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
public enum ButtonAppearanceType {

    TEXT("Text"),
    ICON("Icon");

    public final String buttonAppearance;

    private ButtonAppearanceType(String ButtonAppearance) {
        this.buttonAppearance = ButtonAppearance;
    }

    @Override
    public String toString() {
        return this.buttonAppearance;
    }

    public static Optional<ButtonAppearanceType> get(String buttonAppearance) {
        return Arrays.stream(ButtonAppearanceType.values())
                .filter(env -> env.buttonAppearance.equals(buttonAppearance))
                .findFirst();
    }

}
