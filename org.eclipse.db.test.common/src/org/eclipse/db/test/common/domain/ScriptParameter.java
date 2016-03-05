package org.eclipse.db.test.common.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ScriptParameter extends NamedEntity {
	private Object value;
	private ParameterDirection direction;
	private String _type;
	private String _size;
	private int _scriptId;
	
	public ScriptParameter() {
		
	}
	
	public ScriptParameter(String pname, ParameterDirection pdirection) {
		setName(pname);
		setDirection(pdirection);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public ParameterDirection getDirection() {
		return direction;
	}
	@XmlAttribute
	public void setDirection(ParameterDirection direction) {
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		String a = getDirection().toString();
		String b = getName();
		String c =  getValue() == null ? "null" : getValue().toString();
		return "[" + a + "] " + b + " = " + c;
	}

	public String getSize() {
		return _size;
	}

	public void setSize(String size) {
		_size = size;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public int getScriptId() {
		return _scriptId;
	}

	public void setScriptId(int scriptId) {
		_scriptId = scriptId;
	}

}
