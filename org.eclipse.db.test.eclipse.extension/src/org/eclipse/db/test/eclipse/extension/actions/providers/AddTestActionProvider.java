package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddTestAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddTestActionProvider extends BaseActionProvider<AddTestAction> {
	
	public AddTestActionProvider() {
		setTexts("������� ����", "�������� �����", "������� �������� ������ �����");
	}
	
	@Override
	protected String getDefaultValue() {
		return "����� ����";
	}
}
