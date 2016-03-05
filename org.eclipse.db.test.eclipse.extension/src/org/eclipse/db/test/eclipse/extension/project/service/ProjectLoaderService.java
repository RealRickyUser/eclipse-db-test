package org.eclipse.db.test.eclipse.extension.project.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.domain.Component;
import org.eclipse.db.test.common.domain.ComponentCategory;
import org.eclipse.db.test.common.domain.ComponentItem;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptCategory;
import org.eclipse.db.test.common.domain.ScriptItem;
import org.eclipse.db.test.common.domain.Test;
import org.eclipse.db.test.common.domain.TestCategory;
import org.eclipse.db.test.common.service.ComponentCategoryService;
import org.eclipse.db.test.common.service.ComponentService;
import org.eclipse.db.test.common.service.ScriptCategoryService;
import org.eclipse.db.test.common.service.ScriptService;
import org.eclipse.db.test.common.service.TestCategoryService;
import org.eclipse.db.test.common.service.TestService;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.db.test.eclipse.extension.project.ICustomProjectElement;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentCategory;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptCategory;
import org.eclipse.db.test.eclipse.extension.project.ProjectScriptItem;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestCategory;
import org.eclipse.db.test.eclipse.extension.project.ProjectTestItem;

public class ProjectLoaderService {

	private DbServerInfo _info;
	
	public ProjectLoaderService(DbServerInfo info) {
		_info = info;
	}
	
	public ProjectLoaderService(ICustomProjectElement element) {
		this(ConfigurationManager.getServerInfo(element.getProject()));
	}
	
	public List<ICustomProjectElement> loadScripts(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		items.addAll(loadScriptCategories(parent));
		items.addAll(loadCategoryScripts(parent));
		return items;
	}
	
	private List<ICustomProjectElement> loadScriptCategories(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		List<ScriptCategory> categories = ScriptService.getCategories(_info, parent);
		for (ScriptCategory category : categories) {
			ProjectScriptCategory psc = new ProjectScriptCategory(category);
			items.add(psc);
		}
		return items;
	}
	
	private List<ICustomProjectElement> loadCategoryScripts(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		List<Script> categories = ScriptService.getScripts(_info, parent);
		for (Script category : categories) {
			ProjectScriptItem psc = new ProjectScriptItem(category);
			items.add(psc);
		}
		return items;
	}
	
	public void loadScript(ProjectScriptItem item) {
		loadScript(item.getScript());
	}
	
	public void loadScript(Script item) {
		ScriptService.loadScript(_info, item);
		ScriptService.loadScriptParameters(_info, item);
	}
	
	public void saveScript(ProjectScriptItem item) {
		ScriptService.saveScript(_info, item.getScript());
	}
	
	public void createScriptCategory(ScriptCategory category) {
		ScriptService.addScriptCategory(_info, category);
	}
	
	public void updateScriptCategory(ScriptCategory category) {
		ScriptCategoryService.updateCategory(_info, category);
	}
	
	public List<Integer> getAllCategories(int rootId) {
		return ScriptCategoryService.getAllCategories(_info, rootId);
	}
	
	public void deleteScriptCategory(int categoryId) {
		ScriptCategoryService.deleteCategory(_info, categoryId);
	}
	
	public void createScript(Script item) {
		ScriptService.addScript(_info, item);
	}
	
	public void deleteScript(Script item) {
		ScriptService.deleteScript(_info, item);
	}
	
	public List<ICustomProjectElement> loadComponents(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		items.addAll(loadComponentCategories(parent));
		items.addAll(loadComponentItems(parent));
		return items;
	}
	
	private List<ICustomProjectElement> loadComponentItems(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		List<Component> comps = ComponentService.getComponents(_info, parent);
		for (Component comp : comps) {
			ProjectComponentItem psc = new ProjectComponentItem(comp);
			items.add(psc);
		}
		return items;
	}

	private List<ICustomProjectElement> loadComponentCategories(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		List<ComponentCategory> categories = ComponentService.getCategories(_info, parent);
		for (ComponentCategory category : categories) {
			ProjectComponentCategory psc = new ProjectComponentCategory(category);
			items.add(psc);
		}
		return items;
	}
	
	public void createComponentCategory(ComponentCategory category) {
		ComponentService.addComponentCategory(_info, category);
	}
	
	public void updateComponentCategory(ComponentCategory category) {
		ComponentCategoryService.updateCategory(_info, category);
	}
	
	public List<Integer> getAllComponentCategories(int rootId) {
		return ComponentCategoryService.getAllCategories(_info, rootId);
	}
	
	public void deleteComponentCategory(int categoryId) {
		ComponentCategoryService.deleteCategory(_info, categoryId);
	}
	
	public void updateComponent(Component item) {
		ComponentService.updateComponent(_info, item);
	}
	
	public void loadComponent(ProjectComponentItem item) {
		loadComponent(item.getComponent());
	}
	
	public void loadComponent(Component item) {
		ComponentService.loadComponent(_info, item);
		for(ScriptItem i : item.getScripts()) {
			loadScriptParameters(i, item.getId());
		}
	}
	
	public void loadScriptParameters(ScriptItem item, int componentId) {
		ComponentService.loadScriptMappedParams(_info, item, componentId);
		ComponentService.loadScriptItemParameters(_info, item);
		item.loadParamsFromMapping();
	}

	public void createComponent(Component comp) {
		ComponentService.createComponent(_info, comp);
	}
	
	public void deleteComponent(Component comp) {
		ComponentService.deleteComponent(_info, comp);
	}
	
	public void createTestCategory(TestCategory category) {
		TestCategoryService.addTestCategory(_info, category);
	}
	
	public void updateTestCategory(TestCategory category) {
		TestCategoryService.updateCategory(_info, category);
	}
	
	public List<Integer> getAllTestCategories(int rootId) {
		return TestCategoryService.getAllCategories(_info, rootId);
	}
	
	public void deleteTestCategory(int categoryId) {
		TestCategoryService.deleteCategory(_info, categoryId);
	}
	
	public List<ICustomProjectElement> loadTests(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		items.addAll(loadTestCategories(parent));
		items.addAll(loadTestsInternal(parent));
		return items;
	}
	
	private List<ICustomProjectElement> loadTestsInternal(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		List<Test> tests = TestService.getTests(_info, parent);
		for (Test category : tests) {
			ProjectTestItem psc = new ProjectTestItem(category);
			items.add(psc);
		}
		return items;
	}

	private List<ICustomProjectElement> loadTestCategories(int parent) {
		List<ICustomProjectElement> items = new ArrayList<ICustomProjectElement>();
		List<TestCategory> categories = TestCategoryService.getCategories(_info, parent);
		for (TestCategory category : categories) {
			ProjectTestCategory psc = new ProjectTestCategory(category);
			items.add(psc);
		}
		return items;
	}

	public void updateTest(Test test) {
		TestService.updateTest(_info, test);
	}

	public void loadComponentParameters(ComponentItem item, int id) {
		TestService.loadComponentMappedParams(_info, item, id);
		TestService.loadTestComponentParameters(_info, item);
		item.loadParamsFromMapping();
	}
	
	public void loadTest(ProjectTestItem item) {
		loadTest(item.getTest());
	}
	
	public void loadTest(Test item) {
		TestService.loadTest(_info, item);
		for(ComponentItem i : item.getComponents()) {
			loadComponentParameters(i, item.getId());
		}
	}
	
	public void createTest(Test test) {
		TestService.createTest(_info, test);
	}

	public void deleteTest(Test test) {
		TestService.deleteTest(_info, test);
	}
}
