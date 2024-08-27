/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.util;

import com.mycompany.passwordmanager.util.smart.FileUtil;
import com.google.gson.Gson;
import com.mycompany.passwordmanager.fix.Constants;
import com.mycompany.passwordmanager.vo.config.OptionsGeneralSingelton;

/**
 *
 * @author Tom7668h
 */
public class OptionsManager {
    
    static {
        gson = new Gson();
    }
    
    private static Gson gson;

    public static void loadGeneralOptions() {
        String optionsString = FileUtil.readFile(Constants.GENERAL_OPTION_CONFIG_PATH);
        OptionsGeneralSingelton.setInstance(gson.fromJson(optionsString, OptionsGeneralSingelton.class));
    }
    public static boolean saveGeneralOptions() {
        String optionsString = gson.toJson(OptionsGeneralSingelton.getInstance());
        return FileUtil.writeFile(optionsString, Constants.GENERAL_OPTION_CONFIG_PATH);
    }
    

}
