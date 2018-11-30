/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package services;

import java.sql.Connection;
import java.sql.SQLException;

import org.dsrg.soenea.service.threadLocal.DbRegistry;

public class DatabaseManager {
    public static Connection getConnection() {
    	Connection connection = null;
		try {
			connection = DbRegistry.getDbConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return connection;
    }
}
