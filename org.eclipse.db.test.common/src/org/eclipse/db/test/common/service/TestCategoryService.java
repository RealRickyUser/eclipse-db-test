package org.eclipse.db.test.common.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.db.test.common.dal.ConnectionManager;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.dal.NamedParameterStatement;
import org.eclipse.db.test.common.domain.TestCategory;

public class TestCategoryService {

	public static List<Integer> getAllCategories(DbServerInfo info, int rootId) {
		Connection conn = ConnectionManager.getConnection(info);
		List<Integer> result = new ArrayList<Integer>();
		try {
			
			NamedParameterStatement stmt = new NamedParameterStatement(conn, "exec GetTestCategories :id");
			stmt.setObject("id", rootId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt("id"));
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	

	public static List<TestCategory> getCategories(DbServerInfo info, int parent) {
		List<TestCategory> categories = new ArrayList<TestCategory>();
		Connection conn = ConnectionManager.getConnection(info);
		String query = "select id, name from TestCategory";
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
				TestCategory sc = new TestCategory(id, name);
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
	
	public static void updateCategory(DbServerInfo info, TestCategory category) {
		Connection conn = ConnectionManager.getConnection(info);
		String query = "UPDATE TestCategory SET name = :name WHERE id = :id";
		try {
			
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", category.getId());
			stmt.setObject("name", category.getName());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		return;
	}
	
	public static void deleteCategory(DbServerInfo info, int categoryId) {
		Connection conn = ConnectionManager.getConnection(info);
		String query = "DELETE FROM TestCategory WHERE id = :id";
		try {
			NamedParameterStatement stmt = new NamedParameterStatement(conn, query);
			stmt.setObject("id", categoryId);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		return;
	}
	
	public static void addTestCategory(DbServerInfo info, TestCategory category) {
		Connection conn = ConnectionManager.getConnection(info);
		try {
			
			String query = "INSERT INTO TestCategory(name, parentId) VALUES(:name, :parentId);";
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
	
}
