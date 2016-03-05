package org.eclipse.db.test.eclipse.extension.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.db.test.eclipse.extension.editors.SqlScriptEditor;
import org.eclipse.db.test.eclipse.extension.editors.SqlScriptEditorInput;
import org.eclipse.db.test.eclipse.extension.preferences.NamedDbServerInfo;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenScriptEditorHandler extends AbstractHandler implements IHandler {

	public static final String ID = "org.eclipse.db.test.eclipse.extension.commands.opensqlscripteditor";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
	    // get the selection
	    ISelection selection = HandlerUtil.getCurrentSelection(event);
	    if (selection != null && selection instanceof IStructuredSelection) {
	      Object obj = ((IStructuredSelection) selection).getFirstElement();
	      // if we had a selection lets open the editor
	      if (obj != null) {
	    	  if (obj instanceof ProjectScriptItem) {
	    		  DoInBackground(page, (ProjectScriptItem) obj);
	    	  }
	      }
	    }
		return null;
	}
	
	private void DoInBackground(IWorkbenchPage page, ProjectScriptItem item) {
		final IWorkbenchPage _page = page;
		final ProjectScriptItem _item = item;
		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				ProjectLoaderService service = getService(_item.getProject());
				service.loadScript(_item);
				final SqlScriptEditorInput input = new SqlScriptEditorInput(_item);
				_item.setLoaded(true);
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						try {
							_page.openEditor(input, SqlScriptEditor.ID);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				});
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, false, op);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static ProjectLoaderService getService(IProject project) {
		NamedDbServerInfo info = ConfigurationManager.getServerInfo(project);
		return new ProjectLoaderService(info); 
	}

}
