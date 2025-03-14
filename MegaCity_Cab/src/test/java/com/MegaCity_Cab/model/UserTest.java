package com.MegaCity_Cab.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultRole() {
        User user = new User();
        assertEquals(User.Role.USER, user.getRole(), "Default role should be USER");
    }

    @Test
    void testNullRoleHandling() {
        User user = new User();
        
        // Test null Role enum
        user.setRole(null);
        assertEquals(User.Role.USER, user.getRole(), "Null role should default to USER");
        
        // Test null String input
        user.setRoleFromString(null);
        assertEquals(User.Role.USER, user.getRole(), "Null string should default to USER");
        
        // Test empty string
        user.setRoleFromString("");
        assertEquals(User.Role.USER, user.getRole(), "Empty string should default to USER");
        
        // Test invalid string
        user.setRoleFromString("INVALID_ROLE");
        assertEquals(User.Role.USER, user.getRole(), "Invalid role should default to USER");
    }

    @Test
    void testValidRoleAssignments() {
        User user = new User();
        
        // Test direct enum assignment
        user.setRole(User.Role.ADMIN);
        assertEquals(User.Role.ADMIN, user.getRole(), "Setting ADMIN role should work");
        
        user.setRole(User.Role.RIDER);
        assertEquals(User.Role.RIDER, user.getRole(), "Setting RIDER role should work");

        // Test string-based role assignment
        user.setRoleFromString("RIDER");
        assertEquals(User.Role.RIDER, user.getRole(), "String 'RIDER' should be assigned correctly");

        user.setRoleFromString("ADMIN");
        assertEquals(User.Role.ADMIN, user.getRole(), "String 'ADMIN' should be assigned correctly");

        user.setRoleFromString("USER");
        assertEquals(User.Role.USER, user.getRole(), "String 'USER' should be assigned correctly");
    }

    @Test
    void testEdgeCases() {
        User user = new User();
        
        // Test mixed case role assignment
        user.setRoleFromString("rIdEr");
        assertEquals(User.Role.RIDER, user.getRole(), "Mixed case 'rIdEr' should be assigned as RIDER");

        user.setRoleFromString("UsEr");
        assertEquals(User.Role.USER, user.getRole(), "Mixed case 'UsEr' should be assigned as USER");

        // Test whitespace handling
        user.setRoleFromString("  rider  ");
        assertEquals(User.Role.RIDER, user.getRole(), "Whitespace-padded 'rider' should be assigned as RIDER");

        user.setRoleFromString("   ADMIN   ");
        assertEquals(User.Role.ADMIN, user.getRole(), "Whitespace-padded 'ADMIN' should be assigned as ADMIN");
    }
}
