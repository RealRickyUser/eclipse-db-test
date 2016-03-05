package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddComponentCategoryAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddComponentCategoryActionProvider extends BaseActionProvider<AddComponentCategoryAction> {

	public AddComponentCategoryActionProvider() {
		setTexts("������� �����", "�������� �����", "������� �������� ����� �����");
	}
	
	@Override
	protected String getDefaultValue() {
		return "����� �����";
	}
}
