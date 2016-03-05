package org.eclipse.db.test.eclipse.extension.actions;

public class DeleteScriptCategoryActionProvider extends BaseActionProvider<DeleteScriptCategoryAction> {
	
	public DeleteScriptCategoryActionProvider() {
		setTexts("Удалить папку", "Удаление папки", "Точно удалить?");
	}

	@Override
	protected String getDefaultValue() {
		return null;
	}
	
	

}
