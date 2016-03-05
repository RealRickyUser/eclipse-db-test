package org.eclipse.db.test.common.domain;

public class Entity {
	private int _id;
	
	public Entity() {
		setId(0);
	}
	
	public Entity(int id) {
		setId(id);
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		_id = id;
	}
}
