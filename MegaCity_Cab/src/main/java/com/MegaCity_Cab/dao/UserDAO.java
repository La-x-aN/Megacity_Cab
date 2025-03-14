package com.MegaCity_Cab.dao;

import com.MegaCity_Cab.model.User;
import com.MegaCity_Cab.utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public boolean createUser(User user) throws Exception {
        // Check for existing email
        if (findByEmail(user.getEmail()) != null) {
            throw new SQLException("Email already registered");
        }

        String sql = "INSERT INTO users (name, nic, phone, email, password_hash, role) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set parameters
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getNic());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPasswordHash());
            stmt.setString(6, user.getRole().name());

            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("User creation failed, no rows affected");
            }

            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve generated user ID");
                }
            }
            return true;
        }
    }

    public User findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setNic(rs.getString("nic"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(User.Role.valueOf(rs.getString("role").toUpperCase()));
                return user;
            }
            return null; 
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setNic(rs.getString("nic"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
               
                user.setRole(User.Role.valueOf(rs.getString("role").toUpperCase())); 
                try {
                    user.setRole(User.Role.valueOf(rs.getString("role").toUpperCase()));
                } catch (IllegalArgumentException e) {
                    user.setRole(User.Role.USER); // Default to USER
                    System.err.println("Invalid role in DB: " + rs.getString("role"));
                }
                
                // FIXED: Add user to list
                users.add(user); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setNic(rs.getString("nic"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                
                // Handle role with error checking
                try {
                    user.setRole(User.Role.valueOf(rs.getString("role").toUpperCase()));
                } catch (IllegalArgumentException e) {
                    user.setRole(User.Role.USER); // Default to USER
                }
                return user;
            }
            return null; // No user found
        }
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name=?, nic=?, phone=?, email=?, role=? WHERE id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getNic());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getRole().name());
            stmt.setInt(6, user.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}