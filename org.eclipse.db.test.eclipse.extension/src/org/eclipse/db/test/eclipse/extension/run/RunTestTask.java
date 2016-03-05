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
import org.eclipse.db.test.common.domain.ComponentItem;
import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ComponentParameterItem;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.common.domain.ScriptExecutionResult;
import org.eclipse.db.test.common.domain.Test;
import org.eclipse.db.test.eclipse.extension.project.editor.ComponentParameterTableItem;

public class RunTestTask extends BaseRunTask {
	private ComponentExecutionContext _context;
	private Test _test;
	private ScriptExecutionResult _result;
	
	private BaseTreeItem _scriptsRoot;
	private List<OutputParameterInfo> _outParams;

	public RunTestTask(ComponentExecutionContext context, Test test) {
		_context = context;
		_test = test;
		_outParams = new ArrayList<OutputParameterInfo>();
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		int steps = _test.getComponents().size();
		monitor.beginTask("Выполнение теста", steps * 100);
		if (!validate()) {
			monitor.done();
			return;
		}
		createLocalParams();
		printInputParams();
		createComponentGroup();
		for(ComponentItem item : _test.getComponents()) {
			if (monitor.isCanceled())
				break;
			ComponentExecutionContext ctx = _context.createChildContext();
			BaseTreeItem si = addComponent(item);
			monitor.subTask("компонент ["+ item.getName() + "]");
			monitor.worked(10);
			Component comp = _context.getComponent(item.getId());
			//fill params before script execution
			preProcessParams(item, ctx);
			monitor.worked(10);
			//create script execution task
			RunComponentTask task = new RunComponentTask(ctx, comp);
			task.setRoot(si);
			//execute script
			task.run(new SubProgressMonitor(monitor, 100));
			monitor.worked(70);
			//get result
			_result = task.getResult();
			if (!_result.isSuccess())
				break;
			//save params after script excuted
			postProcessParams(item, ctx);
			monitor.worked(10);
		}
		monitor.done();
		printOutputParams();
		processOutParams();
		setCompleted();
	}

	private void createLocalParams() {
		for (ComponentParameter param : _test.getParameters()) {
			if (param.getContext() != ParameterContext.Local)
				continue;
			if (_context.getParams().contains(param.getContext(), param.getName()))
				continue;
			_context.getParams().add(param.getContext(), param.getName(), null);
		}
	}
	
	private boolean validate() {
		//check all scripts
		for(ComponentItem item : _test.getComponents()) {
			if (!_context.containsComponent(item.getId()))
				return false;
		}
		return true;
	}
	
	private void preProcessParams(ComponentItem item, ComponentExecutionContext ctx) {
		Component comp = _context.getComponent(item.getId());
		comp.clearParamValues();
		for (ComponentParameterItem pitem : item.getParameters()) {
			//ComponentParameterItem pitem = item.findParameter(pi.getName(), pi.getContext());
			if (pitem.getContext() == null) {
				ctx.getParams().add(pitem.getMappedContext(), pitem.getMapped(), null);
				continue;
			}
			if (!_context.getParams().contains(pitem.getContext(), pitem.getName()))
				continue;
			Object value = _context.getParams().get(pitem.getContext(), pitem.getName());
			if (pitem.getMappedContext() == ParameterContext.Local)
				value = null;
			ctx.getParams().add(pitem.getMappedContext(), pitem.getMapped(), value);
		}
	}
	
	private void postProcessParams(ComponentItem item, ComponentExecutionContext ctx) {
		//Component script = _context.getComponent(item.getId());
		for (ComponentParameterItem pi : item.getParameters()) {
			if (pi.getMappedContext() == ParameterContext.Local)
				continue;
			//parameter not mapped? skipping.
			if (pi.getContext() == null || !_context.getParams().contains(pi.getContext(), pi.getName()))
				continue;
			if (!ctx.getParams().contains(pi.getMappedContext(), pi.getMapped()))
				continue;
			//save output parameters
			Object value = ctx.getParams().get(pi.getMappedContext(), pi.getMapped());
			saveOutParams(pi.getName(), pi.getContext(), value);
		}
	}
	
	private void saveOutParams(String name, ParameterContext ctx, Object value) {
		//Object orig = _context.getParams().get(ctx, name);
		if (ctx == ParameterContext.Etalon || ctx == ParameterContext.Runtime) {
			_outParams.add(new OutputParameterInfo(name, ctx, _context.getParams().get(ctx, name), value));
		}
		_context.getParams().set(ctx, name, value);
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
			processedParams.add(info.getName());
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
	
	private void createComponentGroup() {
		_scriptsRoot = new BaseTreeItem(getRoot());
		_scriptsRoot.setText("Компоненты");
		getRoot().addChild(_scriptsRoot);
		_scriptsRoot.setImagePath("images/component.png");
		refresh(getRoot());
	}
	
	private BaseTreeItem addComponent(ComponentItem item) {
		BaseTreeItem script = new BaseTreeItem(_scriptsRoot);
		script.setText("Компонент [" + item.getName() + "]");
		script.setImagePath("images/component.png");
		_scriptsRoot.addChild(script);
		refresh(_scriptsRoot);
		return script;
		
	}
	
	private void setCompleted() {
		BaseTreeItem item = addChild(getRoot(), "Тест завершен", null);
		String status = "Результат: " + (_result.isSuccess() ? "успех" : "ошибка");
		String image = "images/" + (_result.isSuccess() ? "success" : "fail") + ".png";
		BaseTreeItem result = addChild(item, status, image);
		item.setImagePath(image);
		if (!_result.isSuccess()) {
			addChild(result, _result.getMessage(), "images/cancel.png");
		}
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
		for (ComponentParameter p : _test.getParameters()) {
			params.get(p.getContext()).add(p);
		}
		return params;
	}
}
