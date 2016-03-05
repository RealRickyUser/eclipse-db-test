package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.common.domain.TestCategory;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestCategory;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestRootCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;

public class AddTestCategoryAction extends BaseDialogSelectionListenerAction {
	
    public AddTestCategoryAction(String title) {
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
		ProjectTestRootCategory rootCat;
		ProjectTestCategory cat;
		ICategoryProjectElement elem = null;
		if (selected instanceof ProjectTestCategory) {
			cat = (ProjectTestCategory) selected;
			service = new ProjectLoaderService(cat);
			parentId = cat.getId();
			parentObj = cat;
			elem = cat;
		} else if (selected instanceof ProjectTestItem) {
			ProjectTestItem icat = (ProjectTestItem) selected;
			service = new ProjectLoaderService(icat);
			parentId = icat.getCategoryId();
		} else if (selected instanceof ProjectTestRootCategory) {
			rootCat = (ProjectTestRootCategory) selected;
			service = new ProjectLoaderService(rootCat);
			parentObj = rootCat;
			elem = rootCat;
			parentId = 0;
		}
		if (service == null)
			return;
		TestCategory sc = new TestCategory(0, name);
		sc.setParentId(parentId);
		service.createTestCategory(sc);
		if (elem != null)
			elem.clearChilds();
		if (parentObj != null) {
			ViewHelper.refreshExplorer(parentObj);
			if (parentObj instanceof ICategoryProjectElement) {
				ICategoryProjectElement e = (ICategoryProjectElement) parentObj;
				Object child = e.getChild(sc.getId());
				if (child != null)
					ViewHelper.selectExplorer(child);
			}
		}
	}
}
