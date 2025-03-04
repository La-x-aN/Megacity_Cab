package com.MegaCity_Cab.dao;

import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RideDAO {
   

	public boolean createRide(Ride ride) throws Exception {
        String sql = "INSERT INTO rides (user_id, pickup_location, destination, scheduled_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ride.getUserId());
            stmt.setString(2, ride.getPickupLocation());
            stmt.setString(3, ride.getDestination());
            stmt.setTimestamp(4, Timestamp.valueOf(ride.getScheduledTime()));
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	public List<Ride> getUserRides(int userId) throws Exception {
	    List<Ride> rides = new ArrayList<>();
	    String sql = "SELECT * FROM rides WHERE user_id = ? ORDER BY scheduled_time";
	    
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        
	        while(rs.next()) {
	            rides.add(mapRideFromResultSet(rs));
	        }
	    } catch (SQLException e) {
	        throw new Exception("Failed to fetch user rides", e); // Better error propagation
	    }
	    return rides;
	}

    public boolean updateRide(Ride ride) throws Exception {
        String sql = "UPDATE rides SET pickup_location = ?, destination = ?, scheduled_time = ? WHERE ride_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ride.getPickupLocation());
            stmt.setString(2, ride.getDestination());
            stmt.setTimestamp(3, Timestamp.valueOf(ride.getScheduledTime()));
            stmt.setInt(4, ride.getRideId());
            
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

    private Ride mapRideFromResultSet(ResultSet rs) throws SQLException {
        Ride ride = new Ride();

        ride.setRideId(rs.getInt("ride_id"));
        ride.setUserId(rs.getInt("user_id"));
        ride.setPickupLocation(rs.getString("pickup_location"));
        ride.setDestination(rs.getString("destination"));
        

        // Convert String to Status enum
        String statusStr = rs.getString("status");
        try {
            ride.setStatus(Ride.Status.valueOf(statusStr.trim().toUpperCase()));
        	} catch (IllegalArgumentException e) {
            ride.setStatus(Ride.Status.PENDING); // Fallback to default
            System.err.println("Invalid status value in DB: " + statusStr);
        	}

       
     
        ride.setScheduledTime(rs.getTimestamp("scheduled_time").toLocalDateTime());

        
        Timestamp deadlineTimestamp = rs.getTimestamp("deadline_time");
        if (!rs.wasNull()) { 
         ride.setDeadlineTime(deadlineTimestamp.toLocalDateTime());
        }

        ride.setBookedTime(
            rs.getTimestamp("booked_time") != null ?
            rs.getTimestamp("booked_time").toLocalDateTime() :
            null
        );

        ride.setDeadlineTime(
            rs.getTimestamp("deadline_time") != null ?
            rs.getTimestamp("deadline_time").toLocalDateTime() :
            null
        );

        ride.setAssignedRiderId(rs.getInt("assigned_rider_id"));
        ride.setScheduledTime(rs.getTimestamp("scheduled_time").toLocalDateTime());

        return ride;
    }


	public boolean completeRide(int rideId) {
		// TODO Auto-generated method stub
		return false;
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
	    String sql = "UPDATE rides SET assigned_rider_id = ?, status = 'assigned' WHERE ride_id = ?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, riderId);
	        stmt.setInt(2, rideId);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
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