package org.eclipse.db.test.common.domain;

import java.util.HashMap;
import java.util.Map;

public class ComponentContext {
	private Map<Integer, Script> scripts;
	private ParameterStorage parameters;
	private Map<Integer, Component> components;
	
	public ComponentContext() {
		scripts = new HashMap<Integer, Script>();
		parameters = new ParameterStorage();
		components = new HashMap<Integer, Component>();
	}

	public void addScript(Script script) {
		scripts.put(script.getId(), script);
	}
	
	public Script getScript(int id) {
		return scripts.containsKey(id) ? scripts.get(id) : null;
	}

	public ParameterStorage getParameters() {
		return parameters;
	}

	public void setParameters(ParameterStorage parameters) {
		this.parameters = parameters;
	}

	public Map<Integer, Component> getComponents() {
		return components;
	}

	public void setComponents(Map<Integer, Component> components) {
		this.components = components;
	}
	
	public Component getComponent(int id) {
		return components.containsKey(id) ? components.get(id) : null;
	}
	
	public void addComponent(Component component) {
		components.put(component.getId(), component);
	}
}
