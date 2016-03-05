package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.cnf.ProjectExplorerNavigator;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestItem;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.ui.IViewPart;

public class DeleteTestAction extends BaseConfirmSelectionListenerAction {
	
	public DeleteTestAction(String title) {
		super(title);
		setImage("images/test.png");
	}
	
	@Override
	protected void doWork() {
		ProjectTestItem cat = (ProjectTestItem) getStructuredSelection().getFirstElement();
		ProjectLoaderService service = new ProjectLoaderService(cat);
		service.deleteTest(cat.getTest());
		IViewPart view = ViewHelper.getView(ProjectExplorerNavigator.ID);
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		ICategoryProjectElement parent = (ICategoryProjectElement) cat.getParent();
		if (parent != null) {
			parent.clearChilds();
			nav.refresh(parent);
		}
	}
}
