package org.eclipse.db.test.eclipse.extension.actions.providers;

import org.eclipse.db.test.eclipse.extension.actions.BaseActionProvider;
import org.eclipse.db.test.eclipse.extension.actions.DeleteTestAction;

public class DeleteTestActionProvider extends BaseActionProvider<DeleteTestAction> {

	public DeleteTestActionProvider() {
		setTexts("������� ����", "�������� �����", "����� �������?");
	}
	
	@Override
	protected String getDefaultValue() {
		return null;
	}
}
