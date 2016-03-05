package org.eclipse.db.test.eclipse.extension.run;

import org.eclipse.db.test.common.domain.ParameterContext;

public class OutputParameterInfo {
	private String _name;
	private ParameterContext _context;
	private Object _value;
	private Object _newValue;
	
	public OutputParameterInfo(String name, ParameterContext context, Object value, Object newValue) {
		setName(name);
		setContext(context);
		setValue(value == null);
		setNewValue(newValue);
	}
	
	public String getName() {
		return _name;
	}
	
	private void setName(String name) {
		_name = name;
	}
	
	public ParameterContext getContext() {
		return _context;
	}
	
	private void setContext(ParameterContext context) {
		_context = context;
	}
	
	public Object getValue() {
		return _value;
	}
	
	private void setValue(Object value) {
		_value = value;
	}

	public Object getNewValue() {
		return _newValue;
	}
	
	private void setNewValue(Object value) {
		_newValue = value;
	}

	
}
