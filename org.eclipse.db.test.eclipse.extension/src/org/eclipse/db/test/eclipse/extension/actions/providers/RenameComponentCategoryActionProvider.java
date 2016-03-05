package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;
import org.eclipse.db.test.eclipse.extension.actions.RenameComponentCategoryAction;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentCategory;

public class RenameComponentCategoryActionProvider extends BaseActionProvider<RenameComponentCategoryAction> {

	public RenameComponentCategoryActionProvider() {
		setTexts("Переименовать", "Переименование", "Введите новое название");
	}
	
	@Override
	protected String getDefaultValue() {
		ProjectComponentCategory cat = (ProjectComponentCategory) _action.getStructuredSelection().getFirstElement();
		return cat == null ? null : cat.getText();
	}

}
