package org.eclipse.db.test.common.domain;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.db.test.common.dal.DbServerInfo;

/**
 * Base execution context
 */
public class ExecutionContext {
	
	private DbServerInfo _info;
	private DbServerInfo _execInfo;
	/**
	 * Parameter list for this context
	 */
	protected ParameterStorage _parameters;
	/**
	 * Script list
	 */
	protected Map<Integer, Script> _scripts;
	protected Map<Integer, Component> _components;
	
	public ExecutionContext(DbServerInfo info, DbServerInfo execInfo) {
		_parameters = new ParameterStorage();
		_scripts = new HashMap<Integer, Script>();
		_components = new HashMap<Integer, Component>();
		_info  = info;
		_execInfo = execInfo;
	}
	
	public DbServerInfo getServerInfo() {
		return _info;
	}

	public DbServerInfo getExecServerInfo() {
		return _execInfo;
	}
	
	public boolean containsScript(int id) {
		return _scripts.containsKey(id);
	}
	
	public void addScript(Script script) {
		_scripts.put(script.getId(), script);
	}
	
	public Script getScript(int id) {
		return _scripts.get(id);
	}
	
	public boolean containsComponent(int id) {
		return _components.containsKey(id);
	}
	
	public void addComponent(Component script) {
		_components.put(script.getId(), script);
	}
	
	public Component getComponent(int id) {
		return _components.get(id);
	}
	
	public ParameterStorage getParams() {
		return _parameters;
	}

}
