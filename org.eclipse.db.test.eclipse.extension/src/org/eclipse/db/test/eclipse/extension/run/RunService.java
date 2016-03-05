package org.eclipse.db.test.eclipse.extension.run;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.common.domain.ComponentExecutionContext;
import org.eclipse.db.test.common.domain.ComponentItem;
import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.common.domain.Test;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;

public class RunService {

	private static void showView() {
		ViewHelper.showView(RunTestView.ID);
	}
	
	public static RunTestView getView() {
		return (RunTestView) ViewHelper.getView(RunTestView.ID);
	}
	
	public static void run(DbServerInfo loaderInfo, DbServerInfo execInfo, Component component) {
		showView();
		final BaseTreeItem root = getView().createNewItem("Запуск компонента [" + component.getName() + "]");
		root.setImagePath("images/component.png");
		final Component _comp = component;
		final ComponentExecutionContext context = new ComponentExecutionContext(loaderInfo, execInfo);
		Job job = new Job("Выполнение компонента [" + component.getName() + "]") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Загружаем компонент", 100);
				List<ComponentParameter> params = new ArrayList<ComponentParameter>(_comp.getParameters());
				_comp.getParameters().clear();
				LoaderService.load(_comp, context);
				for(ComponentParameter param : params) {
					if (!context.getParams().contains(param.getContext(), param.getName()))
						continue;
					context.getParams().add(param.getContext(), param.getName(), param.getValue());
				}
				RunComponentTask task = new RunComponentTask(context, _comp);
				task.setRoot(root);
				//load complete
				monitor.worked(10);
				try {
					monitor.setTaskName("Выполняем компонент");
					task.run(new SubProgressMonitor(monitor, 900));
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.LONG);
		job.schedule();
	}

	public static void run(DbServerInfo loaderInfo, DbServerInfo execInfo, Test component) {
		showView();
		final BaseTreeItem root = getView().createNewItem("Запуск теста [" + component.getName() + "]");
		root.setImagePath("images/test.png");
		final Test _comp = component;
		final ComponentExecutionContext context = new ComponentExecutionContext(loaderInfo, execInfo);
		Job job = new Job("Выполнение теста [" + component.getName() + "]") {
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Загружаем тест", 100);
				List<ComponentParameter> params = new ArrayList<ComponentParameter>(_comp.getParameters());
				_comp.getParameters().clear();
				LoaderService.load(_comp, context);
				for(ComponentParameter param : params) {
					if (!context.getParams().contains(param.getContext(), param.getName()))
						continue;
					context.getParams().add(param.getContext(), param.getName(), param.getValue());
				}
				for (ComponentItem item : _comp.getComponents()) {
					Component comp = new Component(item.getId(), item.getName());
					LoaderService.load(comp, context);
				}
				RunTestTask task = new RunTestTask(context, _comp);
				task.setRoot(root);
				//load complete
				monitor.worked(10);
				try {
					monitor.setTaskName("Выполняем тест");
					task.run(new SubProgressMonitor(monitor, 900));
				} catch (InvocationTargetException | InterruptedException e) {
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setPriority(Job.LONG);
		job.schedule();
	}
	
}
