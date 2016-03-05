package org.eclipse.db.test.common.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.db.test.common.dal.ConnectionManager;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.dal.NamedParameterStatement;
import org.eclipse.db.test.common.domain.ScriptCategory;

public class ScriptCategoryService {

	public static List<Integer> getAllCategories(DbServerInfo info, int rootId) {
		Connection conn = ConnectionManager.getConnection(info);
		List<Integer> result = new ArrayList<Integer>();
		try {
			
			NamedParameterStatement stmt = new NamedParameterStatement(conn, "exec GetScriptCategories :id");
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
	
	public static void updateCategory(DbServerInfo info, ScriptCategory category) {
		Connection conn = ConnectionManager.getConnection(info);
		String query = "UPDATE ScriptCategory SET name = :name WHERE id = :id";
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
		String query = "DELETE FROM ScriptCategory WHERE id = :id";
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
}
