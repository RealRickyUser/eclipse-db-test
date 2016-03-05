package org.eclipse.db.test.eclipse.extension.actions;

public class AddScriptActionProvider extends BaseActionProvider<AddScriptAction> {

	public AddScriptActionProvider() {
		setTexts("Создать скрипт", "Создание скрипта", "Введите название нового скрипта");
	}
	
	@Override
	protected String getDefaultValue() {
		return "Новый скрипт";
	}

}
