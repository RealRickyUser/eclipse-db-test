package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.cnf.ProjectExplorerNavigator;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentItem;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.ui.IViewPart;

public class DeleteComponentAction extends BaseConfirmSelectionListenerAction {
	
	public DeleteComponentAction(String title) {
		super(title);
		setImage("images/component.png");
	}
	
	@Override
	protected void doWork() {
		ProjectComponentItem cat = (ProjectComponentItem) getStructuredSelection().getFirstElement();
		ProjectLoaderService service = new ProjectLoaderService(cat);
		service.deleteComponent(cat.getComponent());
		IViewPart view = ViewHelper.getView(ProjectExplorerNavigator.ID);
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		ICategoryProjectElement parent = (ICategoryProjectElement) cat.getParent();
		if (parent != null) {
			parent.clearChilds();
			nav.refresh(parent);
		}
	}
}
