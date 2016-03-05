package org.eclipse.db.test.eclipse.extension.run;

import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.common.domain.ComponentExecutionContext;
import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptItem;
import org.eclipse.db.test.common.domain.Test;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;

public class LoaderService {
	
	public static void load(Component component, ComponentExecutionContext context) {
		ProjectLoaderService service = new ProjectLoaderService(context.getServerInfo());
		if (context.containsComponent(component.getId()))
			return;
		context.addComponent(component);
		service.loadComponent(component);
		for (ScriptItem si : component.getScripts()) {
			if (context.containsScript(si.getId()))
				continue;
			Script script = new Script();
			script.setId(si.getId());
			service.loadScript(script);
			context.addScript(script);
		}
		
		for (ComponentParameter param : component.getParameters()) {
			if (param.getContext() == ParameterContext.Constant) {
				param.setValue(param.getName());
				context.getParams().add(param.getContext(), param.getName(), param.getValue());
			} else {
				context.getParams().add(param.getContext(), param.getName(), null);
			}
		}
		
	}

	public static void load(Test comp, ComponentExecutionContext context) {
		ProjectLoaderService service = new ProjectLoaderService(context.getServerInfo());
		service.loadTest(comp);
		for (ComponentParameter param : comp.getParameters()) {
			context.getParams().add(param.getContext(), param.getName(), null);
		}
	}

}
