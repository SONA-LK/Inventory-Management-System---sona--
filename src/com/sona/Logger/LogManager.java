/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sona.Logger;

/**
 *
 * @author Sampath
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {

    private static final String INFO_LOG_FILE = "info.log";
    private static final String ERROR_LOG_FILE = "error.log";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void logInfo(String user, String message) {
        log(INFO_LOG_FILE, "INFO", user, message);
    }

    public static void logError(String user, String message, Exception exception) {
        log(ERROR_LOG_FILE, "ERROR", user, message + " - " + exception.getMessage());
        exception.printStackTrace(); // Also print to console for debugging
    }

    private static void log(String fileName, String level, String user, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String timestamp = DATE_FORMAT.format(new Date());
            String logEntry = String.format("[%s] [%s] [%s] %s%n", timestamp, level, user, message);
            writer.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace(); // Log to console if logging fails
        }
    }
}
