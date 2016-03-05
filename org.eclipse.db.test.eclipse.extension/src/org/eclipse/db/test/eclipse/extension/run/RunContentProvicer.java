package org.eclipse.db.test.eclipse.extension.run;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class RunContentProvicer implements ITreeContentProvider {

	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return ((BaseTreeItem) inputElement).getChildren();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return getElements(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		return ((BaseTreeItem) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		return ((BaseTreeItem) element).hasChildren();
	}


}
