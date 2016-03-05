package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class TestActionProvider extends CommonActionProvider {


	private TestAction openFileAction;

	private ICommonViewerWorkbenchSite viewSite = null;

	private boolean contribute = false;
	
	public TestActionProvider() {
	}
	
	protected  boolean filterAction(IAction action) {
		return super.filterAction(action);
	}
	
	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
	}
	
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			viewSite = (ICommonViewerWorkbenchSite) aSite.getViewSite();
			openFileAction = new TestAction(viewSite.getPage());
			contribute = true;
		}
	}
	
	@Override
	public void updateActionBars() {
		super.updateActionBars();
	}
	
	@Override
	public ActionContext getContext() {
		return super.getContext();
	}
	
	@Override
	public void saveState(IMemento aMemento) {
		super.saveState(aMemento);
	}
	
	@Override
	public void restoreState(IMemento aMemento) {
		super.restoreState(aMemento);
	}
	
	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (!contribute || getContext().getSelection().isEmpty()) {
			return;
		}

		IStructuredSelection selection = (IStructuredSelection) getContext()
				.getSelection();

		openFileAction.selectionChanged(selection);
		//if (openFileAction.isEnabled()) {
			menu.insertAfter(ICommonMenuConstants.GROUP_OPEN, openFileAction);
		//}
		//addOpenWithMenu(menu);
	}
	/*
	private void addOpenWithMenu(IMenuManager aMenu) {
		IStructuredSelection ss = (IStructuredSelection) getContext()
				.getSelection();

		if (ss == null || ss.size() != 1) {
			return;
		}

		Object o = ss.getFirstElement();

		// first try IResource
		IAdaptable openable = (IAdaptable) AdaptabilityUtility.getAdapter(o,
				IResource.class);
		// otherwise try ResourceMapping
		if (openable == null) {
			openable = (IAdaptable) AdaptabilityUtility.getAdapter(o,
					ResourceMapping.class);
		} else if (((IResource) openable).getType() != IResource.FILE) {
			openable = null;
		}

		if (openable != null) {
			// Create a menu flyout.
			IMenuManager submenu = new MenuManager(
					WorkbenchNavigatorMessages.OpenActionProvider_OpenWithMenu_label,
					ICommonMenuConstants.GROUP_OPEN_WITH);
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_TOP));
			//submenu.add(new OpenWithMenu(viewSite.getPage(), openable)); 
			submenu.add(openFileAction);
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_ADDITIONS));

			// Add the submenu.
			if (submenu.getItems().length > 2 && submenu.isEnabled()) {
				aMenu.appendToGroup(ICommonMenuConstants.GROUP_OPEN_WITH,
						submenu);
			}
		}
	}*/

}
