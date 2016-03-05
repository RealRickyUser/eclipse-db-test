package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.cnf.ProjectExplorerNavigator;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.ui.IViewPart;

public class RenameComponentCategoryAction extends BaseDialogSelectionListenerAction {
	
	public RenameComponentCategoryAction(String title) {
		super(title);
		setImage("images/folder.png");
	}

	@Override
	protected void doWork(String value) {
		ProjectComponentCategory cat = (ProjectComponentCategory) getStructuredSelection().getFirstElement();
		if (cat == null)
			return;
		cat.getItem().setName(value);
		ProjectLoaderService service = new ProjectLoaderService(cat);
		service.updateComponentCategory(cat.getItem());
		IViewPart view = ViewHelper.getView(ProjectExplorerNavigator.ID);
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		nav.refresh(cat);
	}
}
