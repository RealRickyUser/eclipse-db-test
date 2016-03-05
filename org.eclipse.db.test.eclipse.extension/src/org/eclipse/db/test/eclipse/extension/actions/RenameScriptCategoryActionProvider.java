package org.eclipse.db.test.eclipse.extension.actions;

import org.eclipse.db.test.eclipse.extension.project.ProjectScriptCategory;

public class RenameScriptCategoryActionProvider extends BaseActionProvider<RenameScriptCategoryAction> {

	public RenameScriptCategoryActionProvider() {
		setTexts("�������������", "��������������", "������� ����� ��������");
	}
	
	@Override
	protected String getDefaultValue() {
		ProjectScriptCategory cat = (ProjectScriptCategory) _action.getStructuredSelection().getFirstElement();
		return cat == null ? null : cat.getText();
	}
}
