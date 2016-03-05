package org.eclipse.db.test.common.domain;

/**
 * ����� ������ ��������� ���������� �������
 * @author sbt-blinchikov-ae
 *
 */
public class ScriptExecutionResult {
	private Exception exception;
	private boolean _hasDifference; 
	private boolean _canceled;

	/**
	 * ������������� ������
	 * @param exception - ������, ������� ����� ��������� � ������
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	/**
	 * ���������� ������� ���������� ����������.
	 * @return
	 */
	public Boolean isSuccess() {
		return exception == null && !isHasDifference() && !isCanceled();
	}

	public boolean isHasDifference() {
		return _hasDifference;
	}

	public void setHasDifference(boolean hasDifference) {
		_hasDifference = hasDifference;
	}
	
	public String getMessage() {
		if (exception != null)
			return exception.getMessage();
		if (isHasDifference())
			return "������� �������� � ��������� ����������";
		if (isCanceled())
			return "��������";
		return null;
	}

	public boolean isCanceled() {
		return _canceled;
	}

	public void setCanceled(boolean canceled) {
		_canceled = canceled;
	}

}
