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
import org.eclipse.db.test.common.domain.ComponentItem;
import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ComponentParameterItem;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.common.domain.Test;

public class TestService {
	
	public static List<Test> getTests(DbServerInfo info, int parent) {
		List<Test> items = new ArrayList<Test>();
		Connection conn = ConnectionManager.getConnection(info);
		String query = "select id, name, categoryId from Test";
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
				Test sc = new Test(id, name);
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
	
	
	public static void clearTestComponents(DbServerInfo info, Test item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "delete from TestComponent where testId = :id";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void clearTestParameters(DbServerInfo info, Test item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "delete from TestParameter where testId = :id";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateTest(DbServerInfo info, Test item) {
		clearTestParameters(info, item);
		clearTestComponents(info, item);
		addTestParameters(info, item);
		addTestComponents(info, item);
		updateComponentInternal(info, item);
	}
	
	
	private static void updateComponentInternal(DbServerInfo info, Test item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "UPDATE Test SET name = :name WHERE id = :id";
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
	
	private static void addTestParameters(DbServerInfo info, Test item) {
		for(ComponentParameter param : item.getParameters()) {
			addTestParameter(info, param, item.getId());
		}
	}
	
	private static void addTestParameter(DbServerInfo info, ComponentParameter item, int testId) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO TestParameter(testId, name, contextId) VALUES (:testId, :name, :contextId)";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("testId", testId);
			stmt.setObject("name", item.getName());
			stmt.setObject("contextId", item.getContext().ordinal());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void addTestComponents(DbServerInfo info, Test item) {
		int x = 0;
		for(ComponentItem param : item.getComponents()) {
			x++;
			addTestComponent(info, param, item.getId(), x);
		}
	}
	
	private static void addTestComponent(DbServerInfo info, ComponentItem item, int testId, int orderid) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			item.saveParamsToMapping();
			String query = "INSERT INTO TestComponent(testId, componentId, orderid, mappedParams) VALUES (:testId, :componentId, :orderid, :mappedParams)";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("testId", testId);
			stmt.setObject("orderid", orderid);
			stmt.setObject("componentId", item.getId());
			stmt.setObject("mappedParams", item.getMappedParams());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadTest(DbServerInfo info, Test item) {
		loadTestInternal(info, item);
		loadTestComponents(info, item);
	}
	
	private static void loadTestInternal(DbServerInfo info, Test item) {
		if (info == null || item == null)
			return;
		item.getParameters().clear();
		String query = "select id, name, contextId from TestParameter where testId = :id";
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
	
	
	public static void loadTestComponentParameters(DbServerInfo info, ComponentItem item) {
		if (info == null || item == null)
			return;
		item.getParameters().clear();
		String query = "select name, contextId from ComponentParameter where componentId = :id";
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
				ComponentParameterItem param = new ComponentParameterItem();
				param.setMapped(rs.getString("Name"));
				param.setMappedContext(org.eclipse.db.test.common.domain.ParameterContext.values()[rs.getShort("contextId")]);
				if (param.getMappedContext() == ParameterContext.Local)
					continue;
				item.getParameters().add(param);
			}
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private static void loadTestComponents(DbServerInfo info, Test item) {
		if (info == null || item == null)
			return;
		item.getComponents().clear();
		String query = "select s.id, s.name, c.orderId from TestComponent c inner join Component s on s.id = c.componentId where c.testId = :testId order by c.orderid";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("testId", item.getId());
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
				ComponentItem param = new ComponentItem();
				param.setName(rs.getString("name"));
				param.setId(rs.getInt("id"));
				param.setOrderId(rs.getInt("orderId"));
				item.getComponents().add(param);
			}
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	

	public static void createTest(DbServerInfo info, Test item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO Test(name, categoryId) VALUES(:name, :categoryId)";
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

	public static void deleteTest(DbServerInfo info, Test item) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			String query = "exec DeleteTest :id";
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", item.getId());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadComponentMappedParams(DbServerInfo info, ComponentItem item, int testId) {
		if (info == null || item == null)
			return;
		String query = "select c.mappedParams from [TestComponent] c where componentId = :componentId and testId = :testId and orderId = :orderId";
		Connection conn = ConnectionManager.getConnection(info);
		NamedParameterStatement stmt = null;
		try {
			stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("testId", testId);
			stmt.setObject("componentId", item.getId());
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


	public static void loadComponentItemParameters(DbServerInfo _info, ComponentItem item) {
		Component s = new Component();
		s.setId(item.getId());
		ComponentService.addComponentParameters(_info, s);
		for (ComponentParameter param : s.getParameters()) {
			ComponentParameterItem pi = new ComponentParameterItem();
			pi.setMapped(param.getName());
			pi.setMappedContext(param.getContext());
			item.addParameter(pi);
		}
	}
}
