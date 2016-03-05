package org.eclipse.db.test.eclipse.extension.misc;

import org.eclipse.jface.dialogs.IInputValidator;

public class LengthValudator implements IInputValidator {
	private int _minValue;
	private int _maxValue;
	
	public LengthValudator(int minValue, int maxValue) {
		_minValue = minValue;
		_maxValue = maxValue;
	}

	@Override
	public String isValid(String newText) {
		if (newText == null)
			return "������� �����";
		if (_minValue >= 0 && newText.length() < _minValue)
			return String.format("������ ������ ���� �� ������ %d ��������", _minValue);
		if (_maxValue >= 0 && newText.length() > _maxValue)
			return String.format("������ ������ ���� �� ������� %d ��������", _maxValue);
		return null;
	}

}
