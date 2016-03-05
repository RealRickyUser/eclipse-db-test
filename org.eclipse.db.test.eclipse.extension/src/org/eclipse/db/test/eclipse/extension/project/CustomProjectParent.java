package org.eclipse.db.test.eclipse.extension.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.swt.graphics.Image;

public class CustomProjectParent implements ICustomProjectElement {

    private Image _image;
	private Object[] _children;
	private IProject _project;
	private Object _parent;
    
	public CustomProjectParent(IProject project) {
		_project = project;
	}
	
    public String getProjectName() {
        return _project.getName();
    }
    
    public Image getImage() {
        if (_image == null) {
            _image = Activator.getImage("images/ServerProject.png"); //$NON-NLS-1$
        }
 
        return _image;
    }

	@Override
	public Object[] getChildren() {
        if (_children == null) {
            _children = initializeChildren(_project);
        }
        return _children;
	}
	
    private ICustomProjectElement[] initializeChildren(IProject project) {
        ICustomProjectElement[] children = {
                new ProjectScriptRootCategory(this),
                new ProjectComponentRootCategory(this),
                new ProjectTestRootCategory(this)/*,
                new ProjectTestSetRootCategory(this)*/
        };
 
        return children;
    }

	@Override
	public String getText() {
		return _project.getName();
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

	@Override
	public IProject getProject() {
		return _project;
	}

	@Override
	public Object getParent() {
		return _parent;
	}

	@Override
	public int getWeigth() {
		return 0;
	}

	@Override
	public void setProject(IProject project) {
		_project = project;
	}

	@Override
	public int getId() {
		return -1;
	}

	@Override
	public void setParent(Object parent) {
		_parent = parent;
	}
}
