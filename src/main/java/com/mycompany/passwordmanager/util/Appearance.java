package com.mycompany.passwordmanager.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.passwordmanager.vo.config.OptionsGeneralSingelton;
import com.mycompany.passwordmanager.vo.types.LanguageTyp;
import com.mycompany.passwordmanager.vo.types.LookAndFeelType;
import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 * Modern UI Look & Feel and Resource Manager
 */
public class Appearance {

    public static void setLookAndFeel(LookAndFeelType type) {
        if (type == null) {
            FlatDarkLaf.setup();
            return;
        }

        switch (type) {
            case LIGHT -> FlatLightLaf.setup();
            case DARK -> FlatDarkLaf.setup();
            default -> FlatDarkLaf.setup();
        }
    }

    public static Icon getIcon(String iconName, String iconFolder) {
        final String lookAndFeelName = UIManager.getLookAndFeel() != null ? UIManager.getLookAndFeel().getName() : "FlatLaf Dark";
        final boolean darkMode = lookAndFeelName.contains("Dark") || lookAndFeelName.contains("Darcula");
        final String folder = darkMode ? "white" : "black";
        final String relPath = "/com/mycompany/res/icon/" + folder + "/" + iconFolder + "/" + iconName + ".png";

        // 1. Try ClassLoader Resource (for JAR execution)
        URL resourceUrl = Appearance.class.getResource(relPath);
        if (resourceUrl != null) {
            return new ImageIcon(resourceUrl);
        }

        // 2. Try file system relative path
        File file = new File("src/main/resources" + relPath);
        if (file.exists()) {
            return new ImageIcon(file.getAbsolutePath());
        }

        // 3. Fallback: return clean empty placeholder icon to prevent NPEs
        return new EmptyIcon(16, 16);
    }

    public static ResourceBundle loadLanguage() {
        Locale locale = Locale.GERMAN;
        try {
            if (OptionsGeneralSingelton.getInstance() != null && OptionsGeneralSingelton.getInstance().getLanguage() != null) {
                if (OptionsGeneralSingelton.getInstance().getLanguage().equals(LanguageTyp.ENGLISH)) {
                    locale = Locale.ENGLISH;
                }
            }
        } catch (Exception ignored) {}

        try {
            return ResourceBundle.getBundle("com.mycompany.res.language.Language", locale);
        } catch (Exception e) {
            return ResourceBundle.getBundle("com.mycompany.res.language.Language_DE", Locale.GERMAN);
        }
    }

    private static class EmptyIcon implements Icon {
        private final int width;
        private final int height;

        public EmptyIcon(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {}

        @Override
        public int getIconWidth() { return width; }

        @Override
        public int getIconHeight() { return height; }
    }
}
