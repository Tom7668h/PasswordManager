/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.passwordmanager.bo.crypt;

/**
 *
 * @author Tom7668h
 */
public interface TextEncrypter {
    
    public String encrypt(String text, String password);
    
    public String decrypt(String text, String password);
    
}
