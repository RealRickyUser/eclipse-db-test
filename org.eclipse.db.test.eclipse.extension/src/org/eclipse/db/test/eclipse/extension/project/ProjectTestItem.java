package org.eclipse.db.test.eclipse.extension.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.db.test.common.domain.Test;
import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.db.test.eclipse.extension.editors.PropertyChangedListener;
import org.eclipse.swt.graphics.Image;

public class ProjectTestItem implements ICustomProjectElement {

	private Test _test;
	private Image _image;
	private IProject _project;
	private Object _parent;
	private boolean _isLoaded;
	
	private List<PropertyChangedListener> _listeners;
	
	public ProjectTestItem(Test test) {
		_test = test;
		_listeners = new ArrayList<PropertyChangedListener>();
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
		return null;
	}

	@Override
	public String getText() {
		return _test.getName();
	}

	@Override
	public boolean hasChildren() {
		return false;
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
		return 20;
	}

	public int getId() {
		return _test.getId();
	}
	
	public Test getScript() {
		return _test;
	}

	@Override
	public void setProject(IProject project) {
		_project = project;
	}
	
	public int getCategoryId() {
		return _test.getCategoryId();
	}

	@Override
	public void setParent(Object parent) {
		_parent = parent;
	}
	
	public void setLoaded(boolean value) {
		_isLoaded = value;
		if (!value)
			clearData();
	}
	
	public boolean isLoaded() {
		return _isLoaded;
	}
	
	public void addListener(PropertyChangedListener listener) {
		if (!_listeners.contains(listener))
			_listeners.add(listener);
	}
	
	public void removeListener(PropertyChangedListener listener) {
		if (_listeners.contains(listener))
			_listeners.remove(listener);
	}
	
	public void raisePropertyChanged() {
		for (PropertyChangedListener listener : _listeners) {
			listener.raise();
		}
	}
	
	private void clearData() {
		/*for (ScriptParameter item : _script.getParameters()) {
			item.setValue(null);
		}*/
	}
	
	public Test getTest() {
		return _test;
	}
}
