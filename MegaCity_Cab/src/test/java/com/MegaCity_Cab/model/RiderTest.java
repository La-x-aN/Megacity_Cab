package com.MegaCity_Cab.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RiderTest {

    @Test
    void testRiderSettersAndGetters() {
        Rider rider = new Rider();

        // Set values
        rider.setRiderId(1);
        rider.setUserId(101);
        rider.setVehicleType("Car");
        rider.setVehicleModel("Toyota Prius");
        rider.setVehicleNumber("ABC-1234");
        rider.setPhone("0712345678");

        // Validate values
        assertEquals(1, rider.getRiderId(), "Rider ID should be 1");
        assertEquals(101, rider.getUserId(), "User ID should be 101");
        assertEquals("Car", rider.getVehicleType(), "Vehicle type should be 'Car'");
        assertEquals("Toyota Prius", rider.getVehicleModel(), "Vehicle model should be 'Toyota Prius'");
        assertEquals("ABC-1234", rider.getVehicleNumber(), "Vehicle number should be 'ABC-1234'");
        assertEquals("0712345678", rider.getPhone(), "Phone number should be '0712345678'");
    }

    @Test
    void testDefaultValues() {
        Rider rider = new Rider();

        assertEquals(0, rider.getRiderId(), "Default Rider ID should be 0");
        assertEquals(0, rider.getUserId(), "Default User ID should be 0");
        assertNull(rider.getVehicleType(), "Default Vehicle Type should be null");
        assertNull(rider.getVehicleModel(), "Default Vehicle Model should be null");
        assertNull(rider.getVehicleNumber(), "Default Vehicle Number should be null");
        assertNull(rider.getPhone(), "Default Phone should be null");
    }
}

