package org.eclipse.db.test.eclipse.extension.run;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class RunLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public Image getImage(Object element) {
		BaseTreeItem item = (BaseTreeItem) element;
		if (item != null)
			return item.getImage();
		return null;
	}

	@Override
	public String getText(Object element) {
		BaseTreeItem item = (BaseTreeItem) element;
		if (item != null)
			return item.getText();
		return null;
	}

}
