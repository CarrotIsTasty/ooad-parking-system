package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:parking_app.db";
    private static Connection connection = null;
    
    public static synchronized Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Connected to SQLite database");
                //createTables();
            } catch (SQLException e) {
                System.err.println("Error connecting to database: " + e.getMessage());
            }
        }
        return connection;
    }
    
        public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
        
        private static void createTables() {
        String[] createTableSQLs = {
            """
            CREATE TABLE IF NOT EXISTS parking_spots (
                spot_id INTEGER PRIMARY KEY AUTOINCREMENT,
                floor_number INTEGER NOT NULL,
                row_number INTEGER NOT NULL,
                spot_number INTEGER NOT NULL,
                spot_type TEXT NOT NULL CHECK(spot_type IN ('COMPACT', 'REGULAR', 'RESERVED')),
                hourly_rate REAL NOT NULL,
                is_available BOOLEAN NOT NULL DEFAULT 1,
                current_vehicle_plate TEXT DEFAULT NULL,
                UNIQUE(floor_number, row_number, spot_number)
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS administrator (
                adminID INTEGER PRIMARY KEY AUTOINCREMENT,
                username VARCHAR(50) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                lastLoginDate DATETIME,   
            )
            """
        };
        
         try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Execute each CREATE TABLE statement
            for (String sql : createTableSQLs) {
                stmt.execute(sql);
            }
            
            System.out.println("Database tables created successfully");
            
            // Insert default fine strategy setting
            String insertDefaultSettings = """
                INSERT OR IGNORE INTO fine_settings 
                (fine_strategy, fixed_amount, progressive_24_48, progressive_48_72, progressive_above_72, hourly_rate, is_active)
                VALUES ('FIXED', 50.0, 100.0, 150.0, 200.0, 20.0, 1)
                """;
            stmt.execute(insertDefaultSettings);
            
            System.out.println("Default fine settings initialized");
            
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
        
}