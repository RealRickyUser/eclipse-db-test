package org.eclipse.db.test.common.dal;

public class DbServerInfo {
	private String _server;
	private String _instance;
	private int _port;
	private String _database;
	private String _user;
	private String _password;
	
	public String getServer() {
		return _server;
	}
	public void setServer(String server) {
		_server = server;
	}
	public String getInstance() {
		return _instance;
	}
	public void setInstance(String instance) {
		_instance = instance;
	}
	public int getPort() {
		return _port;
	}
	public void setPort(int port) {
		_port = port;
	}
	public String getDatabase() {
		return _database;
	}
	public void setDatabase(String database) {
		_database = database;
	}
	public String getUser() {
		return _user;
	}
	public void setUser(String user) {
		_user = user;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		_password = password;
	}
	
	@Override
	public String toString() {
		return String.format("%s|%s|%d|%s|%s", _server, _instance, _port, _database, _user);
	}
	
	public static DbServerInfo fromString(String str) {
		if (str == null)
			return null;
		DbServerInfo info = new DbServerInfo();
		String[] params = str.split("\\|");
		if (params.length != 5)
			return null;
		info.setServer(params[0]);
		info.setInstance(params[1]);
		int port = 0;
		try {
			port = Integer.parseInt(params[2]);
			info.setPort(port);
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		info.setDatabase(params[3]);
		info.setUser(params[4]);
		return info;
	}
}
