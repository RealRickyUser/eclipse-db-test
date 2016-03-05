package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.misc.LengthValudator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;

public abstract class BaseDialogSelectionListenerAction extends BaseSelectionListenerAction {
	
	private String _defaultValue;
	
    public BaseDialogSelectionListenerAction(String title) {
    	super(title);
	}
    
    @Override
    protected void openDialog(Shell activeShell) {
		InputDialog dialog = new InputDialog(activeShell, _title, _comment, _defaultValue, new LengthValudator(1, 50));
		if (dialog.open() != InputDialog.OK)
			return;
    	doWork(dialog.getValue());
    }
    
	protected abstract void doWork(String value);
	
	public void setDefaultValue(String value) {
		_defaultValue = value;
	}

}
