package org.eclipse.db.test.common.domain;

import java.util.ArrayList;
import java.util.List;

public class Test extends NamedEntity {
	private List<ComponentItem> components;
	private List<ComponentParameter> parameters;
	private int _categoryId;

	public Test() {
		setComponents(new ArrayList<ComponentItem>());
		setParameters(new ArrayList<ComponentParameter>());
	}
	
	public Test(int id, String name) {
		this();
		setId(id);
		setName(name);
	}
	
	public List<ComponentItem> getComponents() {
		return components;
	}

	public void setComponents(List<ComponentItem> components) {
		this.components = components;
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

	public int getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(int categoryId) {
		_categoryId = categoryId;
	}
}
