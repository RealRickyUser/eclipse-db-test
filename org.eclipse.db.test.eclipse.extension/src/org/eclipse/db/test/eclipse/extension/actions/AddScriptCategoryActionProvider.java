package org.eclipse.db.test.eclipse.extension.actions;

public class AddScriptCategoryActionProvider extends BaseActionProvider<AddScriptCategoryAction> {

	public AddScriptCategoryActionProvider() {
		setTexts("������� �����", "�������� �����", "������� �������� ����� �����");
	}
	
	@Override
	protected String getDefaultValue() {
		return "����� �����";
	}
	

}
