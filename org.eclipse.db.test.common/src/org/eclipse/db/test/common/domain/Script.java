package org.eclipse.db.test.common.domain;

import java.util.ArrayList;
import java.util.List;

public class Script extends NamedEntity {
	private String text;
	private List<ScriptParameter> parameters;
	private ScriptType type;
	private String stateParam;
	private String stateValue;
	private int _categoryId;
	private String _comment;

	public Script() {
		parameters = new ArrayList<ScriptParameter>();
	}
	
	public Script(String ptext) {
		this();
		setText(ptext);
	}
	
	public Script(int id, String name) {
		this();
		setId(id);
		setName(name);
	}
	
	public void addParameter(ScriptParameter parameter) {
		for(ScriptParameter param : parameters) {
			if (param != null && param.getName() != null && param.getName().equals(parameter.getName()))
				return;
		}
		parameters.add(parameter);
	}
	
	public List<ScriptParameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<ScriptParameter> parameters) {
		this.parameters = parameters;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public ScriptParameter findParameter(int id) {
		for(ScriptParameter param : parameters) {
			if (param.getId() == id)
				return param;
		}
		return null;
	}
	
	public ScriptParameter findParameter(String name) {
		for(ScriptParameter param : parameters) {
			if (param.getName().equals(name))
				return param;
		}
		return null;
	}
	
	public void setParameterValue(String name, Object value) {
		ScriptParameter param = findParameter(name);
		if (param == null)
			return;
		param.setValue(value);
	}
	
	public Object getParameterValue(String name) {
		ScriptParameter param = findParameter(name);
		if (param == null)
			return null;
		return param.getValue();
	}
	
	public boolean isInputParameter(String name) {
		ScriptParameter param = findParameter(name);
		return param != null && param.getDirection() != ParameterDirection.Out;
	}
	
	public boolean isOutputParameter(String name) {
		ScriptParameter param = findParameter(name);
		return param != null && param.getDirection() != ParameterDirection.In;
	}

	public ScriptType getType() {
		return type;
	}

	public void setType(ScriptType type) {
		this.type = type;
	}

	public String getStateParam() {
		return stateParam;
	}

	public void setStateParam(String stateParam) {
		this.stateParam = stateParam;
	}

	public String getStateValue() {
		return stateValue;
	}

	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}
	
	public void setCategoryId(int categoryId) {
		_categoryId = categoryId;
	}
	
	public int getCategoryId() {
		return _categoryId;
	}
	
	public Boolean isAction() {
		return type == ScriptType.Action;
	}

	public Boolean isStateSuccess() {
		if (isAction() || stateParam == null)
			return false;
		String value = null;
		for(ScriptParameter p : parameters) {
			if (p.getName().equals(stateParam)) {
				value = p.getValue() == null ? null : p.getValue().toString();
				break;
			}
		}
		return stateValue.equals(value);
	}

	public String getComment() {
		return _comment;
	}

	public void setComment(String comment) {
		_comment = comment;
	}
	
	public void clearParamValues() {
		for (ScriptParameter param : parameters) {
			param.setValue(null);
		}
	}

}
