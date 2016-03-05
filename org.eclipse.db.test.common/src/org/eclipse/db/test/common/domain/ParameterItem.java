package org.eclipse.db.test.common.domain;

public class ParameterItem /*extends Entity*/ {
	private String _paramName;
	private String mapped;
	private ParameterContext context;
	
	private Object _value;
	
	public ParameterItem() {
		
	}
	
	public ParameterItem(String paramName, ParameterContext pcontext, String mapName) {
		setName(paramName);
		setContext(pcontext);
		setMapped(mapName);
	}
	
	public ParameterItem(String mapName) {
		setMapped(mapName);
	}

	public ParameterContext getContext() {
		return context;
	}

	public void setContext(ParameterContext context) {
		this.context = context;
	}

	public String getMapped() {
		return mapped;
	}

	public void setMapped(String mapped) {
		this.mapped = mapped;
	}
	
	@Override
	public String toString() {
		return getMapped() + "=[" + getContext().toString() + "] " + getName();
	}

	public String getName() {
		return _paramName;
	}

	public void setName(String paramName) {
		_paramName = paramName;
	}

	public Object getValue() {
		return _value;
	}

	public void setValue(Object _value) {
		this._value = _value;
	}

	public ParameterItem clone() {
		return new ParameterItem(getName(), getContext(), getMapped());
	}
	
	
}
