package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddComponentAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddComponentActionProvider extends BaseActionProvider<AddComponentAction> {

	public AddComponentActionProvider() {
		setTexts("Создать компонент", "Создание компонента", "Введите название нового компонента");
	}
	
	@Override
	protected String getDefaultValue() {
		return "Новый компонент";
	}
}
