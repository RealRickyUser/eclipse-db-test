package org.eclipse.db.test.common.domain;

import java.util.ArrayList;
import java.util.List;

public class BaseParametredItem<T extends ParameterItem> extends NamedEntity {
	
	private List<T> parameters;
	
	public BaseParametredItem() {
		setParameters(new ArrayList<T>());
	}
	
	public BaseParametredItem(int id, String name) {
		this();
		setId(id);
		setName(name);
	}

	public List<T> getParameters() {
		return parameters;
	}

	public void setParameters(List<T> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(T item) {
		parameters.add(item);
	}


}
