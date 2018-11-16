/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.DatabaseManager;


public abstract class AbstractRDG {
	private int id;
	private transient int version;
	
	
	public AbstractRDG(int id) {
		this.id = id;
		this.version = 1;
	}
	
	public AbstractRDG(int id, int version) {
		this.id = id;
		this.version = version;
	}
	
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
	
	public synchronized static int getNextID(String tableName, int nextID) {
		if (nextID == 0) {
			Connection conn = DatabaseManager.getConnection();
			String query = "SELECT id FROM " + tableName + " ORDER BY id DESC LIMIT 1;";
			
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery(query);
				
				while (rs.next()) {
		            nextID = rs.getInt("id");
				}
				
				ps.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return ++nextID;
	}
	
	
	
	/*	Getters and Setters	*/
	
	public int getId() {
		return id;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public void incrementVersion() {
		version++;
	}
}
