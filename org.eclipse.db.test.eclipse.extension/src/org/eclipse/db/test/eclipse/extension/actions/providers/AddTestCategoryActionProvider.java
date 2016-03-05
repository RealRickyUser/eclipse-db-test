package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddTestCategoryAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddTestCategoryActionProvider extends BaseActionProvider<AddTestCategoryAction>  {
	
	public AddTestCategoryActionProvider() {
		setTexts("Создать папку", "Создание папки", "Введите название новой папки");
	}
	
	@Override
	protected String getDefaultValue() {
		return "Новая папка";
	}
}
