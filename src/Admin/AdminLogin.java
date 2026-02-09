package Admin;

import java.sql.*;
import java.time.LocalDateTime;

public class AdminLogin {
    private Connection connection;
    private String currentAdminName;
    
    public AdminLogin(Connection connection) {
        this.connection = connection;
    }
    
    // For GUI login
    public boolean login(String adminName, String password) {
        try {
            String hashedPassword = hashPassword(password);
            String query = "SELECT * FROM administrator WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, adminName);
            stmt.setString(2, hashedPassword);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentAdminName = adminName;
                updateLastLogin(adminName);
                rs.close();
                stmt.close();
                return true;
            }
            rs.close();
            stmt.close();
            return false;
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }
    
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            return password;
        }
    }
    
    private void updateLastLogin(String adminName) {
        try {
            String query = "UPDATE administrator SET lastLoginDate = ? WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, LocalDateTime.now().toString());
            stmt.setString(2, adminName);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error updating last login: " + e.getMessage());
        }
    }
}