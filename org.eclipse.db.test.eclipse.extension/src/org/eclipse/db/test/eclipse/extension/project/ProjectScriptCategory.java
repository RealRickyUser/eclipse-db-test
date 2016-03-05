package org.eclipse.db.test.eclipse.extension.project;

import java.util.List;

import org.eclipse.db.test.common.domain.ScriptCategory;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;

public class ProjectScriptCategory extends ItemCategory<ScriptCategory> implements ICategoryProjectElement {

	private List<ICustomProjectElement>_childs;
	public ProjectScriptCategory(ScriptCategory category) {
		super(category);
	}

	@Override
	public Object[] getChildren() {
		if (_childs == null) {
			ProjectLoaderService service = new ProjectLoaderService(this);
			_childs = service.loadScripts(_item.getId());
		}
		for (ICustomProjectElement element : _childs) {
			element.setProject(getProject());
			element.setParent(this);
		}
		return _childs.toArray();
	}
	
	@Override
	public void clearChilds() {
		if (_childs == null)
			return;
		_childs.clear();
		_childs =null;
	}

	@Override
	public Object getChild(int id) {
		if (_childs == null)
			return null;
		for (ICustomProjectElement elem : _childs) {
				if (elem.getId() == id)
					return elem;
			}
		return null;
	}
	
}
