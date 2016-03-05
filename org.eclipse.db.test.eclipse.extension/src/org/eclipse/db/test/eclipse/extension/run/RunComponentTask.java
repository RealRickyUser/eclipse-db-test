package org.eclipse.db.test.eclipse.extension.run;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.common.domain.ComponentExecutionContext;
import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.common.domain.ParameterItem;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptExecutionResult;
import org.eclipse.db.test.common.domain.ScriptItem;
import org.eclipse.db.test.eclipse.extension.project.editor.ComponentParameterTableItem;

public class RunComponentTask extends BaseRunTask {
	private ComponentExecutionContext _context;
	private Component _component;
	private ScriptExecutionResult _result;
	private List<OutputParameterInfo> _outParams;
	
	private BaseTreeItem _scriptsRoot;
	
	
	public RunComponentTask(ComponentExecutionContext context, Component component) {
		_context = context;
		_component = component;
		_outParams = new ArrayList<OutputParameterInfo>();
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		int steps = _component.getScripts().size();
		monitor.beginTask("Выполнение компонента", steps * 100);
		if (!validate()) {
			monitor.done();
			return;
		}
		createLocalParams();
		printInputParams();
		createScriptsGroup();
		for(ScriptItem item : _component.getScripts()) {
			if (monitor.isCanceled())
				break;
			BaseTreeItem si = addScript(item);
			monitor.subTask("скрипт ["+ item.getName() + "]");
			monitor.worked(10);
			Script script = _context.getScript(item.getId());
			//fill params before script execution
			preProcessParams(item);
			monitor.worked(10);
			//create script execution task
			RunScriptTask task = new RunScriptTask(script, _context.getExecServerInfo());
			task.setRoot(si);
			//execute script
			task.run(new SubProgressMonitor(monitor, 100));
			monitor.worked(70);
			//get result
			_result = task.getResult();
			if (!_result.isSuccess())
				break;
			//save params after script excuted
			postProcessParams(item);
			monitor.worked(10);
		}
		monitor.done();
		printOutputParams();
		processOutParams();
		setCompleted();
	}
	
	private void createLocalParams() {
		for (ComponentParameter param : _component.getParameters()) {
			if (param.getContext() != ParameterContext.Local && param.getContext() != ParameterContext.Constant)
				continue;
			if (param.getContext() != ParameterContext.Constant) {
				if (_context.getParams().contains(param.getContext(), param.getName()))
					continue;
				_context.getParams().add(param.getContext(), param.getName(), null);
			} else {
				_context.getParams().add(param.getContext(), param.getName(), param.getValue());
			}
		}
	}

	private void processOutParams() {
		if (_outParams.size() == 0)
			return;
		List<String> processedParams = new ArrayList<String>();
		BaseTreeItem root = addChild(getRoot(), "Сравнение параметров", null);
		for (OutputParameterInfo info : _outParams) {
			boolean changed = hasChanged(info.getValue(), info.getNewValue());
			if (!changed)
				continue;
			if (processedParams.contains(info.getName()))
				continue;
			String image = null;
			BaseTreeItem child = addChild(root, "Параметр [" + info.getName() + "]", null);
			Object value = info.getContext() == ParameterContext.Etalon ? info.getNewValue() : _context.getParams().get(ParameterContext.Etalon, info.getName());
			if (value == null)
				value = "<null>";
			/*BaseTreeItem etalon =*/ addChild(child, "[" + ComponentParameterTableItem.getParamContextName(ParameterContext.Etalon) + "] " + value.toString(), null);
			Object newvalue = info.getContext() == ParameterContext.Etalon ? _context.getParams().get(ParameterContext.Runtime, info.getName()) : info.getNewValue();
			if (newvalue == null)
				newvalue = "<null>";
			/*BaseTreeItem runtime =*/ addChild(child, "[" + ComponentParameterTableItem.getParamContextName(ParameterContext.Runtime) + "] " + newvalue.toString(), null);
			changed = hasChanged(value, newvalue);
			if (changed)
				_result.setHasDifference(true);
			image = "images/" + (changed ? "fail" : "success") + ".png";
			child.setImagePath(image);
		}
		refresh(getRoot());
	}
	
	private boolean validate() {
		//check all scripts
		for(ScriptItem item : _component.getScripts()) {
			if (!_context.containsScript(item.getId()))
				return false;
		}
		return true;
	}
	
	private void preProcessParams(ScriptItem item) {
		Script script = _context.getScript(item.getId());
		script.clearParamValues();
		for (ParameterItem pi : item.getParameters()) {
			if (!script.isInputParameter(pi.getMapped()))
				continue;
			if (!_context.getParams().contains(pi.getContext(), pi.getName()))
				continue;
			Object value = _context.getParams().get(pi.getContext(), pi.getName());
			script.setParameterValue(pi.getMapped(), value);
		}
	}
	
	private void postProcessParams(ScriptItem item) {
		_outParams.clear();
		Script script = _context.getScript(item.getId());
		for (ParameterItem pi : item.getParameters()) {
			if (!script.isOutputParameter(pi.getMapped()))
				continue;
			//parameter not mapped? skipping.
			if (pi.getContext() == null || !_context.getParams().contains(pi.getContext(), pi.getName()))
				continue;
			//save output parameters
			saveOutParams(pi.getName(), pi.getContext(), script.getParameterValue(pi.getMapped()));
		}
	}
	
	private void saveOutParams(String name, ParameterContext ctx, Object value) {
		if (ctx == ParameterContext.Etalon || ctx == ParameterContext.Runtime) {
			_outParams.add(new OutputParameterInfo(name, ctx, _context.getParams().get(ctx, name), value));
		}
		_context.getParams().set(ctx, name, value);
	}
	
	private void createScriptsGroup() {
		_scriptsRoot = new BaseTreeItem(getRoot());
		_scriptsRoot.setText("Скрипты");
		getRoot().addChild(_scriptsRoot);
		_scriptsRoot.setImagePath("images/script.png");
		refresh(getRoot());
	}
	
	private BaseTreeItem addScript(ScriptItem item) {
		BaseTreeItem script = new BaseTreeItem(_scriptsRoot);
		script.setText("Скрипт [" + item.getName() + "]");
		//script.setImagePath("images/script.png");
		_scriptsRoot.addChild(script);
		refresh(_scriptsRoot);
		return script;
		
	}
	
	private void setCompleted() {
		BaseTreeItem item = addChild(getRoot(), "Компонент завершен", null);
		String status = "Результат: " + (_result.isSuccess() ? "успех" : "ошибка");
		String image = "images/" + (_result.isSuccess() ? "success" : "fail") + ".png";
		BaseTreeItem result = addChild(item, status, image);
		item.setImagePath(image);
		if (!_result.isSuccess()) {
			addChild(result, _result.getMessage(), "images/cancel.png");
		}
		getRoot().setImagePath(image);
		refresh(getRoot());
	}
	
	private void printInputParams() {
		BaseTreeItem root = addChild(getRoot(), "Начальные параметры", "images/right-arrow.png");
		Map<ParameterContext, List<ComponentParameter>> params = getMappedParams();
		for (ParameterContext ctx : params.keySet()) {
			List<ComponentParameter> values = params.get(ctx);
			if (values.isEmpty())
				continue;
			BaseTreeItem context = addChild(root, ComponentParameterTableItem.getParamContextName(ctx), null);
			for(ComponentParameter p : values) {
				if (!_context.getParams().contains(p.getContext(), p.getName()))
					continue;
				Object value  = _context.getParams().get(p.getContext(), p.getName());
				if (value == null)
					value = "<null>";
				String txt = p.getName() + "=" + value.toString();
				addChild(context, txt, "images/right-arrow.png");
			}
		}
		refresh(getRoot());
	}
	
	private void printOutputParams() {
		BaseTreeItem root = addChild(getRoot(), "Итоговые параметры", "images/left-arrow.png");
		Map<ParameterContext, List<ComponentParameter>> params = getMappedParams();
		for (ParameterContext ctx : params.keySet()) {
			if (ctx == ParameterContext.Constant)
				continue;
			List<ComponentParameter> values = params.get(ctx);
			if (values.isEmpty())
				continue;
			BaseTreeItem context = addChild(root, ComponentParameterTableItem.getParamContextName(ctx), null);
			for(ComponentParameter p : values) {
				if (!_context.getParams().contains(p.getContext(), p.getName()))
					continue;
				Object value  = _context.getParams().get(p.getContext(), p.getName());
				if (value == null)
					value = "<null>";
				String txt = p.getName() + "=" + value.toString();
				addChild(context, txt, "images/left-arrow.png");
			}
		}
		refresh(getRoot());
	}
	
	private Map<ParameterContext, List<ComponentParameter>> getMappedParams() {
		Map<ParameterContext, List<ComponentParameter>> params = new HashMap<ParameterContext, List<ComponentParameter>>();
		for (ParameterContext ctx : ParameterContext.values()) {
			params.put(ctx, new ArrayList<ComponentParameter>());
		}
		for (ComponentParameter p : _component.getParameters()) {
			params.get(p.getContext()).add(p);
		}
		return params;
	}

	public ScriptExecutionResult getResult() {
		return _result;
	}

}
