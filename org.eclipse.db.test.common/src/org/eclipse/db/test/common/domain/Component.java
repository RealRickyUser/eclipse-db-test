package org.eclipse.db.test.common.domain;

import java.util.ArrayList;
import java.util.List;

public class Component extends NamedEntity {
	private List<ScriptItem> scripts;
	private List<ComponentParameter> parameters;
	private int _categoryId;
	
	public Component() {
		setScripts(new ArrayList<ScriptItem>());
		setParameters(new ArrayList<ComponentParameter>());
	}
	
	public Component(int id, String name) {
		this();
		setId(id);
		setName(name);
	}

	public List<ScriptItem> getScripts() {
		return scripts;
	}

	public void setScripts(List<ScriptItem> scripts) {
		this.scripts = scripts;
	}

	public List<ComponentParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ComponentParameter> parameters) {
		this.parameters = parameters;
	}
	
	public void addParameter(ComponentParameter param) {
		parameters.add(param);
	}
	
	public ComponentParameter findParameter(String name, ParameterContext ctx) {
		for(ComponentParameter param : parameters) {
			if (param.getName().equals(name) && param.getContext() == ctx)
				return param;
		}
		return null;
	}
	
	public void setCategoryId(int categoryId) {
		_categoryId = categoryId;
	}
	
	public int getCategoryId() {
		return _categoryId;
	}
	
	public void clearParamValues() {
		for (ComponentParameter param : parameters) {
			param.setValue(null);
		}
	}
	
	public void setParameterValue(String name, ParameterContext ctx, Object value) {
		ComponentParameter param = findParameter(name, ctx);
		if (param == null)
			return;
		param.setValue(value);
	}
	
	public Object getParameterValue(String name, ParameterContext ctx) {
		ComponentParameter param = findParameter(name, ctx);
		if (param == null)
			return null;
		return param.getValue();
	}
}
