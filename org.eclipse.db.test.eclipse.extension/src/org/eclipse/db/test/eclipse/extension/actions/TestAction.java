package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionListenerAction;

public class TestAction extends SelectionListenerAction {

	public static final String ID = PlatformUI.PLUGIN_ID + ".TestAction";
			
	protected TestAction(String text) {
		super(text);
	}
	
    public TestAction(IWorkbenchPage page) {
        this("Тестовое действие");
        setId(ID);
    }
    
    
    @Override
    public void run() {
    	super.run();
    }
	    
	    

}
