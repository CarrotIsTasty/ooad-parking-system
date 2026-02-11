/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

/**
 *
 * @author herbert
 */

import parkingapp.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    
    private DatabaseManager() {
        // Ensure database is initialized
        DatabaseConnection.getConnection();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
   
    // Load operations
//    public List<Spot> loadAllParkingSpots() {
//        List<Spot> spots = new ArrayList<>();
//        String sql = "SELECT * FROM parking_spots";
//        
//        try (Statement stmt = DatabaseConnection.getConnection().createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//            
//            while (rs.next()) {
//                int floor = rs.getInt("floor_number");
//                int row = rs.getInt("row_number");
//                int spotNum = rs.getInt("spot_number");
//                String type = rs.getString("spot_type");
//                
//                Spot spot = new Spot(floor, row, spotNum, type);
//                spot.setHourlyRate(rs.getDouble("hourly_rate"));
//                spot.setReserved(rs.getBoolean("is_reserved"));
//                
//                // Set availability
//                if (!rs.getBoolean("is_available")) {
//                    // In a real system, you'd load the vehicle too
//                }
//                
//                spots.add(spot);
//            }
//        } catch (SQLException e) {
//            System.err.println("Error loading parking spots: " + e.getMessage());
//        }
//        
//        return spots;
//    }
    
    private List<String> getCompatibleSpotTypes(VehicleType vehicleType) {
    List<String> compatibleTypes = new ArrayList<>();
    
    switch (vehicleType) {
        case BICYCLE:
        case MOTORCYCLE:
            // Motorcycles and bicycles can use COMPACT spots
            compatibleTypes.add("COMPACT");
            break;
        case CAR:
            compatibleTypes.add("COMPACT");
            compatibleTypes.add("REGULAR");
            break;
        case SUV:
            compatibleTypes.add("REGULAR");
            break;
        case TRUCK:
            compatibleTypes.add("REGULAR");
            break;
        case HANDICAPPED:
            // Handicapped vehicles might have dedicated spots or use regular ones
            compatibleTypes.add("HANDICAPPED");
            compatibleTypes.add("REGULAR");
            compatibleTypes.add("COMPACT");
            break;
        default:
            // Default to REGULAR spots for unknown types
            compatibleTypes.add("REGULAR");
    }
    
    return compatibleTypes;
}
    
    public List<Integer> getFloorsByVehicleType(VehicleType vehicleType) {
        List<Integer> floorNumbers = new ArrayList<>();
        List<String> compatibleSpotTypes = getCompatibleSpotTypes(vehicleType);
    
    if (compatibleSpotTypes.isEmpty()) {
        return floorNumbers; // Return empty list
    }
       
        
        
        StringBuilder sqlBuilder = new StringBuilder("""
        SELECT DISTINCT floor_number 
        FROM parking_spots 
        WHERE spot_type IN (
        """);
    
    for (int i = 0; i < compatibleSpotTypes.size(); i++) {
        if (i > 0) sqlBuilder.append(", ");
        sqlBuilder.append("?");
    }
    
    sqlBuilder.append("""
        ) 
        AND is_available = 1 
        ORDER BY floor_number ASC
        """);
    
        String sql = sqlBuilder.toString();
        
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
        // Set parameters for IN clause
        for (int i = 0; i < compatibleSpotTypes.size(); i++) {
            pstmt.setString(i + 1, compatibleSpotTypes.get(i));
        }
        
        ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                floorNumbers.add(rs.getInt("floor_number"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting floors by vehicle type: " + e.getMessage());
        }
        
        return floorNumbers;
    }
    
    public List<Integer> getRowsByFloorAndVehicleType(int floorNumber, VehicleType vehicleType) {
    List<Integer> rowNumbers = new ArrayList<>();
    
    // Get compatible spot types for this vehicle
    List<String> compatibleSpotTypes = getCompatibleSpotTypes(vehicleType);
    
    if (compatibleSpotTypes.isEmpty()) {
        return rowNumbers; // Return empty list
    }
    
    // Build SQL with IN clause for compatible spot types
    StringBuilder sqlBuilder = new StringBuilder("""
        SELECT DISTINCT row_number 
        FROM parking_spots 
        WHERE floor_number = ? 
          AND spot_type IN (
        """);
    
    // Add placeholders for each compatible spot type
    for (int i = 0; i < compatibleSpotTypes.size(); i++) {
        if (i > 0) sqlBuilder.append(", ");
        sqlBuilder.append("?");
    }
    
    sqlBuilder.append("""
        ) 
        AND is_available = 1 
        ORDER BY row_number ASC
        """);
    
    String sql = sqlBuilder.toString();
    
    try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
        pstmt.setInt(1, floorNumber);
        
        // Set parameters for IN clause
        for (int i = 0; i < compatibleSpotTypes.size(); i++) {
            pstmt.setString(i + 2, compatibleSpotTypes.get(i));
        }
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            rowNumbers.add(rs.getInt("row_number"));
        }
    } catch (SQLException e) {
        System.err.println("Error getting rows by floor and vehicle type: " + e.getMessage());
    }
    
    return rowNumbers;
}

    public List<Integer> getSpotsByFloorRowAndVehicleType(int floorNumber, int rowNumber, VehicleType vehicleType) {
    List<Integer> spotNumbers = new ArrayList<>();
    
    // Get compatible spot types for this vehicle
    List<String> compatibleSpotTypes = getCompatibleSpotTypes(vehicleType);
    
    if (compatibleSpotTypes.isEmpty()) {
        return spotNumbers; // Return empty list
    }
    
    // Build SQL with IN clause for compatible spot types
    StringBuilder sqlBuilder = new StringBuilder("""
        SELECT DISTINCT spot_number 
        FROM parking_spots 
        WHERE floor_number = ? 
          AND row_number = ? 
          AND spot_type IN (
        """);
    
    // Add placeholders for each compatible spot type
    for (int i = 0; i < compatibleSpotTypes.size(); i++) {
        if (i > 0) sqlBuilder.append(", ");
        sqlBuilder.append("?");
    }
    
    sqlBuilder.append("""
        ) 
        AND is_available = 1 
        ORDER BY spot_number ASC
        """);
    
    String sql = sqlBuilder.toString();
    
    try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
        pstmt.setInt(1, floorNumber);
        pstmt.setInt(2, rowNumber);
        
        // Set parameters for IN clause
        for (int i = 0; i < compatibleSpotTypes.size(); i++) {
            pstmt.setString(i + 3, compatibleSpotTypes.get(i));
        }
        
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            spotNumbers.add(rs.getInt("spot_number"));
        }
    } catch (SQLException e) {
        System.err.println("Error getting spots by floor, row and vehicle type: " + e.getMessage());
    }
    
    return spotNumbers;
}
    
    public boolean saveTicket(Ticket ticket) {
    Connection conn = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        
       
        String ticketSql = """
            INSERT INTO tickets 
            (ticket_id, license_plate, spot_id, entry_time, is_paid) 
            VALUES (?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement ticketStmt = conn.prepareStatement(ticketSql)) {
            // Set ticket parameters
            ticketStmt.setString(1, ticket.getTicketID());
            ticketStmt.setString(2, ticket.getVehicle().getPlateNumber());
            ticketStmt.setString(3, ticket.getSpot().getSpotId());
            
           
            Timestamp entryTimestamp = Timestamp.valueOf(ticket.getEntryTime());
            ticketStmt.setTimestamp(4, entryTimestamp);
            
            // Set payment status (usually false when ticket is created)
            ticketStmt.setBoolean(5, false); // Tickets are unpaid when created
            
            ticketStmt.executeUpdate();
        }
        
        // 2. Update parking spot availability to false (0)
        String spotSql = "UPDATE parking_spots SET is_available = 0 WHERE spot_id = ?";
        
        try (PreparedStatement spotStmt = conn.prepareStatement(spotSql)) {
            spotStmt.setString(1, ticket.getSpot().getSpotId());
            int spotsUpdated = spotStmt.executeUpdate();
            
            if (spotsUpdated == 0) {
                throw new SQLException("Failed to update parking spot availability");
            }
        }
        
        // 3. Commit the transaction
        conn.commit();
        return true;
        
    } catch (SQLException e) {
        // Rollback in case of error
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
            }
        }
        System.err.println("Error saving ticket: " + e.getMessage());
        e.printStackTrace();
        return false;
        
    } catch (Exception e) {
        // Rollback for any other exception
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
            }
        }
        System.err.println("Error saving ticket: " + e.getMessage());
        e.printStackTrace();
        return false;
        
    } finally {
        // Restore auto-commit mode
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error restoring auto-commit: " + e.getMessage());
            }
        }
    }
}
    
    public Ticket getTicketDetailsByPlateNumber(String plateNumber) {
    String sql = """
        SELECT ticket_id, entry_time, exit_time, license_plate, spot_id
        FROM tickets 
        WHERE license_plate = ? 
        ORDER BY entry_time DESC
        LIMIT 1
        """;
    
    try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
        pstmt.setString(1, plateNumber);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            // Create a simplified Ticket object or return a map
            String ticketId = rs.getString("ticket_id");
            LocalDateTime entryTime = rs.getTimestamp("entry_time").toLocalDateTime();
            
            // Handle possible null exit_time
            LocalDateTime exitTime = null;
            Timestamp exitTimestamp = rs.getTimestamp("exit_time");
            if (exitTimestamp != null) {
                exitTime = exitTimestamp.toLocalDateTime();
            }
            
            String plate = rs.getString("license_plate");
            String spotId = rs.getString("spot_id");
            
            // Create and return a ticket object
            return new Ticket(ticketId, entryTime, exitTime, spotId, plate);
        }
    } catch (SQLException e) {
        System.err.println("Error getting ticket details: " + e.getMessage());
    }
    
    return null;
}
    
    public String getSpotTypeByLPlate(String plateNumber) {
    String sql = """
        SELECT spot_type 
        FROM parking_spots 
        WHERE license_plate = ?
        """;
    
    try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
        pstmt.setString(1, plateNumber);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            return rs.getString("spot_type");
        }
    } catch (SQLException e) {
        System.err.println("Error getting current spot type: " + e.getMessage());
    }
    
    return null;
}
}
