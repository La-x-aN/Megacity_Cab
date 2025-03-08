package com.MegaCity_Cab.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Ride {
    
    public enum Status { REQUESTED, PENDING, ASSIGNED, COMPLETED }
    
    public enum SelectedVehicle { 
        MOTOR_BIKE(30), THREE_WHEEL(35), CAR(45), VAN(60), TRUCK(80);
        
        private final double ratePerKm;
        
        SelectedVehicle(double rate) {
            this.ratePerKm = rate;
        }
        
        public double getRate() {
            return ratePerKm;
        }
        
        public static SelectedVehicle fromString(String value) {
            for (SelectedVehicle vehicle : values()) {
                if (vehicle.name().equalsIgnoreCase(value)) {
                    return vehicle;
                }
            }
            throw new IllegalArgumentException("No enum constant " + SelectedVehicle.class.getName() + "." + value);
        }
    }

    
    private int rideId;
    private int userId;
    private String pickupLocation;
    private String destination;
    private Integer assignedRiderId;
    private String requestTime;
    private LocalDateTime bookedTime; 
    private Status status;
    private LocalDateTime scheduledTime;
    private LocalDateTime deadlineTime;
    private double distance;  
    private double cost;
    private SelectedVehicle selectedVehicle;
    
   
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
    
    public Integer getAssignedRiderId() { 
        return assignedRiderId; 
    }
    public void setAssignedRiderId(Integer assignedRiderId) { 
        this.assignedRiderId = assignedRiderId; 
    }
    
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
    
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

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
    
    public double calculateCost() {
        double baseFare = 100.00;
        return baseFare + (distance * selectedVehicle.getRate());
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public SelectedVehicle getSelectedVehicle() { return selectedVehicle; }
    public void setSelectedVehicle(SelectedVehicle selectedVehicle) { this.selectedVehicle = selectedVehicle; }
}