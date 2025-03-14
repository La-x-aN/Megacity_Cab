package com.MegaCity_Cab.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilTest {

    @Test
    void hashPasswordGeneratesValidBCryptHash() {
        String password = "securePassword123!";
        String hashed = SecurityUtil.hashPassword(password);
        
        assertNotNull(hashed);
        assertTrue(hashed.startsWith("$2a$10$"));
    }

    @Test
    void checkPasswordValidatesCorrectPassword() {
        String password = "admin@123";
        String hashed = SecurityUtil.hashPassword(password);
        
        assertTrue(SecurityUtil.checkPassword(password, hashed));
    }

    @Test
    void checkPasswordRejectsIncorrectPassword() {
        String originalPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        String hashed = SecurityUtil.hashPassword(originalPassword);
        
        assertFalse(SecurityUtil.checkPassword(wrongPassword, hashed));
    }

    @Test
    void checkPasswordThrowsOnInvalidHash() {
        String invalidHash = "invalid_hash_format";
        String password = "password123";
        
        assertThrows(IllegalArgumentException.class, () -> {
            SecurityUtil.checkPassword(password, invalidHash);
        });
    }

    @Test
    void emptyPasswordHandling() {
        String emptyPassword = "";
        String hashed = SecurityUtil.hashPassword(emptyPassword);
        
        assertTrue(SecurityUtil.checkPassword(emptyPassword, hashed));
        assertFalse(SecurityUtil.checkPassword(" ", hashed));
    }

    @Test
    void specialCharacterPasswords() {
        String complexPassword = "P@$$w0rd!≈ç√∫~";
        String hashed = SecurityUtil.hashPassword(complexPassword);
        
        assertTrue(SecurityUtil.checkPassword(complexPassword, hashed));
        assertFalse(SecurityUtil.checkPassword(complexPassword.toLowerCase(), hashed));
    }
}