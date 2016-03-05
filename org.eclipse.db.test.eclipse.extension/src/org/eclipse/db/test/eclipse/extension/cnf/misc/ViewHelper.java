package org.eclipse.db.test.eclipse.extension.cnf.misc;

import org.eclipse.db.test.eclipse.extension.cnf.ProjectExplorerNavigator;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ViewHelper {

	public static IViewPart getView(String viewId) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView(viewId);
		return view;
	}
	
	public static void refreshExplorer(Object item) {
		IViewPart view = getView(ProjectExplorerNavigator.ID);
		if (view == null)
			return;
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		if (nav != null)
			nav.refresh(item);
	}
	
	public static void selectExplorer(Object item) {
		IViewPart view = getView(ProjectExplorerNavigator.ID);
		if (view == null)
			return;
		ProjectExplorerNavigator nav = (ProjectExplorerNavigator) view;
		if (nav != null)
			nav.select(item);
	}
	
	public static void refreshProperties() {
        /*PropertySheet part = (PropertySheet) ViewHelper.getView("org.eclipse.ui.views.PropertySheet");
        PropertySheetPage page = (PropertySheetPage) part.getCurrentPage();
        page.refresh();*/
	}
	
	public static void showView(String id) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(id);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
