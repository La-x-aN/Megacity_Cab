package com.Megacity_Cab.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Ride {
	
	public enum Status { REQUESTED, PENDING, ASSIGNED, COMPLETED }

    private int rideId;
    private int userId;
    private String pickupLocation;
    private String destination;
    private int assignedRiderId;
    private String requestTime;
    private LocalDateTime bookedTime; 
    private Status status;
    private LocalDateTime scheduledTime;
    private LocalDateTime deadlineTime;
    

 
    public int getRideId() { return rideId; }
    public void setRideId(int rideId) { this.rideId = rideId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public int getAssignedRiderId() { return assignedRiderId; }
    public void setAssignedRiderId(int assignedRiderId) { this.assignedRiderId = assignedRiderId; }
    
    public String getRequestTime() { return requestTime; }
    public void setRequestTime(String requestTime) { this.requestTime = requestTime; }

	
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(LocalDateTime scheduledTime) { 
        this.scheduledTime = scheduledTime; 
    }
    
    public LocalDateTime getBookedTime() { return bookedTime; }
    public void setBookedTime(LocalDateTime bookedTime) { 
        this.bookedTime = bookedTime; 
    }
    
    public LocalDateTime getDeadlineTime() { return deadlineTime; }
    public void setDeadlineTime(LocalDateTime deadlineTime) { 
        this.deadlineTime = deadlineTime; 
    }

    public boolean isEditable() {
        return status == Status.REQUESTED 
            && deadlineTime != null 
            && LocalDateTime.now().isBefore(deadlineTime);
    }
    public Date getScheduledTimeAsDate() {
        return Date.from(
            scheduledTime.atZone(ZoneId.systemDefault()).toInstant()
        );
    }

	
}