package org.eclipse.db.test.eclipse.extension.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.db.test.eclipse.extension.cnf.ProjectExplorerNavigator;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

public class DeleteComponentCategoryAction extends BaseConfirmSelectionListenerAction {

	public DeleteComponentCategoryAction(String title) {
		super(title);
		setImage("images/folder.png");
	}

	@Override
	protected void doWork() {
		ProjectComponentCategory cat = (ProjectComponentCategory) getStructuredSelection().getFirstElement();
		ProjectLoaderService service = new ProjectLoaderService(cat);
		List<Integer> cats = service.getAllComponentCategories(cat.getId());
		deleteInBackground(cats, service);
		IViewPart view = ViewHelper.getView(ProjectExplorerNavigator.ID);
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		ICategoryProjectElement elem = (ICategoryProjectElement) cat.getParent();
		if (elem != null)
			elem.clearChilds();
		nav.refresh(cat.getParent());
	}

	private void deleteInBackground(final List<Integer> cats, final ProjectLoaderService service) {
		IRunnableWithProgress process = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Удаление папок", cats.size());
				int pos = 0;
				for (Integer categoryId : cats) {
					if (monitor.isCanceled())
						return;
					service.deleteComponentCategory(categoryId);
					monitor.worked(pos++);
				}
				monitor.done();
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, true, process);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
