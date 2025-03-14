package com.MegaCity_Cab.dao;

import com.MegaCity_Cab.model.Rider;
import com.MegaCity_Cab.utils.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RiderDAOTest {
    private RiderDAO riderDAO;
    private int testUserId;

    @BeforeEach
    void setUp() throws Exception {
        riderDAO = new RiderDAO();
        
        // Setup test database
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Clean previous data
            stmt.executeUpdate("DROP ALL OBJECTS");
            
            // Create tables
            stmt.executeUpdate("CREATE TABLE users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100) UNIQUE)");
            
            stmt.executeUpdate("CREATE TABLE riders (" +
                    "rider_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_id INT NOT NULL, " +
                    "vehicle_type VARCHAR(50) NOT NULL, " +
                    "vehicle_model VARCHAR(100) NOT NULL, " +
                    "vehicle_number VARCHAR(20) NOT NULL UNIQUE, " +
                    "phone VARCHAR(20) NOT NULL, " +
                    "is_available BOOLEAN DEFAULT TRUE)");

            // Insert test user
            stmt.executeUpdate("INSERT INTO users (name, email) VALUES ('Test User', 'test@example.com')");
            
            // Get generated user ID
            var rs = stmt.executeQuery("SELECT id FROM users WHERE email = 'test@example.com'");
            rs.next();
            testUserId = rs.getInt(1);
        }
    }

    @Test
    void createRider_ShouldReturnTrue_WhenValidRider() throws Exception {
        Rider rider = createTestRider();
        boolean result = riderDAO.createRider(rider);
        assertTrue(result);
    }

    @Test
    void findByUserId_ShouldReturnRider_WhenExists() throws Exception {
        Rider rider = createTestRider();
        riderDAO.createRider(rider);
        
        Rider found = riderDAO.findByUserId(testUserId);
        assertNotNull(found);
        assertEquals("Motorcycle", found.getVehicleType());
    }

    @Test
    void getAllRiders_ShouldReturnAllRiders() throws Exception {
        riderDAO.createRider(createTestRider());
        riderDAO.createRider(createTestRider2());
        
        List<Rider> riders = riderDAO.getAllRiders();
        assertEquals(2, riders.size());
    }

    @Test
    void getAllAvailableRiders_ShouldFilterUnavailable() throws Exception {
        // Create available rider
        riderDAO.createRider(createTestRider());
        
        // Create unavailable rider using direct SQL
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO riders (user_id, vehicle_type, vehicle_model, vehicle_number, phone, is_available) " +
                    "VALUES (" + testUserId + ", 'Car', 'Sedan', 'C123', '555-0002', false)");
        }
        
        List<Rider> availableRiders = riderDAO.getAllAvailableRiders();
        assertEquals(1, availableRiders.size());
        assertEquals("Motorcycle", availableRiders.get(0).getVehicleType());
    }

    @Test
    void getRiderById_ShouldReturnCorrectRider() throws Exception {
        riderDAO.createRider(createTestRider());
        Rider created = riderDAO.findByUserId(testUserId);
        
        Rider found = riderDAO.getRiderById(created.getRiderId());
        assertNotNull(found);
        assertEquals("M-100", found.getVehicleModel());
    }

    @Test
    void findByUserId_ShouldReturnNull_WhenNotFound() throws Exception {
        Rider found = riderDAO.findByUserId(999);
        assertNull(found);
    }

    private Rider createTestRider() {
        Rider rider = new Rider();
        rider.setUserId(testUserId);
        rider.setVehicleType("Motorcycle");
        rider.setVehicleModel("M-100");
        rider.setVehicleNumber("MC-123");
        rider.setPhone("555-1234");
        return rider;
    }

    private Rider createTestRider2() {
        Rider rider = new Rider();
        rider.setUserId(testUserId);
        rider.setVehicleType("Car");
        rider.setVehicleModel("C-200");
        rider.setVehicleNumber("CAR-456");
        rider.setPhone("555-5678");
        return rider;
    }
}