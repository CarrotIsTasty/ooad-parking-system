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
        
}