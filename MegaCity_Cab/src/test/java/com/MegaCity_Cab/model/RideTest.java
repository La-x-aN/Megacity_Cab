package com.MegaCity_Cab.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

class RideTest {

    @Test
    void testRideSettersAndGetters() {
        Ride ride = new Ride();

        // Set values
        ride.setRideId(1);
        ride.setUserId(101);
        ride.setPickupLocation("Colombo");
        ride.setDestination("Kandy");
        ride.setAssignedRiderId(202);
        ride.setRequestTime("2025-03-11T10:00:00");
        ride.setScheduledTime(LocalDateTime.of(2025, 3, 11, 15, 0));
        ride.setDeadlineTime(LocalDateTime.of(2025, 3, 11, 18, 0));
        ride.setBookedTime(LocalDateTime.of(2025, 3, 11, 10, 30));
        ride.setDistance(120.5);
        ride.setCost(5000.00);
        ride.setStatus(Ride.Status.ASSIGNED);
        ride.setSelectedVehicle(Ride.SelectedVehicle.CAR);

       
        assertEquals(1, ride.getRideId());
        assertEquals(101, ride.getUserId());
        assertEquals("Colombo", ride.getPickupLocation());
        assertEquals("Kandy", ride.getDestination());
        assertEquals(202, ride.getAssignedRiderId());
        assertEquals("2025-03-11T10:00:00", ride.getRequestTime());
        assertEquals(LocalDateTime.of(2025, 3, 11, 15, 0), ride.getScheduledTime());
        assertEquals(LocalDateTime.of(2025, 3, 11, 18, 0), ride.getDeadlineTime());
        assertEquals(LocalDateTime.of(2025, 3, 11, 10, 30), ride.getBookedTime());
        assertEquals(120.5, ride.getDistance());
        assertEquals(5000.00, ride.getCost());
        assertEquals(Ride.Status.ASSIGNED, ride.getStatus());
        assertEquals(Ride.SelectedVehicle.CAR, ride.getSelectedVehicle());
    }

    @Test
    void testStatusEnum() {
        Ride ride = new Ride();
        ride.setStatus(Ride.Status.REQUESTED);
        assertEquals(Ride.Status.REQUESTED, ride.getStatus());

        ride.setStatus(Ride.Status.COMPLETED);
        assertEquals(Ride.Status.COMPLETED, ride.getStatus());
    }

    @Test
    void testSelectedVehicleEnum() {
        assertEquals(Ride.SelectedVehicle.MOTOR_BIKE, Ride.SelectedVehicle.fromString("motor_bike"));
        assertEquals(Ride.SelectedVehicle.CAR, Ride.SelectedVehicle.fromString("CAR"));
        assertThrows(IllegalArgumentException.class, () -> Ride.SelectedVehicle.fromString("INVALID"));
    }

    @Test
    void testCostCalculation() {
        Ride ride = new Ride();
        ride.setSelectedVehicle(Ride.SelectedVehicle.VAN);
        ride.setDistance(50); // 50 km
        assertEquals(100.00 + (50 * 60), ride.calculateCost(), 0.01);
    }

    @Test
    void testScheduledTimeAsDate() {
        Ride ride = new Ride();
        LocalDateTime now = LocalDateTime.of(2025, 3, 11, 15, 0);
        ride.setScheduledTime(now);

        Date expectedDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        assertEquals(expectedDate, ride.getScheduledTimeAsDate());
    }

    @Test
    void testIsEditable() {
        Ride ride = new Ride();

        ride.setStatus(Ride.Status.REQUESTED);
        assertTrue(ride.isEditable());

        ride.setStatus(Ride.Status.ASSIGNED);
        assertFalse(ride.isEditable());
    }
}
