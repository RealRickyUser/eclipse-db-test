package org.eclipse.db.test.eclipse.extension.run;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

public abstract class BaseRunTask implements IRunnableWithProgress {

	private BaseTreeItem _root;
	
	@Override
	public abstract void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException;
	
	public void setRoot(BaseTreeItem item) {
		_root = item;
	}
	
	protected BaseTreeItem getRoot() {
		return _root;
	}
	
	protected void refresh(BaseTreeItem root) {
		final BaseTreeItem _root  = root;
		 Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				 RunTestView view = RunService.getView();
				 view.refresh(_root);
			}
		});
	}
	
	protected void refreshSync(BaseTreeItem root) {
		final BaseTreeItem _root  = root;
		 Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				 RunTestView view = RunService.getView();
				 view.refresh(_root);
			}
		});
	}
	
	protected static BaseTreeItem addChild(BaseTreeItem root, String text, String image) {
		BaseTreeItem item = new BaseTreeItem(root);
		item.setText(text);
		item.setImagePath(image);
		root.addChild(item);
		return item;
	}
	
	protected static boolean hasChanged(Object oldVal, Object newVal) {
		boolean use = false; 
		if (oldVal != newVal) {
			if (oldVal == null && newVal != null)
				use = true;
			else if (oldVal != null && newVal == null)
				use = true;
			else if (!oldVal.toString().equals(newVal.toString()))
				use = true;
		}
		return use;
	}

}
