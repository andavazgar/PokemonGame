/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Source: https://stackoverflow.com/a/10916633
 */
public abstract class DatabaseManager {
	private static String url = "jdbc:mysql://localhost:3306/andavazgar?serverTimezone=UTC";
	private static String driverName = "com.mysql.cj.jdbc.Driver";   
    private static String username = "andavazgar";   
    private static String password = "usentist";
    private static Connection connection;
    
    
    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            
            try {
                connection = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException ex) {
                System.out.println("Failed to create the database connection."); 
            }
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Database driver not found."); 
        }
        return connection;
    }
}
