package org.eclipse.db.test.common.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.db.test.common.dal.ConnectionManager;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.dal.NamedParameterStatement;
import org.eclipse.db.test.common.domain.ParameterDirection;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptExecutionResult;
import org.eclipse.db.test.common.domain.ScriptParameter;

public class ExecutionService {
	
	public static ScriptExecutionResult execute(DbServerInfo info, Script script) {
		switch (script.getType()) {
			case Action:
				return executeActionInternal(info, script);
			case State:
				return executeStateInternal(info, script);
		}
		return null;
	}
	
	private static ScriptExecutionResult executeActionInternal(DbServerInfo info, Script script) {
		return executeInternal(info, script);
	}
	
	private static ScriptExecutionResult executeStateInternal(DbServerInfo info, Script script) {
		return executeInternal(info, script);
	}
	
	
	private static ScriptExecutionResult executeInternal(DbServerInfo info, Script script) {
		ScriptExecutionResult rc = new ScriptExecutionResult();
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			
			stmt = new NamedParameterStatement(conn, script.getText());
			for (ScriptParameter param : script.getParameters()) {
				if (param.getDirection() == ParameterDirection.Out)
					continue;
				stmt.setObject(param.getName(), param.getValue());
			}
			Map<String, Object> result = new HashMap<>();
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ResultSetMetaData rsm = rs.getMetaData();
				int count = rsm.getColumnCount();
				for(int x = 1; x <= count; x++) {
					String column = rsm.getColumnLabel(x);
					result.put(column, rs.getObject(column));
				}
			}
			if (result.size() > 0) {
				for (ScriptParameter param : script.getParameters()) {
					if (param.getDirection() == ParameterDirection.In)
						continue;
					String name = ":" + param.getName();
					if (result.containsKey(name)) {
						Object value = result.get(name);
						param.setValue(value);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			rc.setException(e);
			return rc;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				rc.setException(e);
				return rc;
			}
		}
		return rc;
	}

}
