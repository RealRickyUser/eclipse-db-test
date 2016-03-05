package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentCategory;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentRootCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;

public class AddComponentAction extends BaseDialogSelectionListenerAction {

	public AddComponentAction(String title) {
		super(title);
		setImage("images/component.png");
	}

	@Override
	protected void doWork(String value) {
		createScript(value);
	}
	
	private void createScript(String name) {
		Object selected = getStructuredSelection().getFirstElement();
		ProjectLoaderService service = null;
		int parentId = -1;
		Object parentObj = null;
		ProjectComponentRootCategory rootCat;
		ProjectComponentCategory cat;
		ICategoryProjectElement elem = null;
		if (selected instanceof ProjectComponentCategory) {
			cat = (ProjectComponentCategory) selected;
			service = new ProjectLoaderService(cat);
			parentId = cat.getId();
			parentObj = cat;
			elem = cat;
		} else if (selected instanceof ProjectComponentItem) {
			ProjectComponentItem icat = (ProjectComponentItem) selected;
			service = new ProjectLoaderService(icat);
			parentId = icat.getCategoryId();
		} else if (selected instanceof ProjectComponentRootCategory) {
			rootCat = (ProjectComponentRootCategory) selected;
			service = new ProjectLoaderService(rootCat);
			parentObj = rootCat;
			elem = rootCat;
			parentId = 0;
		}
		if (service == null)
			return;
		Component comp =new Component(0, name);
		comp.setCategoryId(parentId);
		service.createComponent(comp);
		if (elem != null)
			elem.clearChilds();
		if (parentObj != null) {
			ViewHelper.refreshExplorer(parentObj);
			if (parentObj instanceof ICategoryProjectElement) {
				ICategoryProjectElement e = (ICategoryProjectElement) parentObj;
				Object child = e.getChild(comp.getId());
				if (child != null)
					ViewHelper.selectExplorer(child);
			}
		}
	}
}
