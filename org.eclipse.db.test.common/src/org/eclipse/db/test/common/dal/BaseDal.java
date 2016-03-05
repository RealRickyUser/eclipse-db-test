package org.eclipse.db.test.common.dal;

import java.sql.Connection;

public class BaseDal {
	
	private DbServerInfo _info;
	
	public BaseDal(DbServerInfo info) {
		_info =info;
	}
	
	/**
	 * Returns new database connection
	 * @return new database connection
	 * @see java.sql.Connection
	 */
	protected Connection getConnection() {
		return ConnectionManager.getConnection(_info);
	}

}
