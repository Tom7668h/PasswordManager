/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.vo.config;

import com.mycompany.passwordmanager.vo.types.ButtonAppearanceType;
import com.mycompany.passwordmanager.vo.types.LanguageTyp;
import com.mycompany.passwordmanager.vo.types.LookAndFeelType;

/**
 *
 * @author Tom7668h
 */
public class OptionsGeneralSingelton {

    private String defaultFilePath;
    private LanguageTyp language;
    private ButtonAppearanceType buttonAppearance;
    private LookAndFeelType lookAndFeel;

    private static OptionsGeneralSingelton INSTANCE;

    private OptionsGeneralSingelton() {

    }

    public static OptionsGeneralSingelton getInstance() {
        if (OptionsGeneralSingelton.INSTANCE == null) {
            OptionsGeneralSingelton.INSTANCE = new OptionsGeneralSingelton();
        }
        return OptionsGeneralSingelton.INSTANCE;
    }
    
    public static void setInstance(OptionsGeneralSingelton optionsGeneralSingelton) {
        INSTANCE = optionsGeneralSingelton;
    }

    public String getFilePath() {
        return defaultFilePath;
    }

    public void setFilePath(String filePath) {
        this.defaultFilePath = filePath;
    }

    public LanguageTyp getLanguage() {
        return language;
    }

    public void setLanguage(LanguageTyp language) {
        this.language = language;
    }

    public ButtonAppearanceType getButtonAppearance() {
        return buttonAppearance;
    }

    public void setButtonAppearance(ButtonAppearanceType buttonAppearance) {
        this.buttonAppearance = buttonAppearance;
    }

    public LookAndFeelType getLookAndFeel() {
        return lookAndFeel;
    }

    public void setLookAndFeel(LookAndFeelType lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

}
