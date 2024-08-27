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
public enum LanguageTyp {

    GERMAN("German"),
    ENGLISH("English");

    public final String language;

    private LanguageTyp(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return this.language;
    }

    public static Optional<LanguageTyp> get(String language) {
        return Arrays.stream(LanguageTyp.values())
                .filter(env -> env.language.equals(language))
                .findFirst();
    }
}
