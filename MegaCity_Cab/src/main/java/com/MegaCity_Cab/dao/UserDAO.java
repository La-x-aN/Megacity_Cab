package com.MegaCity_Cab.dao;

import com.MegaCity_Cab.model.User;
import com.MegaCity_Cab.utils.DBUtil;
import java.sql.*;
import java.sql.SQLException;

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
                user.setRole(rs.getString("role"));
                return user;
            }
            return null; // No user found
        }
    }
}