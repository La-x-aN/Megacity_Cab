package com.MegaCity_Cab.dao;

import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RideDAO {
   

	 public boolean createRide(Ride ride) throws Exception {
	        String sql = "INSERT INTO rides (user_id, pickup_location, destination, scheduled_time, deadline_time, distance, cost, selected_vehicle, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            
	            stmt.setInt(1, ride.getUserId());
	            stmt.setString(2, ride.getPickupLocation());
	            stmt.setString(3, ride.getDestination());
	            stmt.setTimestamp(4, Timestamp.valueOf(ride.getScheduledTime()));
	            stmt.setTimestamp(5, ride.getDeadlineTime() != null ? Timestamp.valueOf(ride.getDeadlineTime()) : null);
	            stmt.setDouble(6, ride.getDistance());
	            stmt.setDouble(7, ride.getCost());
	            stmt.setString(8, ride.getSelectedVehicle().name());
	            stmt.setString(9, ride.getStatus().name());

	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }


	public List<Ride> getUserRides(int userId) throws Exception {
	    List<Ride> rides = new ArrayList<>();
	    String sql = "SELECT * FROM rides WHERE user_id = ? AND status != 'COMPLETED' ORDER BY scheduled_time";
	    
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        
	        while(rs.next()) {
	            rides.add(mapRideFromResultSet(rs));
	        }
	    } catch (SQLException e) {
	        throw new Exception("Failed to fetch user rides", e); 
	    }
	    return rides;
	}
	

	 public boolean updateRide(Ride ride) throws Exception {
	        String sql = "UPDATE rides SET pickup_location = ?, destination = ?, distance = ?, selected_vehicle = ?, cost = ?, scheduled_time = ? WHERE ride_id = ?";
	        try (Connection conn = DBUtil.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            
	            stmt.setString(1, ride.getPickupLocation());
	            stmt.setString(2, ride.getDestination());
	            stmt.setDouble(3, ride.getDistance());
	            stmt.setString(4, ride.getSelectedVehicle().name());
	            stmt.setDouble(5, ride.getCost());
	            stmt.setTimestamp(6, Timestamp.valueOf(ride.getScheduledTime()));
	            stmt.setInt(7, ride.getRideId());

	            return stmt.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

    public boolean deleteRide(int rideId) throws Exception {
        String sql = "DELETE FROM rides WHERE ride_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rideId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Ride mapRideFromResultSet(ResultSet rs) throws SQLException, IllegalArgumentException {
        Ride ride = new Ride();
        ride.setRideId(rs.getInt("ride_id"));
        ride.setUserId(rs.getInt("user_id"));
        ride.setPickupLocation(rs.getString("pickup_location"));
        ride.setDestination(rs.getString("destination"));
        ride.setDistance(rs.getDouble("distance"));
        ride.setCost(rs.getDouble("cost"));
        
        String vehicleStr = rs.getString("selected_vehicle");
        try {
            if (vehicleStr == null || vehicleStr.trim().isEmpty()) {
                System.err.println("Warning: Empty vehicle type for ride " + ride.getRideId());
                ride.setSelectedVehicle(Ride.SelectedVehicle.CAR); // Default to CAR
            } else {
                ride.setSelectedVehicle(Ride.SelectedVehicle.fromString(vehicleStr));
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid vehicle type: " + vehicleStr);
            ride.setSelectedVehicle(Ride.SelectedVehicle.CAR);
        }

        // Status handling
        try {
            ride.setStatus(Ride.Status.valueOf(rs.getString("status").trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid status in DB: " + rs.getString("status"));
            ride.setStatus(Ride.Status.PENDING);
        }

        // Date/time handling
        ride.setScheduledTime(rs.getTimestamp("scheduled_time").toLocalDateTime());
        
        Timestamp deadlineTimestamp = rs.getTimestamp("deadline_time");
        if (deadlineTimestamp != null) {
            ride.setDeadlineTime(deadlineTimestamp.toLocalDateTime());
        }

        ride.setBookedTime(
            rs.getTimestamp("booked_time") != null ?
            rs.getTimestamp("booked_time").toLocalDateTime() :
            null
        );

        Integer assignedRiderId = rs.getObject("assigned_rider_id", Integer.class);
        ride.setAssignedRiderId(assignedRiderId);
        return ride;
    }


    public boolean completeRide(int rideId) throws SQLException {
        String sql = "UPDATE rides SET status = 'COMPLETED' WHERE ride_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rideId);
            return stmt.executeUpdate() > 0;
        }
    }
	
	public Ride getRideById(int rideId) throws Exception {
	    String sql = "SELECT * FROM rides WHERE ride_id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, rideId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return mapRideFromResultSet(rs);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public boolean assignRider(int rideId, int riderId) throws Exception {
	    String sql = "UPDATE rides SET assigned_rider_id = ?, status = 'ASSIGNED' " +
	                 "WHERE ride_id = ? AND status = 'REQUESTED'"; 
	    
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, riderId);
	        stmt.setInt(2, rideId);
	        
	        int affectedRows = stmt.executeUpdate();
	        
	        return affectedRows > 0;
	        
	    } catch (SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	        throw new Exception("Database error: " + e.getMessage());
	    }
	}

	
	public List<Ride> getAllRides() throws Exception {
	    List<Ride> rides = new ArrayList<>();
	    String sql = "SELECT * FROM rides ORDER BY booked_time DESC";
	    
	    try (Connection conn = DBUtil.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            rides.add(mapRideFromResultSet(rs));
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rides;
	}
	
	public List<Ride> getRidesByRider(int riderId) throws SQLException {
	    List<Ride> rides = new ArrayList<>();
	    String sql = "SELECT * FROM rides WHERE assigned_rider_id = ?";
	    
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, riderId);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            rides.add(mapRideFromResultSet(rs));
	        }
	    }
	    return rides;
	}
	
	
}