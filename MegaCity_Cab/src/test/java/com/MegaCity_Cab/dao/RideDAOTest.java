package com.MegaCity_Cab.dao;

import static org.junit.jupiter.api.Assertions.*;
import com.MegaCity_Cab.model.Ride;
import com.MegaCity_Cab.model.Ride.SelectedVehicle;
import com.MegaCity_Cab.model.Ride.Status;
import com.MegaCity_Cab.model.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RideDAOTest {
    private RideDAO rideDAO;
    private UserDAO userDAO;
    private int testUserId;
    private int testRiderId;

    @BeforeEach
    void setupDatabase() throws Exception {
        // Initialize fresh H2 database
        DBTestUtil.resetDatabase();
        
        // Initialize DAOs
        rideDAO = new RideDAO();
        userDAO = new UserDAO();

        // Create test user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPasswordHash("password");
        userDAO.createUser(user);
        testUserId = user.getId();

        // Create test rider (simplified)
        testRiderId = 999; // Normally create through RiderDAO
    }

    @Test
    void createRideShouldPersistNewRide() throws Exception {
        Ride ride = createTestRide();
        
        boolean result = rideDAO.createRide(ride);
        assertTrue(result, "Ride creation should succeed");
        assertTrue(ride.getRideId() > 0, "Ride ID should be generated");
    }

    @Test
    void getRideByIdShouldReturnCorrectRide() throws Exception {
        Ride created = createAndSaveRide();
        
        Ride retrieved = rideDAO.getRideById(created.getRideId());
        assertNotNull(retrieved, "Should retrieve created ride");
        assertEquals("Central Park", retrieved.getPickupLocation());
    }

    @Test
    void assignRiderShouldUpdateStatusAndRiderId() throws Exception {
        Ride ride = createAndSaveRide();
        
        boolean result = rideDAO.assignRider(ride.getRideId(), testRiderId);
        assertTrue(result, "Rider assignment should succeed");
        
        Ride updated = rideDAO.getRideById(ride.getRideId());
        assertEquals(testRiderId, updated.getAssignedRiderId());
        assertEquals(Status.ASSIGNED, updated.getStatus());
    }

    @Test
    void completeRideShouldUpdateStatus() throws Exception {
        Ride ride = createAndSaveRide();
        
        boolean result = rideDAO.completeRide(ride.getRideId());
        assertTrue(result, "Completion should succeed");
        
        Ride completed = rideDAO.getRideById(ride.getRideId());
        assertEquals(Status.COMPLETED, completed.getStatus());
    }

    // Additional test helper methods
    private Ride createTestRide() {
        Ride ride = new Ride();
        ride.setUserId(testUserId);
        ride.setPickupLocation("Central Park");
        ride.setDestination("Times Square");
        ride.setScheduledTime(LocalDateTime.now().plusHours(1));
        ride.setDistance(5.5);
        ride.setCost(25.0);
        ride.setSelectedVehicle(SelectedVehicle.CAR);
        return ride;
    }

    private Ride createAndSaveRide() throws Exception {
        Ride ride = createTestRide();
        rideDAO.createRide(ride);
        return ride;
    }
}