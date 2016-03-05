package org.eclipse.db.test.eclipse.extension.editors;

import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class SqlScriptEditorInput implements IEditorInput {
	
	private ProjectScriptItem _item;

	public SqlScriptEditorInput(ProjectScriptItem item) {
		_item = item;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return true;
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
	
	private int getId() {
		return _item.getId();
	}
	
	@Override
	public boolean equals(Object item) {
		if (item == null)
			return false;
		if (!(item instanceof SqlScriptEditorInput))
			return false;
		SqlScriptEditorInput i = (SqlScriptEditorInput) item;
		return (getId() == i.getId());
	}
	
	public String getContent() {
		return _item.getContent();
	}
	
	public ProjectScriptItem getItem() {
		return _item;
	}

}
