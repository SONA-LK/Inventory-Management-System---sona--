package com.sona.config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sampath
 */
import com.sona.Logger.LogManager;
import java.sql.*;

public class DatabaseManager {

    private static Connection connection;

    // Configuration (Ideally, these should come from a properties file or environment variables)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shopapp";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Modayakek@1234";

    // Static block to load the driver only once.
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            LogManager.logError("Current Admin", "MySQL Driver Not Found", e);
            e.printStackTrace(); // Consider logging this error
            // Handle the error appropriately (e.g., throw a RuntimeException)
        }
    }

    // Private constructor to prevent instantiation.
    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public static ResultSet executeSearch(String query) throws SQLException {
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Error executing search query: " + query);
            throw e; // Re-throw the exception for the caller to handle
        }
    }

    public static int executeIUD(String query) throws SQLException {
        try {
            return getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Error executing IUD query: " + query);
            throw e; // Re-throw the exception for the caller to handle
        }
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}