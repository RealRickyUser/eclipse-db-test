package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;
import org.eclipse.db.test.eclipse.extension.actions.DeleteComponentAction;

public class DeleteComponentActionProvider extends BaseActionProvider<DeleteComponentAction> {
	
	public DeleteComponentActionProvider() {
		setTexts("Удалить компонент", "Удаление компонента", "Точно удалить?");
	}
	
	@Override
	protected String getDefaultValue() {
		return null;
	}
}
