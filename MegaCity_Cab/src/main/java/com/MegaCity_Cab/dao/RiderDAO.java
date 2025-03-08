package com.MegaCity_Cab.dao;

import com.MegaCity_Cab.model.Rider;
import com.MegaCity_Cab.utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RiderDAO {
    public boolean createRider(Rider rider) throws Exception {
        String sql = "INSERT INTO riders (user_id, vehicle_type, vehicle_model, vehicle_number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rider.getUserId());
            stmt.setString(2, rider.getVehicleType());
            stmt.setString(3, rider.getVehicleModel());
            stmt.setString(4, rider.getVehicleNumber());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Rider findByUserId(int userId) throws Exception {
        String sql = "SELECT * FROM riders WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                Rider rider = new Rider();
                rider.setRiderId(rs.getInt("rider_id"));
                rider.setUserId(rs.getInt("user_id"));
                rider.setVehicleType(rs.getString("vehicle_type"));
                rider.setVehicleModel(rs.getString("vehicle_model"));
                rider.setVehicleNumber(rs.getString("vehicle_number"));
                return rider;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Rider> getAllRiders() throws Exception {
        List<Rider> riders = new ArrayList<>();
        String sql = "SELECT * FROM riders";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Rider rider = new Rider();
                rider.setRiderId(rs.getInt("rider_id"));
                rider.setUserId(rs.getInt("user_id"));
                rider.setVehicleType(rs.getString("vehicle_type"));
                rider.setVehicleModel(rs.getString("vehicle_model"));
                rider.setVehicleNumber(rs.getString("vehicle_number"));
                riders.add(rider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return riders;
    }
    public List<Rider> getAllAvailableRiders() throws SQLException {
        List<Rider> riders = new ArrayList<>();
        // Match exact database column name (e.g., "is_available")
        String sql = "SELECT * FROM riders WHERE is_available = true"; 
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rider rider = new Rider();
                rider.setRiderId(rs.getInt("rider_id"));
                rider.setVehicleType(rs.getString("vehicle_type"));
                rider.setVehicleNumber(rs.getString("vehicle_number"));
                riders.add(rider);
            }
        }
        return riders;
    }
    
    

}