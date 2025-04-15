package com.sona.operations;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JakartaMailSender {

    public static void sendEmail(String to, String subject, String body, String from, String password) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "mail.sona.lk"); // Your SMTP server
        properties.put("mail.smtp.port", "465"); // Use 465 for SSL or 587 for TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS
        properties.put("mail.smtp.ssl.enable", "true"); // Enable SSL

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("✅ Email sent successfully.");
        } catch (MessagingException mex) {
            Logger.getLogger(JakartaMailSender.class.getName()).log(Level.SEVERE, "❌ Failed to send email", mex);
            throw mex;
        }
    }

    public static void main(String[] args) {
        String to = "isurusmpth22@gmail.com";
        String subject = "Test Email from Jakarta Mail";
        String body = "This is a test email sent using Jakarta Mail.";
        String from = "info.erp@sona.lk";

        // Securely retrieve the password.
        String password = getEmailPassword(); // Replace with a secure method

        if (password == null) {
            System.err.println("Error: Email password not found.");
            return;
        }

        try {
            sendEmail(to, subject, body, from, password);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    private static String getEmailPassword() {
        return "Erpsona@1234"; // Replace with your actual password
    }
}
