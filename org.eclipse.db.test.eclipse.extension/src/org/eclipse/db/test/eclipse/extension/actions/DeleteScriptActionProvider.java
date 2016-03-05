package org.eclipse.db.test.eclipse.extension.actions;

public class DeleteScriptActionProvider extends BaseActionProvider<DeleteScriptAction> {

	public DeleteScriptActionProvider() {
		setTexts("Удалить скрипт", "Удаление скрипта", "Точно удалить?");
	}
	
	@Override
	protected String getDefaultValue() {
		return null;
	}

}
