package org.eclipse.db.test.dbadapter.mssql;

import java.sql.Connection;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class ConnctionManager {
	
	public static Connection getConnection(String server, String instance, int port, String database, String user, String password) {
		Connection con = null;
		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(user);
		ds.setPassword(password);
		ds.setServerName(server);
		ds.setInstanceName(instance);
		if (port > 0)
			ds.setPortNumber(port); 
		ds.setDatabaseName(database);
		try {
			con = ds.getConnection();
		} catch (SQLServerException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static String checkConnection(String server, String instance, int port, String database, String user, String password) {
		Connection con = null;
		SQLServerDataSource ds = new SQLServerDataSource();
		ds.setUser(user);
		ds.setPassword(password);
		ds.setServerName(server);
		ds.setInstanceName(instance);
		if (port > 0)
			ds.setPortNumber(port); 
		ds.setDatabaseName(database);
		try {
			con = ds.getConnection();
			con.close();
		} catch (SQLException e) {
			return e.getMessage();
		}
		return null;
	}

}
