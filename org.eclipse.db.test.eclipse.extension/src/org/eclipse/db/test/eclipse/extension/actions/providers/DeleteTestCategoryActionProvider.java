package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;
import org.eclipse.db.test.eclipse.extension.actions.DeleteTestCategoryAction;

public class DeleteTestCategoryActionProvider extends BaseActionProvider<DeleteTestCategoryAction> {
	public DeleteTestCategoryActionProvider() {
		setTexts("������� �����", "�������� �����", "����� �������?");
	}
	
	@Override
	protected String getDefaultValue() {
		return null;
	}
}
