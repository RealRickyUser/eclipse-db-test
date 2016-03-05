package org.eclipse.db.test.common.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.db.test.common.dal.ConnectionManager;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.dal.NamedParameterStatement;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptCategory;
import org.eclipse.db.test.common.domain.ScriptParameter;
import org.eclipse.db.test.common.domain.ScriptType;

public class ScriptService {

	public static List<ScriptCategory> getCategories(DbServerInfo info, int parent) {
		List<ScriptCategory> categories = new ArrayList<ScriptCategory>();
		Connection conn = ConnectionManager.getConnection(info);
		String query = "select id, name from ScriptCategory";
		if (parent > 0)
			query += " where parentid = :parent";
		else
			query += " where parentid is null";
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
		} catch (SQLException e) {
			e.printStackTrace();
			return categories;
		}
		if (parent > 0) {
			try {
				stmt.setObject("parent", parent);
			} catch (SQLException e) {
				e.printStackTrace();
				return categories;
			}
		}
		
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return categories;
		}
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				ScriptCategory sc = new ScriptCategory(id, name);
				sc.setParentId(parent);
				categories.add(sc);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return categories;
		}
		
		return categories;
	}
	
	public static List<Script> getScripts(DbServerInfo info, int parent) {
		List<Script> items = new ArrayList<Script>();
		Connection conn = ConnectionManager.getConnection(info);
		String query = "select id, name, categoryId from Script";
		if (parent > 0)
			query += " where CategoryID = :parent";
		else
			query += " where CategoryID is null";
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
		} catch (SQLException e) {
			e.printStackTrace();
			return items;
		}
		if (parent > 0) {
			try {
				stmt.setObject("parent", parent);
			} catch (SQLException e) {
				e.printStackTrace();
				return items;
			}
		}
		
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return items;
		}
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Script sc = new Script(id, name);
				sc.setCategoryId(rs.getInt("categoryId"));
				items.add(sc);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return items;
		}
		
		return items;
	}
	
	public static void loadScript(DbServerInfo info, Script item) {
		if (info == null || item == null)
			return;
		String query = "select text, type, comment, stateparam, stateParamValue from script where id = :id";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			if (!rs.next())
				return;
			item.setText(rs.getString("text"));
			item.setType(ScriptType.values()[rs.getShort("type")]);
			item.setComment(rs.getString("comment"));
			item.setStateParam(rs.getString("stateparam"));
			item.setStateValue(rs.getString("stateParamValue"));
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void loadScriptParameters(DbServerInfo info, Script item) {
		if (info == null || item == null)
			return;
		String query = "select Id, ScriptId, Name, DirectionId,  ParamType, ParamSize from ScriptParameter where ScriptId = :id";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			while (rs.next()) {
				ScriptParameter param = new ScriptParameter();
				param.setName(rs.getString("Name"));
				param.setId(rs.getInt("Id"));
				param.setScriptId(rs.getInt("ScriptId"));
				param.setType(rs.getString("ParamType"));
				param.setSize(rs.getString("ParamSize"));
				param.setDirection(org.eclipse.db.test.common.domain.ParameterDirection.values()[rs.getShort("DirectionId")]);
				item.addParameter(param);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void saveScript(DbServerInfo info, Script item) {
		saveScriptInternal(info, item);
		saveScriptParametersInternal(info, item);
	}
	
	private static void saveScriptInternal(DbServerInfo info, Script item) {
		String query = "UPDATE Script SET name = :name, categoryId = :categoryId, comment = :comment, text = :text, type = :type, StateParam = :stateParam, StateParamValue = :paramValue WHERE id = :id";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.setObject("name", item.getName());
			if (item.getCategoryId() > 0)
				stmt.setObject("categoryId", item.getCategoryId());
			else
				stmt.setObject("categoryId", null);
			stmt.setObject("comment", item.getComment());
			stmt.setObject("text", item.getText());
			stmt.setObject("type", item.getType().ordinal());
			stmt.setObject("stateParam", item.getType() == ScriptType.State ? item.getStateParam() : null);
			stmt.setObject("paramValue", item.getType() == ScriptType.State ? item.getStateValue() : null);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void saveScriptParametersInternal(DbServerInfo info, Script item) {
		deleteScriptParameters(info, item);
		for (ScriptParameter param : item.getParameters()) {
			param.setScriptId(item.getId());
			addScriptParameter(info, param);
		}
	}
	
	private static void deleteScriptParameters(DbServerInfo info, Script item) {
		String query = "DELETE FROM ScriptParameter WHERE ScriptId = :id";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void addScriptParameter(DbServerInfo info, ScriptParameter item) {
		String query = "INSERT INTO ScriptParameter(ScriptId, DirectionId, Name, ParamType, ParamSize) " +
				"VALUES(:ScriptId, :DirectionId, :Name, :ParamType, :ParamSize)";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("ScriptId", item.getScriptId());
			stmt.setObject("DirectionId", item.getDirection().ordinal());
			stmt.setObject("Name", item.getName());
			stmt.setObject("ParamType", item.getType());
			stmt.setObject("ParamSize", item.getSize());
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addScriptCategory(DbServerInfo info, ScriptCategory category) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO ScriptCategory(name, parentId) VALUES(:name, :parentId);";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query, true);
			stmt.setObject("name", category.getName());
			Object parent = category.getParentId() == 0 ? null : category.getParentId();
			stmt.setObject("parentId", parent);
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getStatement().getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				category.setId(id);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addScript(DbServerInfo info, Script item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO Script(name, categoryId, type) VALUES(:name, :categoryId, :type);";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query, true);
			stmt.setObject("name", item.getName());
			Object parent = item.getCategoryId() == 0 ? null : item.getCategoryId();
			stmt.setObject("categoryId", parent);
			stmt.setObject("type", item.getType().ordinal());
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getStatement().getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				item.setId(id);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteScript(DbServerInfo info, Script item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "exec DeleteScript :id";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
} 
