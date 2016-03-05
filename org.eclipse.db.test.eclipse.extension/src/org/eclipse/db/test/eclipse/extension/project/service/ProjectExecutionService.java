package org.eclipse.db.test.eclipse.extension.project.service;

import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.domain.ScriptExecutionResult;
import org.eclipse.db.test.common.service.ExecutionService;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;

public class ProjectExecutionService {
	private DbServerInfo _info;
	
	public ProjectExecutionService(DbServerInfo info) {
		_info = info;
	}
	
	public ScriptExecutionResult execute(ProjectScriptItem item) {
		return ExecutionService.execute(_info, item.getScript());
	}
}
