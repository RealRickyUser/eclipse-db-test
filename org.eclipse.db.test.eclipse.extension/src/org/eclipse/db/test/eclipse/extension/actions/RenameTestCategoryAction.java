package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.cnf.ProjectExplorerNavigator;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.ui.IViewPart;

public class RenameTestCategoryAction extends BaseDialogSelectionListenerAction {
	
	public RenameTestCategoryAction(String title) {
		super(title);
		setImage("images/folder.png");
	}

	@Override
	protected void doWork(String value) {
		ProjectTestCategory cat = (ProjectTestCategory) getStructuredSelection().getFirstElement();
		if (cat == null)
			return;
		cat.getItem().setName(value);
		ProjectLoaderService service = new ProjectLoaderService(cat);
		service.updateTestCategory(cat.getItem());
		IViewPart view = ViewHelper.getView(ProjectExplorerNavigator.ID);
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		nav.refresh(cat);
	}
}
