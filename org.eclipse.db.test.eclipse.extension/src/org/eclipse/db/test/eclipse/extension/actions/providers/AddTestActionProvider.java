package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddTestAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddTestActionProvider extends BaseActionProvider<AddTestAction> {
	
	public AddTestActionProvider() {
		setTexts("Создать тест", "Создание теста", "Введите название нового теста");
	}
	
	@Override
	protected String getDefaultValue() {
		return "Новый тест";
	}
}
