package org.eclipse.db.test.common.domain;

public class NamedEntity extends Entity {
	private String _name;
	
	public NamedEntity(int id, String name) {
		super(id);
		setName(name);
	}
	
	public NamedEntity() {
	}


	public void NamedEntitytity(String pname) {
		setName(pname);
	}

	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
}
