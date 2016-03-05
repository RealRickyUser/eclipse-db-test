package org.eclipse.db.test.common.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.common.domain.ComponentContext;
import org.eclipse.db.test.common.domain.ComponentItem;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.common.domain.ParameterDirection;
import org.eclipse.db.test.common.domain.ParameterItem;
import org.eclipse.db.test.common.domain.ParameterStorage;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptExecutionResult;
import org.eclipse.db.test.common.domain.ScriptItem;
import org.eclipse.db.test.common.domain.ScriptParameter;
import org.eclipse.db.test.common.domain.Test;


public class ScriptManager extends BaseDal implements IScriptManager {

	public ScriptManager(DbServerInfo info) {
		super(info);
	}

	@Override
	public ScriptExecutionResult execute(Script script) {
		ScriptExecutionResult result = new ScriptExecutionResult();
		if (script == null) {
			result.setException(new IllegalArgumentException("script"));
			return result;
		}
		Connection conn = null;
		Map<String, ScriptParameter> params = new HashMap<String, ScriptParameter>();
		try {
			conn = getConnection();
			NamedParameterStatement stmt = new NamedParameterStatement(conn, "SET NOCOUNT ON;" + script.getText());
			for(ScriptParameter param : script.getParameters()) {
				if (param.getDirection() == ParameterDirection.Out || param.getDirection() == ParameterDirection.InOut) {
					params.put(param.getName(), param);
				}
				if (param.getDirection() == ParameterDirection.Out) {
					continue;
				}
				stmt.setObject(param.getName(), param.getValue());
			}
			ResultSet rs = stmt.executeQuery();
			conn.commit();
			while(rs.next()) {
				for(String paramName : params.keySet()) {
					int pos = rs.findColumn(paramName);
					if (pos < 0)
						continue;
					ScriptParameter p = params.get(paramName);
					p.setValue(rs.getObject(pos));
				}
			}
			rs.close();
			stmt.close();
			conn.close();
		
		} catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			result.setException(e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ScriptExecutionResult execute(Component component, ComponentContext context) {
		ScriptExecutionResult result = new ScriptExecutionResult();
		if (component == null) {
			result.setException(new IllegalArgumentException("script"));
			return result;
		}
		if (context == null) {
			result.setException(new IllegalArgumentException("context"));
			return result;
		}
		
		for(ScriptItem si : component.getScripts()) {
			Script script = context.getScript(si.getId());
			do {
				PrepareParameters(si, script, context);
				execute(script);
				if (script.isAction() && !script.isStateSuccess()) {
					System.out.println("Sleeping");
					try {
						TimeUnit.MICROSECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
			while (script.isAction() && script.isStateSuccess());
			SaveParameters(si, script, context);
		}
		
		return result;
	}
	
	private void PrepareParameters(ScriptItem si, Script script, ComponentContext context) {
		for(ParameterItem pi : si.getParameters()) {
			ScriptParameter param = script.findParameter(pi.getName());
			if (param == null)
				continue;
			Object val = context.getParameters().get(pi.getContext(), pi.getMapped());
			param.setValue(val);
		}
	}
	
	private void SaveParameters(ScriptItem si, Script script, ComponentContext context) {
		for(ParameterItem pi : si.getParameters()) {
			ScriptParameter param = script.findParameter(pi.getName());
			if (param == null)
				continue;
			if (param.getDirection() == ParameterDirection.In)
				continue;
			Object val = param.getValue();
			context.getParameters().set(pi.getContext(), pi.getMapped(), val);
		}
		
	}

	@Override
	public ScriptExecutionResult execute(Test test, ComponentContext context) {
		ScriptExecutionResult result = new ScriptExecutionResult();
		if (test == null) {
			result.setException(new IllegalArgumentException("script"));
			return result;
		}
		if (context == null) {
			result.setException(new IllegalArgumentException("context"));
			return result;
		}
		
		for(ComponentItem sci : test.getComponents()) {
			Component sc = context.getComponent(sci.getId());
			execute(sc, context);
			SaveParameters(sci, sc, context);
		}
		processTestResults(context, test.getName());
		return result;
	}


	private void SaveParameters(ComponentItem sci, Component sc, ComponentContext context) {
		
	}
	
	private void processTestResults(ComponentContext context, String testName) {
		ParameterStorage ps = context.getParameters();
		for(String key : ps.getByContext(ParameterContext.Etalon).keySet()) {
			if (!ps.contains(ParameterContext.Runtime, key))
				continue;
			Object orig = ps.get(ParameterContext.Etalon, key);
			Object runtime = ps.get(ParameterContext.Runtime, key);
			String origStr = orig == null ? "{null}" : orig.toString();
			String runStr = runtime == null ? "{null}" : runtime.toString();
			Boolean res = origStr.equals(runStr);
			String result = (res ? "[PASSED]" : "[Fail]") + " " + testName + ": Test variable " + key + ". Original="+origStr + ", Runtime=" + runStr + ".";
			System.out.println(result);
		}
	}

}
