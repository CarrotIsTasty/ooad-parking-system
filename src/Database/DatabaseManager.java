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
    
   
}
