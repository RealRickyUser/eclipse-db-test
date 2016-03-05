package org.eclipse.db.test.eclipse.extension.run;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class RunTestView extends ViewPart {

	public static final String ID = "org.eclipse.db.test.eclipse.extension.RunTestView";
	
	private TreeViewer _viewer;
	
	private RootRunInput _root;
	
	public RunTestView() {
		_root = new RootRunInput();
	}

	@Override
	public void createPartControl(Composite parent) {
		_viewer = new TreeViewer(parent);
		_viewer.setContentProvider(new RunContentProvicer());
		_viewer.setLabelProvider(new RunLabelProvider());
		_viewer.setInput(_root);
	}

	@Override
	public void setFocus() {
		_viewer.getTree().setFocus();
	}

	public BaseTreeItem createNewItem(String name) {
		BaseTreeItem item = new BaseTreeItem(null);
		item.setText(name);
		_root.insertFirstChild(item);
		refresh(_root);
		return item;
	}
	
	public void refresh(Object from) {
		_viewer.refresh(from);
	}
	
}
