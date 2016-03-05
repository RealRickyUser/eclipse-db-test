package org.eclipse.db.test.common.domain;

public class BaseCategory extends NamedEntity {
	private int _parent;
	
	public BaseCategory(int id, String name) {
		super(id, name);
	}
	
	public int getParentId() {
		return _parent;
	}
	
	public void setParentId(int parent) {
		_parent = parent;
	}
}
