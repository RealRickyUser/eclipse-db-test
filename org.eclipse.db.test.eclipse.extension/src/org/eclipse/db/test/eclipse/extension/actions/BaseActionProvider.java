package org.eclipse.db.test.eclipse.extension.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import java.lang.reflect.Type;

public abstract class BaseActionProvider<T extends BaseSelectionListenerAction> extends CommonActionProvider {
	@SuppressWarnings("unused")
	private ICommonViewerWorkbenchSite viewSite = null;
	private boolean contribute = false;
	protected T _action;
	
	private String _menuTitle;
	private String _dialogTitle;
	private String _dialogMessage;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			viewSite = (ICommonViewerWorkbenchSite) aSite.getViewSite();
			try {
				/*_action = (T) ((Class)((ParameterizedType)this.getClass().
					       getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();*/
				ParameterizedType ptype = (ParameterizedType) getClass().getGenericSuperclass();
				Type argType = ptype.getActualTypeArguments()[0];
				Class<?> clazz = (Class<?>)argType;
				_action = (T) clazz.getConstructor(String.class).newInstance(_menuTitle);
			} catch (InstantiationException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException e) {
				e.printStackTrace();
			}
			_action.setTexts(_dialogTitle, _dialogMessage);
			contribute = true;
		}
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (!contribute || getContext().getSelection().isEmpty())
			return;
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		_action.selectionChanged(selection);
		if (_action.isEnabled()) {
			if (BaseDialogSelectionListenerAction.class.isAssignableFrom(_action.getClass()))
				((BaseDialogSelectionListenerAction)_action).setDefaultValue(getDefaultValue());
			menu.insertAfter(ICommonMenuConstants.GROUP_OPEN, _action);
		}
	}
	
	protected void setTexts(String menuTitle, String dialogTitle, String dialogMessage) {
		_menuTitle = menuTitle;
		_dialogTitle = dialogTitle;
		_dialogMessage = dialogMessage;
	}
	
	protected abstract String getDefaultValue();
}
