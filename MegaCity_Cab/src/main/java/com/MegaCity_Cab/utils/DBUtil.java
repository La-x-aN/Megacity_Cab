package com.MegaCity_Cab.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static DataSource dataSource;
    private static boolean isTestEnvironment = false;

    static {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/megacitydb");
        } catch (NamingException e) {
            // Fallback to test configuration
            isTestEnvironment = true;
            initializeTestDataSource();
        }
    }

    private static void initializeTestDataSource() {
        try {
            // Explicitly load H2 driver
            Class.forName("org.h2.Driver");
            // Create test connection to verify
            try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")) {
                // Test connection successful
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize test database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (isTestEnvironment) {
            return DriverManager.getConnection(
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;" +
                "MODE=MySQL;DATABASE_TO_UPPER=FALSE;" +
                "USER=sa;PASSWORD=;"
            );
        }
        return dataSource.getConnection();
    }
}