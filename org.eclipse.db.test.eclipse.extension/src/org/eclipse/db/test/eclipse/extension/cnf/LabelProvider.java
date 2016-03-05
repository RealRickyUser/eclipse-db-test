package org.eclipse.db.test.eclipse.extension.cnf;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.db.test.eclipse.extension.project.ICustomProjectElement;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class LabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object element) {
        Image image = null;
 
        if (ICustomProjectElement.class.isInstance(element)) {
            image = ((ICustomProjectElement)element).getImage();
        }
        // else ignore the element
 
        return image;
	}

	@Override
	public String getText(Object element) {
        String text = "test";//NON-NLS-1$
        if (ICustomProjectElement.class.isInstance(element)) {
            text = ((ICustomProjectElement)element).getText();
        } else if (IWorkspaceRoot.class.isInstance(element)) {
        	text = "SQL Test Project Explorer";
        }
 
        return text;
	}

}
