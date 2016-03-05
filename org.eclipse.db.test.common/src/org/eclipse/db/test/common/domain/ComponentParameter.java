package org.eclipse.db.test.common.domain;

public class ComponentParameter extends NamedEntity {

	private ParameterContext _context;
	private Object _value;
	
	public ComponentParameter() {
		this("", ParameterContext.Local);
	}
	
	public ComponentParameter(String name, ParameterContext context) {
		super(0, name);
		setContext(context);
	}

	public ParameterContext getContext() {
		return _context;
	}

	public void setContext(ParameterContext context) {
		_context = context;
	}

	public Object getValue() {
		return _value;
	}

	public void setValue(Object value) {
		_value = value;
	}
	
	public ComponentParameter clone() {
		ComponentParameter param = new ComponentParameter(getName(), getContext());
		param.setValue(getValue());
		return param;
	}
}
