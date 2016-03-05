package org.eclipse.db.test.eclipse.extension.run;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.domain.ParameterDirection;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptExecutionResult;
import org.eclipse.db.test.common.domain.ScriptParameter;
import org.eclipse.db.test.common.service.ExecutionService;

public class RunScriptTask extends BaseRunTask {
	private int _stateTimeout = 100;

	private Script _script;
	private DbServerInfo _info;
	private ScriptExecutionResult _result;
	
	public RunScriptTask(Script script, DbServerInfo info) {
		_script = script;
		_info=info;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		printInputParams();
		while (!monitor.isCanceled()) {
			_result = ExecutionService.execute(_info, _script);
			if (!_result.isSuccess() || _script.isAction() || _script.isStateSuccess() || monitor.isCanceled())
				break;
			Thread.sleep(_stateTimeout);
		}
		printOutputParams();
		addResult(monitor.isCanceled());
	}
	
	public ScriptExecutionResult getResult() {
		return _result;
	}
	
	private void printInputParams() {
		BaseTreeItem root = new BaseTreeItem(getRoot());
		root.setText("Входные параметры");
		root.setImagePath("images/right-arrow.png");
		for (ScriptParameter p : _script.getParameters()) {
			if (p.getDirection() == ParameterDirection.Out)
				continue;
			BaseTreeItem param = new BaseTreeItem(root);
			Object value = p.getValue();
			if (value == null)
				value = "<null>";
			param.setText(p.getName() +"=" + value.toString());
			param.setImagePath("images/right-arrow.png");
			root.addChild(param);
		}
		getRoot().addChild(root);
		refresh(getRoot());
	}
	
	private void printOutputParams() {
		BaseTreeItem root = new BaseTreeItem(getRoot());
		root.setText("Выходные параметры");
		root.setImagePath("images/left-arrow.png");
		for (ScriptParameter p : _script.getParameters()) {
			if (p.getDirection() == ParameterDirection.In)
				continue;
			BaseTreeItem param = new BaseTreeItem(root);
			Object value = p.getValue();
			if (value == null)
				value = "<null>";
			param.setText(p.getName() +"=" + value.toString());
			param.setImagePath("images/left-arrow.png");
			root.addChild(param);
		}
		getRoot().addChild(root);
		refresh(getRoot());
	}
	
	private void addResult(boolean canceled) {
		BaseTreeItem item = addChild(getRoot(), "Скрипт завершен", null);
		String res = null;
		if (canceled)
			res = "отмена";
		else
			res = _result.isSuccess() ? "успех" : "ошибка";
		String status = "Результат: " + res;
		String image = "images/" + ( _result != null && _result.isSuccess() ? "success" : "fail") + ".png";
		BaseTreeItem result = addChild(item, status, image);
		item.setImagePath(image);
		if (_result == null)
			_result = new ScriptExecutionResult();
		if (canceled)
			_result.setCanceled(canceled);
		if (_result == null || !_result.isSuccess()) {
			String err = canceled ? "отменено" : _result.getMessage();
			addChild(result, err, "images/cancel.png");
		}
		getRoot().setImagePath(image);
		refresh(getRoot());
	}
	
}
