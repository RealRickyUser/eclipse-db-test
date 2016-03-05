package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.cnf.ProjectExplorerNavigator;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.ui.IViewPart;

public class DeleteScriptAction extends BaseConfirmSelectionListenerAction {

	public DeleteScriptAction(String title) {
		super(title);
		setImage("images/script.png");
	}

	@Override
	protected void doWork() {
		ProjectScriptItem cat = (ProjectScriptItem) getStructuredSelection().getFirstElement();
		ProjectLoaderService service = new ProjectLoaderService(cat);
		service.deleteScript(cat.getScript());
		IViewPart view = ViewHelper.getView(ProjectExplorerNavigator.ID);
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		ICategoryProjectElement parent = (ICategoryProjectElement) cat.getParent();
		if (parent != null) {
			parent.clearChilds();
			nav.refresh(parent);
		}
	}

}
