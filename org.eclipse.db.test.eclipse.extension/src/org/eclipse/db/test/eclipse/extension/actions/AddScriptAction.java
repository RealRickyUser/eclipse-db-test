package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptType;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.project.ICategoryProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptCategory;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptRootCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;

public class AddScriptAction extends BaseDialogSelectionListenerAction {

	public AddScriptAction(String title) {
		super(title);
		setImage("images/script.png");
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
		Script sc = new Script(0, name);
		sc.setCategoryId(parentId);
		sc.setType(ScriptType.Action);
		service.createScript(sc);
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
