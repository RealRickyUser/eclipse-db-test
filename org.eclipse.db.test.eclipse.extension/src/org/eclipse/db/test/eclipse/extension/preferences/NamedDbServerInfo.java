package org.eclipse.db.test.eclipse.extension.preferences;

import org.eclipse.db.test.common.dal.DbServerInfo;

public class NamedDbServerInfo extends DbServerInfo {
	
	private String _name;
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}

	public static NamedDbServerInfo fromString(String value) {
		NamedDbServerInfo info = new NamedDbServerInfo();
		int delim = value.indexOf("=");
		if (delim == -1)
			return null;
		String alias = value.substring(0, delim);
		String srvInfo = value.substring(delim + 1, value.length());
		info.setName(alias);
		DbServerInfo sinfo = DbServerInfo.fromString(srvInfo);
		info.setServer(sinfo.getServer());
		info.setInstance(sinfo.getInstance());
		info.setPort(sinfo.getPort());
		info.setDatabase(sinfo.getDatabase());
		info.setUser(sinfo.getUser());
		info.setPassword(sinfo.getPassword());
		return info;
	}
	
	@Override
	public String toString() {
		return _name + "=" + super.toString();
		
	}
	
}
