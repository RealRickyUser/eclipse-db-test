package org.eclipse.db.test.common.domain;

public class ComponentParameterItem extends ParameterItem {
	private ParameterContext _mappedContext;

	public ComponentParameterItem() {
		super();
	}
	
	public ComponentParameterItem(ParameterContext mappedContext, String mappedName) {
		setMappedContext(mappedContext);
		setMapped(mappedName);
	}
	
	public ComponentParameterItem(String name, ParameterContext ctx, ParameterContext mappedContext, String mappedName) {
		setMappedContext(mappedContext);
		setMapped(mappedName);
	}
	
	public ParameterContext getMappedContext() {
		return _mappedContext;
	}

	public void setMappedContext(ParameterContext mappedContext) {
		_mappedContext = mappedContext;
	}
	
	public ComponentParameterItem clone() {
		return new ComponentParameterItem(getName(), getContext(), getMappedContext(), getMapped());
	}

}
