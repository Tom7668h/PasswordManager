package com.mycompany.passwordmanager.vo.holder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PasswordEntry Model & Value Object Tests")
public class PasswordEntryTest {

    @Test
    @DisplayName("Should copy password entry correctly")
    public void testCopyConstructor() {
        PasswordEntry original = new PasswordEntry();
        original.setTitle("GitHub Corporate");
        original.setUserName("dev_admin");
        original.setPassword("SecurePass123!");
        original.setUrl("https://github.com");
        original.setGroup("Development");
        original.seteMail("admin@corp.io");
        original.setDetails("2FA via YubiKey");

        PasswordEntry copy = new PasswordEntry(original);
        assertEquals(original.getTitle(), copy.getTitle());
        assertEquals(original.getUserName(), copy.getUserName());
        assertEquals(original.getPassword(), copy.getPassword());
        assertEquals(original.getUrl(), copy.getUrl());
        assertEquals(original.getGroup(), copy.getGroup());
        assertEquals(original.geteMail(), copy.geteMail());
        assertEquals(original.getDetails(), copy.getDetails());
    }

    @Test
    @DisplayName("Equality and hashcode validation")
    public void testEqualsAndHashCode() {
        PasswordEntry entry1 = new PasswordEntry();
        entry1.setTitle("Email");
        entry1.setUserName("user");

        PasswordEntry entry2 = new PasswordEntry();
        entry2.setTitle("Email");
        entry2.setUserName("user");

        assertEquals(entry1, entry2);
        assertEquals(entry1.hashCode(), entry2.hashCode());
    }
}
