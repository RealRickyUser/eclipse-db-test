package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;

public abstract class BaseSelectionListenerAction extends SelectionListenerAction {
	protected final String ID = PlatformUI.PLUGIN_ID + "." + this.getClass().getName();
	protected boolean _enabled;
	protected String _title;
	protected String _comment;
	private String _imagePath;
	private ImageDescriptor _imageDescriptor;
	
	protected BaseSelectionListenerAction(String text) {
		super(text);
	}
	
	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		_enabled = selection.size() == 1;
		return super.updateSelection(selection);
	}
	
	@Override
	public boolean isEnabled() {
		return _enabled;
	}
	
	@Override
    public void run() {
		Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		openDialog(activeShell);
	}

	protected abstract void openDialog(Shell activeShell);
	
    public void setTexts(String title, String comment) {
    	_title = title;
    	_comment = comment;
    }
    
    protected void setImage(String img) {
    	_imagePath = img;
    }
    
    @Override
    public ImageDescriptor getImageDescriptor() {
    	if (_imageDescriptor == null && _imagePath != null) {
    		_imageDescriptor = ImageDescriptor.createFromImage(Activator.getImage(_imagePath));
    	}
    	return _imageDescriptor;
    }
    
}
