package org.eclipse.db.test.eclipse.extension.project;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.db.test.eclipse.extension.preferences.NamedDbServerInfo;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.swt.graphics.Image;

public class ProjectTestRootCategory implements ICustomProjectElement, ICategoryProjectElement {

	private Image _image;
	private List<ICustomProjectElement>_childs;
	public static final String NAME = "Тесты";
	private CustomProjectParent _parent;

	public ProjectTestRootCategory(CustomProjectParent customProjectParent) {
		_parent = customProjectParent;
	}
	
	@Override
	public Image getImage() {
        if (_image == null) {
            _image = Activator.getImage("images/test.png"); //$NON-NLS-1$
        }
 
        return _image;
	}

	@Override
	public Object[] getChildren() {
		ProjectLoaderService service = getService();
		if (_childs == null) {
			_childs = service.loadTests(0);
		}
		for (ICustomProjectElement element : _childs) {
			element.setProject(getProject());
			element.setParent(this);
		}
		return _childs.toArray();
	}

	@Override
	public String getText() {
		return NAME;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

	@Override
	public IProject getProject() {
		return _parent.getProject();
	}

	@Override
	public Object getParent() {
		return _parent;
	}

	@Override
	public int getWeigth() {
		return 2;
	}
	
	private ProjectLoaderService getService() {
		NamedDbServerInfo info = ConfigurationManager.getServerInfo(getProject());
		return new ProjectLoaderService(info); 
	}

	@Override
	public void setProject(IProject project) {
	}
	
	@Override
	public void clearChilds() {
		if (_childs == null)
			return;
		_childs.clear();
		_childs = null;
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

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void setParent(Object parent) {
		_parent = (CustomProjectParent) parent;
	}
}
