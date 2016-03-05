package org.eclipse.db.test.common.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParameterValue {
	private String name;
	private ParameterContext context;
	private Object value;
	
	public ParameterValue() {
		
	}
	
	public ParameterValue(String pname, ParameterContext pcontext, Object pvalue) {
		setName(pname);
		setContext(pcontext);
		setValue(pvalue);
	}
	
	public String getName() {
		return name;
	}
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	public ParameterContext getContext() {
		return context;
	}
	@XmlAttribute
	public void setContext(ParameterContext context) {
		this.context = context;
	}
	public Object getValue() {
		return value;
	}
	@XmlElement
	public void setValue(Object value) {
		this.value = value;
	}

}
