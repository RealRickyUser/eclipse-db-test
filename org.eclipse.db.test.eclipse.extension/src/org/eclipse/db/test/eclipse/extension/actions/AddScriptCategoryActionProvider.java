package org.eclipse.db.test.eclipse.extension.actions;

public class AddScriptCategoryActionProvider extends BaseActionProvider<AddScriptCategoryAction> {

	public AddScriptCategoryActionProvider() {
		setTexts("Создать папку", "Создание папки", "Введите название новой папки");
	}
	
	@Override
	protected String getDefaultValue() {
		return "Новая папка";
	}
	

}
