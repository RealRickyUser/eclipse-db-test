package org.eclipse.db.test.eclipse.extension.cnf.misc;

import java.text.Collator;

import org.eclipse.db.test.eclipse.extension.project.ICustomProjectElement;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class CategorySorter extends ViewerSorter {

	public CategorySorter() {
	}

	public CategorySorter(Collator collator) {
		super(collator);
	}

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
    	if (e1 instanceof ICustomProjectElement && e2 instanceof ICustomProjectElement) {
	        int val1 = ((ICustomProjectElement)e1).getWeigth();
	        int val2 = ((ICustomProjectElement)e2).getWeigth();
	        if (val1 < val2)
	        	return -1;
	        if (val1 == val2)
	        	return 0;
	        if (val1 > val2)
	        	return 1;
    	}
        int result = -1;
        return result;
    }
	
}
