package org.eclipse.db.test.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс хранит данные для теста
 * @author sbt-blinchikov-ae
 *
 */
@XmlRootElement
public class TestData {
	private List<ParameterValue> values;
	private List<TestItem> tests;
	
	public TestData() {
		setValues(new ArrayList<ParameterValue>());
		setTests(new ArrayList<TestItem>());
	}

	public List<ParameterValue> getValues() {
		return values;
	}
	
	@XmlElement(name = "parameter")
	@XmlElementWrapper(name = "parameters")
	public void setValues(List<ParameterValue> values) {
		this.values = values;
	}

	public List<TestItem> getTests() {
		return tests;
	}

	@XmlElement(name = "test")
	@XmlElementWrapper(name = "tests")
	public void setTests(List<TestItem> tests) {
		this.tests = tests;
	}

}
