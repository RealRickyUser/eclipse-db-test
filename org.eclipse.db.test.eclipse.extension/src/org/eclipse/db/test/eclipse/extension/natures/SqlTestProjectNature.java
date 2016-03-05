package org.eclipse.db.test.eclipse.extension.natures;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class SqlTestProjectNature implements IProjectNature {
	
	public static final String NATURE_ID = "org.eclipse.db.test.eclipse.extension.SqlTestProjectNature";
	protected IProject _project;

	@Override
	public void configure() throws CoreException {

	}

	@Override
	public void deconfigure() throws CoreException {

	}

	@Override
	public IProject getProject() {
		return _project;
	}

	@Override
	public void setProject(IProject project) {
		_project = project;
	}

}
