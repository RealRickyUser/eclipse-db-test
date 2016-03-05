package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddComponentAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddComponentActionProvider extends BaseActionProvider<AddComponentAction> {

	public AddComponentActionProvider() {
		setTexts("������� ���������", "�������� ����������", "������� �������� ������ ����������");
	}
	
	@Override
	protected String getDefaultValue() {
		return "����� ���������";
	}
}
