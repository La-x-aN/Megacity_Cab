package com.MegaCity_Cab.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DBUtil {
	private static DataSource dataSource;
 
 static {
     try {
         Context ctx = new InitialContext();
         dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/megacitydb");
     } catch (NamingException e) {
         throw new RuntimeException("DB configuration error", e);
     }
 }

 public static Connection getConnection() throws SQLException {
     return dataSource.getConnection();
 }
}