package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public abstract class BaseConfirmSelectionListenerAction extends BaseSelectionListenerAction {
	
    public BaseConfirmSelectionListenerAction(String title) {
    	super(title);
	}
    
    @Override
    protected void openDialog(Shell activeShell) {
		if (!MessageDialog.openConfirm(activeShell, _title, _comment))
			return;
    	doWork();
    }
    
	protected abstract void doWork();
}
