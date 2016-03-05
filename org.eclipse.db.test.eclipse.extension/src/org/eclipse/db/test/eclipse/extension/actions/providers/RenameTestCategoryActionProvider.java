package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;
import org.eclipse.db.test.eclipse.extension.actions.RenameTestCategoryAction;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestCategory;

public class RenameTestCategoryActionProvider extends BaseActionProvider<RenameTestCategoryAction> {
	public RenameTestCategoryActionProvider() {
		setTexts("Переименовать", "Переименование", "Введите новое название");
	}
	
	@Override
	protected String getDefaultValue() {
		ProjectTestCategory cat = (ProjectTestCategory) _action.getStructuredSelection().getFirstElement();
		return cat == null ? null : cat.getText();
	}
}
