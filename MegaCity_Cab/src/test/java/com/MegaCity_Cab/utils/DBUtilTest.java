package com.MegaCity_Cab.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

class DBUtilTest {

    @Test
    void testTestEnvironmentConnection() throws SQLException {
        // Test the H2 in-memory connection
        try (Connection conn = DBUtil.getConnection()) {
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
            assertTrue(conn.isValid(2), "Connection should be valid");
        }
    }

    @Test
    void testConnectionMetadata() throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String productName = conn.getMetaData().getDatabaseProductName();
            assertEquals("H2", productName, "Should be H2 database in test mode");
        }
    }

    @Test
    void testSQLExecution() throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             var stmt = conn.createStatement()) {
            
            // Test basic SQL execution
            assertTrue(stmt.execute("SELECT 1 + 1"));
            
            // Test table creation
            stmt.executeUpdate("CREATE TEMPORARY TABLE test (id INT)");
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM test");
            assertTrue(rs.next());
            assertEquals(0, rs.getInt(1));
        }
    }
}