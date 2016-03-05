package org.eclipse.db.test.eclipse.extension.wizards.newwizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.db.test.eclipse.extension.dialogs.NewProjectDialogMainPage;
import org.eclipse.db.test.eclipse.extension.natures.SqlTestProjectNature;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

public class NewProjectWizard extends BasicNewResourceWizard implements
		IExecutableExtension {

	private static final String WIZARDS_NEWSQLTESTWIZARD = "org.eclipse.db.test.eclipse.extension.wizards.newsqltestwizard"; //$NON-NLS-1$
	//private IConfigurationElement configElement;
	private NewProjectDialogMainPage mainPage;
	private IProject newProject;

	public void addPages() {
		super.addPages();
		setWindowTitle(NewProjectWizardMessages.NewProjectWizard_New_SQL_Test_Project_Window_Title);
		mainPage = new NewProjectDialogMainPage(
				WIZARDS_NEWSQLTESTWIZARD);
		mainPage.setTitle(NewProjectWizardMessages.NewProjectWizard_New_SQL_Test_Project_Page1_Title);
		mainPage.setDescription(NewProjectWizardMessages.NewProjectWizard_New_SQL_Test_Project_Page1_Description);
		this.addPage(mainPage);
	}

	private IProject createNewProject() {
		IProject newProjectLocal = mainPage.getProjectHandle();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace
				.newProjectDescription(newProjectLocal.getName());
		// description.setLocationURI(location);

		// create the new project operation
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				CreateProjectOperation op = new CreateProjectOperation(
						description,
						NewProjectWizardMessages.NewProjectWizard_New_SQL_Test_Project_Creaetion_Error);
				try {
					op.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
				} catch (ExecutionException e) {
					throw new InvocationTargetException(e);
				}
			}
		};

		// run the new project creation operation
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		}
		return newProjectLocal;
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		//configElement = config;
	}

	@Override
	public boolean performFinish() {
		newProject = createNewProject();
		if (newProject == null)
			return false;
		addProjectNature(newProject);
		saveServerData(newProject);
		return true;
	}
	
	private void saveServerData(IProject project) {
		if (project == null)
			return;
		ConfigurationManager.saveProjectData(project, "server", mainPage.getServer().getName());
		ConfigurationManager.saveProjectData(project, "server_password", mainPage.getServer().getPassword());
		ConfigurationManager.saveProjectData(project, "exec_server", mainPage.getExecServer().getName());
		ConfigurationManager.saveProjectData(project, "exec_server_password", mainPage.getExecServer().getPassword());
	}

	private Boolean addProjectNature(IProject project) {
		if (project == null)
			return false;
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = SqlTestProjectNature.NATURE_ID;
			//IStatus status = ResourcesPlugin.getWorkspace().validateNatureSet(newNatures);
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

}
