package org.eclipse.db.test.common.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.db.test.common.dal.ConnectionManager;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.dal.NamedParameterStatement;
import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.common.domain.ComponentCategory;
import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ParameterItem;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptItem;
import org.eclipse.db.test.common.domain.ScriptParameter;

public class ComponentService {

	public static List<ComponentCategory> getCategories(DbServerInfo info, int parent) {
		List<ComponentCategory> categories = new ArrayList<ComponentCategory>();
		Connection conn = ConnectionManager.getConnection(info);
		String query = "select id, name from ComponentCategory";
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
				ComponentCategory sc = new ComponentCategory(id, name);
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
	
	public static List<Component> getComponents(DbServerInfo info, int parent) {
		List<Component> items = new ArrayList<Component>();
		Connection conn = ConnectionManager.getConnection(info);
		String query = "select id, name, categoryId from Component";
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
				Component sc = new Component(id, name);
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
	
	public static void addComponentCategory(DbServerInfo info, ComponentCategory category) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO ComponentCategory(name, parentId) VALUES(:name, :parentId);";
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
	
	public static void clearComponentParameters(DbServerInfo info, Component item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "delete from ComponentParameter where componentid = :id";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearComponentScripts(DbServerInfo info, Component item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "delete from ComponentScript where componentid = :id";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateComponent(DbServerInfo info, Component item) {
		clearComponentParameters(info, item);
		clearComponentScripts(info, item);
		addComponentParameters(info, item);
		addComponentScripts(info, item);
		updateComponentInternal(info, item);
	}
	
	
	private static void updateComponentInternal(DbServerInfo info, Component item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "UPDATE Component SET name = :name WHERE id = :id";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.setObject("name", item.getName());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addComponentParameters(DbServerInfo info, Component item) {
		for(ComponentParameter param : item.getParameters()) {
			addComponentParameter(info, param, item.getId());
		}
	}
	
	private static void addComponentParameter(DbServerInfo info, ComponentParameter item, int componentId) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO ComponentParameter(componentId, name, contextId) VALUES (:componentId, :name, :contextId)";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("componentId", componentId);
			stmt.setObject("name", item.getName());
			stmt.setObject("contextId", item.getContext().ordinal());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void addComponentScripts(DbServerInfo info, Component item) {
		int x = 0;
		for(ScriptItem param : item.getScripts()) {
			x++;
			addComponentScript(info, param, item.getId(), x);
		}
	}
	
	private static void addComponentScript(DbServerInfo info, ScriptItem item, int componentId, int orderid) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			item.saveParamsToMapping();
			String query = "INSERT INTO ComponentScript(componentId, scriptId, orderid, mappedParams) VALUES (:componentId, :scriptId, :orderid, :mappedParams)";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("componentId", componentId);
			stmt.setObject("orderid", orderid);
			stmt.setObject("scriptId", item.getId());
			stmt.setObject("mappedParams", item.getMappedParams());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadComponent(DbServerInfo info, Component item) {
		loadComponentParameters(info, item);
		loadComponentScripts(info, item);
	}
	
	public static void loadComponentParameters(DbServerInfo info, Component item) {
		if (info == null || item == null)
			return;
		item.getParameters().clear();
		String query = "select id, name, contextId from ComponentParameter where componentId = :id";
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
				ComponentParameter param = new ComponentParameter();
				param.setName(rs.getString("Name"));
				param.setId(rs.getInt("Id"));
				param.setContext(org.eclipse.db.test.common.domain.ParameterContext.values()[rs.getShort("contextId")]);
				item.addParameter(param);
			}
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private static void loadComponentScripts(DbServerInfo info, Component item) {
		if (info == null || item == null)
			return;
		item.getScripts().clear();
		String query = "select s.id, s.name, c.orderId from ComponentScript c inner join Script s on s.id = c.scriptId where componentId = :componentId order by c.orderid";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("componentId", item.getId());
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
				ScriptItem param = new ScriptItem();
				param.setName(rs.getString("name"));
				param.setId(rs.getInt("id"));
				param.setOrderId(rs.getInt("orderId"));
				item.getScripts().add(param);
			}
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void loadScriptItemParameters(DbServerInfo info, ScriptItem item) {
		Script s = new Script();
		s.setId(item.getId());
		ScriptService.loadScriptParameters(info, s);
		for (ScriptParameter param : s.getParameters()) {
			ParameterItem pi = new ParameterItem();
			pi.setMapped(param.getName());
			item.addParameter(pi);
		}
	}
	
	public static void loadScriptMappedParams(DbServerInfo info, ScriptItem item, int componentId) {
		if (info == null || item == null)
			return;
		String query = "select c.mappedParams from ComponentScript c where componentId = :componentId and scriptId = :scriptId and orderId = :orderId";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("componentId", componentId);
			stmt.setObject("scriptId", item.getId());
			stmt.setObject("orderId", item.getOrderId());
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
			if (rs.next()) {
				item.setMappedParams(rs.getString("mappedParams"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createComponent(DbServerInfo info, Component item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO Component(name, categoryId) VALUES(:name, :categoryId)";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query, true);
			stmt.setObject("name", item.getName());
			Object parent = item.getCategoryId() == 0 ? null : item.getCategoryId();
			stmt.setObject("categoryId", parent);
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

	public static void deleteComponent(DbServerInfo info, Component item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			String query = "exec DeleteComponent :id";
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
