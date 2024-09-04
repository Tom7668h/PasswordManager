/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.passwordmanager.bo;

import com.mycompany.passwordmanager.vo.types.HashType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Tom7668h
 */
public class PasswordHasherTest {

    @Test
    public void testHashSha512() {
        String password = "MyTestPassword";
        String hashAlgorithem = HashType.SHA512.toString();
        String expResult = "6c1e1bd5f8d2d053d3cb4152a3a6464e977039f824500b5d60735fcb59dc1f8056f267e99214d7008ecc45e910d4ee0e729bfe793cee4a772b6c6781e355c175";
        String result = PasswordHasher.hash(password, hashAlgorithem);
        System.out.println("PasswordHasherTest - Password: " + password + ", Hash Algorithem: " + hashAlgorithem + ", Expected Result: " + expResult + " -> Result: " + result);
        assertEquals(expResult, result);
    }

    @Test
    public void testHashMd5() {
        String password = "MyTestPassword";
        String hashAlgorithem = HashType.MD5.toString();
        String expResult = "72418435a5ebb5949ccaa42f55bc15f3";
        String result = PasswordHasher.hash(password, hashAlgorithem);
        System.out.println("PasswordHasherTest - Password: " + password + ", Hash Algorithem: " + hashAlgorithem + ", Expected Result: " + expResult + " -> Result: " + result);
        assertEquals(expResult, result);
    }
}
