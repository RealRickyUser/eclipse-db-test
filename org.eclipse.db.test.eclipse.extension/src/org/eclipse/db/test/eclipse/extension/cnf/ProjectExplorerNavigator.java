package org.eclipse.db.test.eclipse.extension.cnf;

import org.eclipse.db.test.eclipse.extension.handlers.OpenComponentHandler;
import org.eclipse.db.test.eclipse.extension.handlers.OpenScriptEditorHandler;
import org.eclipse.db.test.eclipse.extension.handlers.OpenTestEditorHandler;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestItem;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.navigator.CommonNavigator;

public class ProjectExplorerNavigator extends CommonNavigator {
	
	public static final String ID = "org.eclipse.db.test.eclipse.sqlprojectview";
	
	@Override
	public void createPartControl(Composite aParent) {
		super.createPartControl(aParent);
		super.getCommonViewer().addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection sel = (TreeSelection) event.getSelection();
				if (sel.isEmpty())
					return;
				IHandlerService handlerService = getSite().getService(IHandlerService.class);
				String id = null;
				if (sel.getFirstElement() instanceof ProjectScriptItem)
					id = OpenScriptEditorHandler.ID;
				else if (sel.getFirstElement() instanceof ProjectComponentItem)
					id = OpenComponentHandler.ID;
				else if (sel.getFirstElement() instanceof ProjectTestItem)
					id = OpenTestEditorHandler.ID;
				if (id == null)
					return;
				try {
		            handlerService.executeCommand(id, null);
		          } catch (Exception ex) {
		            throw new RuntimeException(ex.getMessage());
		          }
			}
		});
	}
	
	public void refresh() {
		getCommonViewer().refresh();
	}
	
	public void refresh(Object from) {
		getCommonViewer().refresh(from);
	}
	
	public void select(Object item) {
		getCommonViewer().setSelection(new StructuredSelection(item));
	}
}
