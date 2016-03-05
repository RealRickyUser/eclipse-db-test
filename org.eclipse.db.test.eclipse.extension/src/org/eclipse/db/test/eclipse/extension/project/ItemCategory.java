package org.eclipse.db.test.eclipse.extension.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.db.test.common.domain.NamedEntity;
import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.swt.graphics.Image;

public abstract class ItemCategory<T extends NamedEntity> implements ICustomProjectElement {

	protected T _item;
	private Image _image;
	private IProject _project;
	private Object _parent;
	
	public ItemCategory(T item) {
		_item =item;
	}
	
	@Override
	public Image getImage() {
        if (_image == null) {
            _image = Activator.getImage("images/folder.png"); //$NON-NLS-1$
        }
 
        return _image;
	}

	@Override
	public String getText() {
		return _item.getName();
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
	public void setParent(Object parent) {
		_parent = parent;
	}

	@Override
	public int getWeigth() {
		return 10;
	}
	
	public void setProject(IProject project) {
		_project = project;
	}
	
	public int getId() {
		return _item.getId();
	}
	
	public T getItem() {
		return _item;
	}

}
