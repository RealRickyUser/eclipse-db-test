package org.eclipse.db.test.common.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class ConnectionManager {
	
	/*private static void configure() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID + "/sqlserver");
		if (prefs == null)
			return;
		String[] keys = null;
		try {
			keys = prefs.keys();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		for (String key : keys) {
			String val = prefs.get(key, "");
		}
	}
	*/
	/*public static Connection getConnection() {
		DbServerInfo info = new DbServerInfo();
		info.setServer("localhost");
		info.setInstance("sql2008r2");
		info.setDatabase("TestDb");
		info.setUser("testuser");
		info.setPassword("testpass");
		return getConnection(info);
	}*/
	
	public static Connection getConnection(DbServerInfo info) {
		return org.eclipse.db.test.dbadapter.mssql.
				ConnctionManager.
				getConnection(info.getServer(),
						info.getInstance(),
						info.getPort(),
						info.getDatabase(),
						info.getUser(),
						info.getPassword());
	}
	
	public static String checkConnection(DbServerInfo info) {
		try {
			String res = org.eclipse.db.test.dbadapter.mssql.
					ConnctionManager.
					checkConnection(info.getServer(),
							info.getInstance(),
							info.getPort(),
							info.getDatabase(),
							info.getUser(),
							info.getPassword());
			if (res != null)
				return res;
			Connection conn = getConnection(info);
			if (conn == null)
				return "Подключение не создано";
			PreparedStatement stmt = conn.prepareStatement("select getdate() as OpDate");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				@SuppressWarnings("unused")
				Date val = rs.getDate("OpDate");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		return null;
	}
}
