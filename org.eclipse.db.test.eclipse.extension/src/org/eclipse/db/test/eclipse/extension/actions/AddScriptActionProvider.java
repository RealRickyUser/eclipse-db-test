package org.eclipse.db.test.eclipse.extension.actions;

public class AddScriptActionProvider extends BaseActionProvider<AddScriptAction> {

	public AddScriptActionProvider() {
		setTexts("������� ������", "�������� �������", "������� �������� ������ �������");
	}
	
	@Override
	protected String getDefaultValue() {
		return "����� ������";
	}

}
