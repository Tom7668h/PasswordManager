/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.passwordmanager.fix.Constants;
import com.mycompany.passwordmanager.vo.config.OptionsGeneralSingelton;
import com.mycompany.passwordmanager.vo.types.LanguageTyp;
import com.mycompany.passwordmanager.vo.types.LookAndFeelType;
import java.awt.Image;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 *
 * @author Tom
 */
public class Appearance {

    public static void setLookAndFeel(LookAndFeelType type) {
        switch (type) {
            case LIGHT ->
                FlatLightLaf.setup();
            case DARK ->
                FlatDarkLaf.setup();
            default ->
                FlatDarkLaf.setup();
        }
    }

    public static ResourceBundle loadLanguage() {
        Locale locale;
        if (OptionsGeneralSingelton.getInstance().getLanguage().equals(LanguageTyp.GERMAN)) {
            locale = Locale.GERMAN;
        } else {
            locale = Locale.ENGLISH;
        }
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("com.mycompany.res.language.Language", locale);
        return resourceBundle;
    }

    public static Icon getIcon(String iconName, int iconPixelSize) {

        final String lookAndFeelName = UIManager.getLookAndFeel().getName();
        final boolean darkMode = lookAndFeelName.equals("FlatLaf Dark") || lookAndFeelName.equals("FlatLaf Darcula");
        final String iconPath = Constants.ICON_FOLDER_PATH + "/" + iconName + (darkMode ? "_white" : "_black") + ".png";

        final ImageIcon imageIcon = new ImageIcon(iconPath);
        final Image imgage = imageIcon.getImage();
        final Icon icon = new ImageIcon(imgage.getScaledInstance(iconPixelSize, iconPixelSize, java.awt.Image.SCALE_SMOOTH));
        return icon;
    }

    public static Icon getIcon(String iconName) {
        final int iconPixelSize = Constants.DEFAULT_ICON_PIXEL_SIZE;
        return getIcon(iconName, iconPixelSize);
    }
}
