/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.util.smart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tom7668h
 */
public class FileUtil {

    public static boolean writeFile(String content, String path) {
        return writeFile(content, path, StandardCharsets.UTF_8);
    }

    public static boolean writeFile(String content, String path, Charset charset) {
        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path), charset))) {
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static String readFile(String path) {
        return readFile(path, StandardCharsets.UTF_8);
    }

    public static String readFile(String path, Charset charset) {
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path), charset))) {
            final StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
                if (bufferedReader.ready()) {
                    content.append(System.lineSeparator());
                }
            }
            bufferedReader.close();
            return content.toString();
        } catch (Exception ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static boolean deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean createFile(String path) {
        try {
            final File yourFile = new File(path);
            return yourFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean existsFile(String path) {
        File file = new File(path);
        return file.exists() && !file.isDirectory();
    }
}
