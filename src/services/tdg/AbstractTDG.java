/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import services.DatabaseManager;


public abstract class AbstractTDG {
	
	protected static void createTable(String tableName, String query) {
    	Connection connection = DatabaseManager.getConnection();
    	
    	try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			
			System.out.println("Created the table '" + tableName + "' successfully");
		}
    	catch (SQLException e) {
			System.out.println("Failed to create the table '" + tableName + "'.\n" + e.getMessage());
		}
    	finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    protected static void truncateTable(String tableName) {
    	String query = "TRUNCATE TABLE " + tableName;
    	Connection connection = DatabaseManager.getConnection();
    	
    	try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			
			System.out.println("Truncated the table '" + tableName + "' successfully");
		}
    	catch (SQLException e) {
			System.out.println("Failed to truncate the table '" + tableName + "'.\n" + e.getMessage());
		}
    	finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    protected static void dropTable(String tableName) {
    	String query = "DROP TABLE IF EXISTS " + tableName;
    	Connection connection = DatabaseManager.getConnection();
    	
    	try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			
			System.out.println("Dropped the table '" + tableName + "' successfully");
		}
    	catch (SQLException e) {
			System.out.println("Failed to drop the table '" + tableName + "'.\n" + e.getMessage());
		}
    	finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
	
//	public synchronized static int getNextID(String tableName, int nextID) {
//		if (nextID == 0) {
//			Connection conn = DatabaseManager.getConnection();
//			String query = "SELECT id FROM " + tableName + " ORDER BY id DESC LIMIT 1;";
//			
//			try {
//				PreparedStatement ps = conn.prepareStatement(query);
//				ResultSet rs = ps.executeQuery(query);
//				
//				while (rs.next()) {
//		            nextID = rs.getInt("id");
//				}
//				
//				ps.close();
//			}
//			catch (SQLException e) {
//				e.printStackTrace();
//			}
//			finally {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		}
//		
//		return ++nextID;
//	}
}
