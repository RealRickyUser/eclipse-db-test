package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.AddTestCategoryAction;
import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;

public class AddTestCategoryActionProvider extends BaseActionProvider<AddTestCategoryAction>  {
	
	public AddTestCategoryActionProvider() {
		setTexts("������� �����", "�������� �����", "������� �������� ����� �����");
	}
	
	@Override
	protected String getDefaultValue() {
		return "����� �����";
	}
}
