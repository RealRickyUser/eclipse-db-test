package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.common.domain.ScriptCategory;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptCategory;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptRootCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;

public class AddScriptCategoryAction extends BaseDialogSelectionListenerAction {
	
    public AddScriptCategoryAction(String title) {
    	super(title);
    	setImage("images/folder.png");
	}
    
	@Override
	protected void doWork(String value) {
		createCategory(value);
	}
	
	private void createCategory(String name) {
		Object selected = getStructuredSelection().getFirstElement();
		ProjectLoaderService service = null;
		int parentId = -1;
		Object parentObj = null;
		ProjectScriptRootCategory rootCat;
		ProjectScriptCategory cat;
		ICategoryProjectElement elem = null;
		if (selected instanceof ProjectScriptCategory) {
			cat = (ProjectScriptCategory) selected;
			service = new ProjectLoaderService(cat);
			parentId = cat.getId();
			parentObj = cat;
			elem = cat;
		} else if (selected instanceof ProjectScriptItem) {
			ProjectScriptItem icat = (ProjectScriptItem) selected;
			service = new ProjectLoaderService(icat);
			parentId = icat.getCategoryId();
		} else if (selected instanceof ProjectScriptRootCategory) {
			rootCat = (ProjectScriptRootCategory) selected;
			service = new ProjectLoaderService(rootCat);
			parentObj = rootCat;
			elem = rootCat;
			parentId = 0;
		}
		if (service == null)
			return;
		ScriptCategory sc = new ScriptCategory(0, name);
		sc.setParentId(parentId);
		service.createScriptCategory(sc);
		/*IViewPart view = ViewHelper.getView(ProjectExplorerNavigator.ID);
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;*/
		if (elem != null)
			elem.clearChilds();
		if (parentObj != null) {
			ViewHelper.refreshExplorer(parentObj);
			//nav.refresh(parentObj);
			if (parentObj instanceof ICategoryProjectElement) {
				ICategoryProjectElement e = (ICategoryProjectElement) parentObj;
				Object child = e.getChild(sc.getId());
				if (child != null)
					ViewHelper.selectExplorer(child);
					//nav.select(child);
			}
		}
	}
}
