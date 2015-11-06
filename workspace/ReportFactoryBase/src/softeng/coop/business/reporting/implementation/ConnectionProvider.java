package softeng.coop.business.reporting.implementation;

import java.util.*;

import java.sql.*;

import com.mchange.v2.c3p0.*;

/**
 * Provides JDBC connections from a pool.
 * Uses C3P0 library for connection pooling.
 */
class ConnectionProvider
{
	private static ResourceBundle resources = openResources();
	
	private static ComboPooledDataSource dataSource = createDataSource();

	private static ComboPooledDataSource createDataSource()
	{
		try
		{
			ComboPooledDataSource source = new ComboPooledDataSource();
			
			source.setDriverClass(resources.getString("DRIVER_CLASS"));
			source.setJdbcUrl(resources.getString("JDBC_URL"));
			source.setUser(resources.getString("USER"));
			source.setPassword(resources.getString("PASSWORD"));
			
			return source; 
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Cannot set up the connection pool for reporting engine", ex);
		}
	}

	private static ResourceBundle openResources()
	{
		return ResourceBundle.getBundle("Reporting");
	}
	
	/**
	 * Acquire an open connection from the pool.
	 * @throws SQLException
	 */
	public static Connection acquireConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
}
