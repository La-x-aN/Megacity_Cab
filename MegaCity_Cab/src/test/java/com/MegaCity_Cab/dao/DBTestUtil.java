package com.MegaCity_Cab.dao;

import com.MegaCity_Cab.utils.DBUtil;
import java.sql.Connection;
import java.sql.Statement;

public class DBTestUtil {
    public static void resetDatabase() throws Exception {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Drop tables if exist
            stmt.executeUpdate("DROP TABLE IF EXISTS rides CASCADE");
            stmt.executeUpdate("DROP TABLE IF EXISTS users CASCADE");
            stmt.executeUpdate("DROP TABLE IF EXISTS riders CASCADE");

            // Create tables with H2-compatible syntax
            stmt.executeUpdate(
                "CREATE TABLE users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "email VARCHAR(100) UNIQUE)"
            );
            
            stmt.executeUpdate(
                "CREATE TABLE riders (" +
                "rider_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id INT REFERENCES users(id))"
            );

            stmt.executeUpdate(
                "CREATE TABLE rides (" +
                "ride_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id INT REFERENCES users(id), " +
                "assigned_rider_id INT REFERENCES riders(rider_id), " +
                "pickup_location VARCHAR(255), " +
                "status VARCHAR(20))"
            );
        }
    }
}