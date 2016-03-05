package org.eclipse.db.test.common.domain;

/**
 * Класс хранит результат выполнения скрипта
 * @author sbt-blinchikov-ae
 *
 */
public class ScriptExecutionResult {
	private Exception exception;
	private boolean _hasDifference; 
	private boolean _canceled;

	/**
	 * Устанавливает ошибку
	 * @param exception - ошибка, которая будет храниться в классе
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	/**
	 * Возвращает признак успешности выполнения.
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
			return "Найдены различия в значениях переменных";
		if (isCanceled())
			return "Отменено";
		return null;
	}

	public boolean isCanceled() {
		return _canceled;
	}

	public void setCanceled(boolean canceled) {
		_canceled = canceled;
	}

}
