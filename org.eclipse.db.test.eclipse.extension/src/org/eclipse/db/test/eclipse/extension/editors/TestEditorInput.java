package org.eclipse.db.test.eclipse.extension.editors;

import org.eclipse.db.test.eclipse.extension.project.ProjectTestItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class TestEditorInput implements IEditorInput {

	private ProjectTestItem _item;
	
	public TestEditorInput(ProjectTestItem item) {
		_item = item;
	}
	
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return _item.getText();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return getName();
	}
	

	@Override
	public boolean equals(Object item) {
		if (item == null)
			return false;
		if (!(item instanceof TestEditorInput))
			return false;
		TestEditorInput i = (TestEditorInput) item;
		return (getId() == i.getId());
	}
	
	private int getId() {
		return _item.getId();
	}
	
	public ProjectTestItem getItem() {
		return _item;
	}

}
