package org.eclipse.db.test.eclipse.extension.actions;

public class DeleteScriptActionProvider extends BaseActionProvider<DeleteScriptAction> {

	public DeleteScriptActionProvider() {
		setTexts("������� ������", "�������� �������", "����� �������?");
	}
	
	@Override
	protected String getDefaultValue() {
		return null;
	}

}
