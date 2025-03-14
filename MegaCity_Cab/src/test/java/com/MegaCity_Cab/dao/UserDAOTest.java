package com.MegaCity_Cab.dao;

import static org.junit.jupiter.api.Assertions.*;
import com.MegaCity_Cab.model.User;
import com.MegaCity_Cab.utils.DBUtil;
import java.sql.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDAOTest {
    private UserDAO userDAO;
    private User testUser;

    @BeforeEach
    void setUp() throws Exception {
        userDAO = new UserDAO();
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create tables with H2-compatible syntax
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "nic VARCHAR(20) NOT NULL, " +
                "phone VARCHAR(15) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "password_hash VARCHAR(100) NOT NULL, " +
                "role VARCHAR(10) NOT NULL)"
            );

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS riders (" +
                "rider_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id INT NOT NULL, " +
                "vehicle_type VARCHAR(50) NOT NULL, " +
                "vehicle_model VARCHAR(100) NOT NULL, " +
                "vehicle_number VARCHAR(20) NOT NULL UNIQUE, " +
                "is_available BOOLEAN DEFAULT TRUE, " +
                "phone VARCHAR(20) NOT NULL, " +
                "FOREIGN KEY (user_id) REFERENCES users(id))"
            );

            // Clear existing data
            stmt.executeUpdate("DELETE FROM riders");
            stmt.executeUpdate("DELETE FROM users");
            
            // Reset sequences for H2
            stmt.executeUpdate("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
            stmt.executeUpdate("ALTER TABLE riders ALTER COLUMN rider_id RESTART WITH 1");

        } catch (SQLException e) {
            throw new RuntimeException("Test database setup failed", e);
        }

        // Initialize test user
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setNic("123456789X");
        testUser.setPhone("555-1234");
        testUser.setEmail("john@example.com");
        testUser.setPasswordHash("securehash");
        testUser.setRole(User.Role.USER);
    }

    @Test
    void testCreateAndRetrieveUser() throws Exception {
        // Test user creation
        assertTrue(userDAO.createUser(testUser));
        assertTrue(testUser.getId() > 0, "User ID should be generated");

        // Test retrieval by email
        User retrieved = userDAO.findByEmail(testUser.getEmail());
        assertNotNull(retrieved, "User should be found by email");
        assertEquals(testUser.getName(), retrieved.getName(), "Names should match");
        assertEquals(testUser.getEmail(), retrieved.getEmail(), "Emails should match");
    }

    @Test
    void testDuplicateEmailCreation() throws Exception {
        userDAO.createUser(testUser);
        
        User duplicateUser = new User();
        duplicateUser.setName("Duplicate John");
        duplicateUser.setEmail(testUser.getEmail());
        duplicateUser.setPasswordHash("anotherhash");
        
        Exception exception = assertThrows(SQLException.class, () -> {
            userDAO.createUser(duplicateUser);
        }, "Should throw on duplicate email");
        
        assertTrue(exception.getMessage().contains("Email already registered"), 
                 "Exception message should indicate duplicate email");
    }

    @Test
    void testGetAllUsers() throws Exception {
        userDAO.createUser(testUser);
        
        User secondUser = new User();
        secondUser.setName("Jane Smith");
        secondUser.setEmail("jane@example.com");
        secondUser.setPasswordHash("hash456");
        userDAO.createUser(secondUser);
        
        List<User> users = userDAO.getAllUsers();
        assertEquals(2, users.size(), "Should retrieve all created users");
    }

    @Test
    void testUpdateUser() throws Exception {
        userDAO.createUser(testUser);
        
        // Modify user details
        testUser.setName("Updated Name");
        testUser.setPhone("555-5678");
        testUser.setRole(User.Role.ADMIN);
        
        assertTrue(userDAO.updateUser(testUser), "Update should succeed");
        
        User updatedUser = userDAO.getUserById(testUser.getId());
        assertNotNull(updatedUser, "Updated user should exist");
        assertEquals("Updated Name", updatedUser.getName(), "Name should update");
        assertEquals("555-5678", updatedUser.getPhone(), "Phone should update");
        assertEquals(User.Role.ADMIN, updatedUser.getRole(), "Role should update");
    }

    @Test
    void testDeleteUser() throws Exception {
        userDAO.createUser(testUser);
        int userId = testUser.getId();
        
        assertTrue(userDAO.deleteUser(userId), "Delete should succeed");
        assertNull(userDAO.getUserById(userId), "User should be deleted");
    }

    @Test
    void testRoleHandling() throws Exception {
        User adminUser = new User();
        adminUser.setName("Admin User");
        adminUser.setEmail("admin@example.com");
        adminUser.setPasswordHash("adminhash");
        adminUser.setRole(User.Role.ADMIN);
        
        userDAO.createUser(adminUser);
        
        User retrieved = userDAO.getUserById(adminUser.getId());
        assertNotNull(retrieved, "Admin user should exist");
        assertEquals(User.Role.ADMIN, retrieved.getRole(), "Role should persist");
    }

    @Test
    void testInvalidUserRetrieval() throws Exception {
        assertNull(userDAO.getUserById(999), "Non-existent user should return null");
    }
}