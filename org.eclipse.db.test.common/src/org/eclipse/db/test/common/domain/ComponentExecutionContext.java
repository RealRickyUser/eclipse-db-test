package org.eclipse.db.test.common.domain;

import org.eclipse.db.test.common.dal.DbServerInfo;

/**
 * Component execution context
 */
public class ComponentExecutionContext extends ExecutionContext {

	public ComponentExecutionContext(DbServerInfo info, DbServerInfo execInfo) {
		super(info, execInfo);
	}
	
	public ComponentExecutionContext createChildContext() {
		ComponentExecutionContext ctx = new ComponentExecutionContext(getServerInfo(), getExecServerInfo());
		ctx._components = this._components;
		ctx._scripts = this._scripts;
		return ctx;
	}

}
