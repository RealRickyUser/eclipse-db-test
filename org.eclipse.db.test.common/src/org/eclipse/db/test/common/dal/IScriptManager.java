package org.eclipse.db.test.common.dal;

import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.common.domain.ComponentContext;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptExecutionResult;
import org.eclipse.db.test.common.domain.Test;

public interface IScriptManager {

	/**
	 * ��������� ������
	 * @param script ������, ������� ������� ���������
	 */
	ScriptExecutionResult execute(Script script);
	
	ScriptExecutionResult execute(Component component, ComponentContext context);
	
	ScriptExecutionResult execute(Test test, ComponentContext context);
	
	//Script load(String dir);
}
