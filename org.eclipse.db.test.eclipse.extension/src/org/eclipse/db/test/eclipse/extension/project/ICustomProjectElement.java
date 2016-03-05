package org.eclipse.db.test.eclipse.extension.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.graphics.Image;

public interface ICustomProjectElement {
	
    Image getImage();
    
    Object[] getChildren();
 
    String getText();
 
    boolean hasChildren();
 
    IProject getProject();
    void setProject(IProject project);
 
    Object getParent();
    
    void setParent(Object parent);
    
    int getWeigth();
    
    int getId();
}
