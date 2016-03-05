package org.eclipse.db.test.eclipse.extension.project;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.db.test.eclipse.extension.editors.PropertyChangedListener;
import org.eclipse.swt.graphics.Image;

public class ProjectComponentItem implements ICustomProjectElement {

	private Component _script;
	private Image _image;
	//private ProjectScriptItemPropertySource _propSource;
	private IProject _project;
	private Object _parent;
	private boolean _isLoaded;
	
	private List<PropertyChangedListener> _listeners;
	
	public ProjectComponentItem(Component script) {
		_script = script;
		_listeners = new ArrayList<PropertyChangedListener>();
	}
	
	@Override
	public Image getImage() {
        if (_image == null) {
            _image = Activator.getImage("images/component.png"); //$NON-NLS-1$
        }
 
        return _image;
	}

	@Override
	public Object[] getChildren() {
		return null;
	}

	@Override
	public String getText() {
		return _script.getName();
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

	/*@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter != IPropertySource.class)
			return null;
		if (_propSource == null)
			_propSource = new ProjectScriptItemPropertySource(this);
		return (T) _propSource;
	}*/

	public int getId() {
		return _script.getId();
	}
	

	public Component getComponent() {
		return _script;
	}

	@Override
	public void setProject(IProject project) {
		_project = project;
	}
	
	public int getCategoryId() {
		return _script.getCategoryId();
	}

	@Override
	public void setParent(Object parent) {
		_parent = parent;
	}
	
	public void setLoaded(boolean value) {
		_isLoaded = value;
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
	
}
