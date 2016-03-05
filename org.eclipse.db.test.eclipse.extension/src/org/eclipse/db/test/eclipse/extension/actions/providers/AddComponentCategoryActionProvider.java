package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddComponentCategoryAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddComponentCategoryActionProvider extends BaseActionProvider<AddComponentCategoryAction> {

	public AddComponentCategoryActionProvider() {
		setTexts("Создать папку", "Создание папки", "Введите название новой папки");
	}
	
	@Override
	protected String getDefaultValue() {
		return "Новая папка";
	}
}
