package org.eclipse.db.test.eclipse.extension.cnf;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.db.test.eclipse.extension.natures.SqlTestProjectNature;
import org.eclipse.db.test.eclipse.extension.project.CustomProjectParent;
import org.eclipse.db.test.eclipse.extension.project.ICustomProjectElement;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ContentProvider implements ITreeContentProvider {

    private static final Object[] NO_CHILDREN = {};
    private List<CustomProjectParent> _customProjectParents;
    
    public ContentProvider() {
    	
    }
    
	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
	}

    @Override
    public Object[] getChildren(Object parentElement) {
        Object[] children = null;
        if (parentElement instanceof IWorkspaceRoot) {
        	/*if (_customProjectParents == null)*/ {
        		_customProjectParents = getProjects((IWorkspaceRoot)parentElement);
        	}
        	return _customProjectParents.toArray();
        }
        if (CustomProjectParent.class.isInstance(parentElement)) {
        	CustomProjectParent proj = (CustomProjectParent)parentElement;
            children = proj.getChildren();
        }
        else if (ICustomProjectElement.class.isInstance(parentElement)) {
        	children = ((ICustomProjectElement) parentElement).getChildren();
        } else {
            children = NO_CHILDREN;
        }
 
        return children;
    }
    
    private List<CustomProjectParent> getProjects(IWorkspaceRoot root) {
    	IProject[] projects = root.getProjects();
    	List<CustomProjectParent> items=new ArrayList<CustomProjectParent>();
    	for (IProject project : projects) {
			try {
				if (!project.hasNature(SqlTestProjectNature.NATURE_ID))
					continue;
			} catch (CoreException e) {
				e.printStackTrace();
				continue;
			}
			CustomProjectParent proj = new CustomProjectParent(project);
			items.add(proj);
		}
    	return items;
    }

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ICustomProjectElement) {
			ICustomProjectElement item = (ICustomProjectElement) element;
			return item.hasChildren();
		}
		return true;
	}

}
