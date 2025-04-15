/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sona.operations;

import com.sona.Logger.LogManager;
import com.sona.config.DatabaseManager;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.sona.store.LoggedUser;
import java.util.Date;

/**
 *
 * @author Sampath
 */
public class LogInProcess {

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
    private static final String EMAIL_EMPTY_MSG = "Please enter your email.";
    private static final String EMAIL_INVALID_MSG = "Invalid email format.";
    private static final String PASSWORD_EMPTY_MSG = "Please enter your password.";
    private static final String LOGIN_FAILED_MSG = "Invalid email or password.";

    public void handleLogin(JFrame frame, JTextField emailField, JPasswordField passwordField) {
        String email = emailField.getText();
        char[] passwordChars = passwordField.getPassword();

        if (!validateEmail(email)) {
            JOptionPane.showMessageDialog(frame, email.isEmpty() ? EMAIL_EMPTY_MSG : EMAIL_INVALID_MSG, "Warning", JOptionPane.WARNING_MESSAGE);
            Arrays.fill(passwordChars, '0');
            return;
        }

        if (passwordChars.length == 0) {
            JOptionPane.showMessageDialog(frame, PASSWORD_EMPTY_MSG, "Warning", JOptionPane.WARNING_MESSAGE);
            Arrays.fill(passwordChars, '0');
            return;
        }

        String password = new String(passwordChars);
        Arrays.fill(passwordChars, '0'); // Clear password

        try {
            if (performLogin(frame, email, password)) {
                LogManager.logInfo(email, " User Logged Succesfully , System Authorized Credentials");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, LOGIN_FAILED_MSG, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            LogManager.logError(email, "Perform Login Not Success. Mostly Database Error!", e);
            JOptionPane.showMessageDialog(frame, "Database error. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean performLogin(JFrame frame, String email, String password) throws SQLException {
        String query = "SELECT * FROM employee WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = DatabaseManager.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String fName = resultSet.getString("first_name");
                    String lName = resultSet.getString("last_name");
//                    Home home = new Home(email, fName, lName);
//                    home.setVisible(true);
                    LoggedUser data = new LoggedUser();
                    data.setEmail(email);
                    data.setfName(fName);
                    data.setlName(lName);
                    return true;
                }
                return false;
            }
        }
    }

}
