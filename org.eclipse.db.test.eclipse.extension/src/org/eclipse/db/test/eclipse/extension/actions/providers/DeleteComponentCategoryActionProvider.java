package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;
import org.eclipse.db.test.eclipse.extension.actions.DeleteComponentCategoryAction;

public class DeleteComponentCategoryActionProvider extends BaseActionProvider<DeleteComponentCategoryAction> {

	public DeleteComponentCategoryActionProvider() {
		setTexts("������� �����", "�������� �����", "����� �������?");
	}
	
	@Override
	protected String getDefaultValue() {
		return null;
	}

}
