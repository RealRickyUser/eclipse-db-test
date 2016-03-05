package org.eclipse.db.test.eclipse.extension.editors;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.common.domain.ComponentItem;
import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ComponentParameterItem;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.common.domain.Test;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.db.test.eclipse.extension.preferences.NamedDbServerInfo;
import org.eclipse.db.test.eclipse.extension.project.ProjectComponentItem;
import org.eclipse.db.test.eclipse.extension.project.editor.ComponentParameterTableItem;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.db.test.eclipse.extension.run.RunService;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class TestEditor extends EditorPart {
	public static final String ID = "org.eclipse.db.test.eclipse.extension.editors.testeditor";
	
	private IPartListener _partListener;
	private TestEditorInput _input;
	private boolean _dirty;
	
	private Map<Integer, ComponentItem> _componentItems;
	private java.util.List<ComponentItem> _displayItems;
	
	private List _components;
	private Table _propsTable;
	private Table _componentPropsTable;
	private Text _scriptName;
	
	public TestEditor() {
		_componentItems = new HashMap<Integer, ComponentItem>();
		_displayItems = new ArrayList<ComponentItem>();
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		ProjectLoaderService service = getService();
		service.updateTest(_input.getItem().getTest());
		setDirty(false);
		ViewHelper.refreshExplorer(_input.getItem());
		setPartName(_input.getName());
	}

	@Override
	public void doSaveAs() {
		doSave(null);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof TestEditorInput))
			return;
		_input = (TestEditorInput) input;
		
        setSite(site);
        setInput(input);
        setPartName(_input.getName());
        
        _input.getItem().addListener(new PropertyChangedListener() {
			@Override
			public void raise() {
				setDirty(true);
			}
		});
        
        final TestEditor _this = this;
        
        _partListener = new IPartListener() {
			
			@Override
			public void partOpened(IWorkbenchPart part) { }
			
			@Override
			public void partDeactivated(IWorkbenchPart part) { }
			
			@Override
			public void partClosed(IWorkbenchPart part) {
				/*if (_input != null)
					_input.getItem().setLoaded(false);*/
				getSite().getPage().removePartListener(_partListener);
			}
			
			@Override
			public void partBroughtToTop(IWorkbenchPart part) {}

			@Override
			public void partActivated(IWorkbenchPart part) {
				if (part != _this)
					return;
				ViewHelper.selectExplorer(_input.getItem());
			}
		};
		site.getPage().addPartListener(_partListener);
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}
	
    protected void setDirty(boolean value) {
    	if (value == _dirty)
    		return;
        _dirty = value;
        firePropertyChange(PROP_DIRTY);
     }

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout root  = new GridLayout(1, false);
		root.marginWidth = 5;
		root.marginHeight = 5;
		root.verticalSpacing = 3;
		root.horizontalSpacing = 0;
		parent.setLayout(root);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		
		GridLayout textLayout = new GridLayout(6, true);
		textLayout.marginWidth = 5;
		textLayout.marginHeight = 5;
		textLayout.verticalSpacing = 3;
		textLayout.horizontalSpacing = 5;
		Composite txtCommon = new Composite(parent, SWT.FILL);
		txtCommon.setLayout(textLayout);
		Label txtName = new Label(txtCommon, SWT.NONE);
		txtName.setText("Название теста");
		_scriptName = new Text(txtCommon, SWT.FILL);
		_scriptName.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1));
		_scriptName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				_input.getItem().getTest().setName(_scriptName.getText());
				setDirty(true);
			}
		});
		
		Button btnRun = new Button(txtCommon, SWT.PUSH);
		btnRun.setText("Запустить");
		btnRun.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		btnRun.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
        		runComponent();
    		}
		});
		
		/*Блок со скриптами и их параметрами*/
		GridLayout scriptsBlockLayout = new GridLayout(2, true);
		scriptsBlockLayout.marginWidth = 5;
		scriptsBlockLayout.marginHeight = 5;
		scriptsBlockLayout.verticalSpacing = 3;
		scriptsBlockLayout.horizontalSpacing = 5;
		Composite scriptsBlock = new Composite(parent, SWT.FILL);
		scriptsBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		scriptsBlock.setLayout(scriptsBlockLayout);
		
		//Device device = Display.getCurrent();
		//org.eclipse.swt.graphics.Color col = new Color(device, 0, 255, 255);
		//scriptsBlock.setBackground(col);
		
		GridLayout scriptItemsLayout = new GridLayout(2, false);
		scriptItemsLayout.marginWidth = 5;
		scriptItemsLayout.marginHeight = 5;
		scriptItemsLayout.verticalSpacing = 3;
		scriptItemsLayout.horizontalSpacing = 5;
		Composite scriptItemsBlock = new Composite(scriptsBlock, SWT.FILL);
		scriptItemsBlock.setLayout(scriptItemsLayout);
		//col = new Color(device, 0, 0, 255);
		//scriptItemsBlock.setBackground(col);
		scriptItemsBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		/*Список скриптов*/
		Label label = new Label(scriptItemsBlock, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		label.setText("Компоненты");
		
		Button btnRemoveScript = new Button(scriptItemsBlock, SWT.PUSH);
		btnRemoveScript.setText("Удалить");
		btnRemoveScript.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
        		removeSelectedScript();
    		}
		});
		btnRemoveScript.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false));

		
		_components=new List(scriptItemsBlock, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		_components.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedScriptChnged();
			}
		});
		_components.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		/*Параметры скрипта*/
		GridLayout scriptParamsLayout = new GridLayout(1, false);
		scriptParamsLayout.marginWidth = 5;
		scriptParamsLayout.marginHeight = 5;
		scriptParamsLayout.verticalSpacing = 3;
		scriptParamsLayout.horizontalSpacing = 5;
		Composite scriptParamsBlock = new Composite(scriptsBlock, SWT.FILL);
		scriptParamsBlock.setLayout(scriptParamsLayout);
		scriptParamsBlock.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		//col = new Color(device, 255, 0, 255);
		//scriptParamsBlock.setBackground(col);
		
		label = new Label(scriptParamsBlock, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		label.setText("Параметры компонента");
		createScriptParamsTable(scriptParamsBlock, scriptParamsLayout);
		
		addDND();
		
		/*Блок с параметрами компонента*/
		GridLayout commonLayout = new GridLayout(3, false);
		commonLayout.marginWidth = 5;
		commonLayout.marginHeight = 5;
		commonLayout.verticalSpacing = 3;
		commonLayout.horizontalSpacing = 5;
		Composite common = new Composite(parent, SWT.FILL);
		common.setLayout(commonLayout);
		
		Label propsLabel = new Label(common, SWT.NONE);
		propsLabel.setText("Параметры");
		
        Button btn = new Button(common, SWT.PUSH);
        btn.setText("Добавить");
        btn.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
        btn.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
        		addComponentParam();
    		}
		});
        
        Button delBtn = new Button(common, SWT.PUSH);
        delBtn.setText("Удалить");
        delBtn.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
        delBtn.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
        		delComponentParam();
    		}
		});
		
		createPropertiesTable(parent, root);
		load();
		setDirty(false);
	}
	
	private void removeSelectedScript() {
		int pos = _components.getSelectionIndex();
		if (pos == -1)
			return;
		_input.getItem().getTest().getComponents().remove(pos);
		_components.remove(pos);
		clearScriptPropertiesTable();
		setDirty(true);
	}
	
	protected void delComponentParam() {
		int pos = _propsTable.getSelectionIndex();
		if (_input.getItem().getTest().getParameters().size() <= pos)
			return;
		_input.getItem().getTest().getParameters().remove(pos);
		updateProperties();
	}

	private void addComponentParam() {
		ComponentParameter item = new ComponentParameter();
		item.setContext(ParameterContext.Local);
		_input.getItem().getTest().addParameter(item);
		updateProperties();
		setDirty(true);
	}

	private void addDND() {
		DropTarget target = new DropTarget(_components, DND.DROP_COPY | DND.DROP_DEFAULT);
		target.setTransfer(new Transfer[]{ org.eclipse.jface.util.LocalSelectionTransfer.getTransfer() });
		target.addDropListener(new DropTargetListener() {
			@Override
			public void dropAccept(DropTargetEvent event) {
			}
			@Override
			public void drop(DropTargetEvent event) {
				if (event.data == null)
					return;
				TreeSelection selection = (TreeSelection) event.data;
				if (selection == null)
					return;
				ProjectComponentItem item = (ProjectComponentItem) selection.getFirstElement();
				if (item == null)
					return;
				addScriptToComponent(item);
			}
			
			@Override
			public void dragOver(DropTargetEvent event) {

			}
			
			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT)
					event.detail = DND.DROP_COPY;
			}
			
			@Override
			public void dragLeave(DropTargetEvent event) {

			}
			
			@Override
			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT)
					event.detail = DND.DROP_COPY;

			}
		});
	}
	
	private void createScriptParamsTable(Composite parent, GridLayout root) {
		_componentPropsTable = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		_componentPropsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_componentPropsTable.setHeaderVisible(true);
		_componentPropsTable.setLinesVisible (true);
		TableColumn col = new TableColumn(_componentPropsTable, SWT.NONE);
		col.setText("Параметр");
		col = new TableColumn(_componentPropsTable, SWT.NONE);
		col.setText("Связан с");
		for (TableColumn column : _componentPropsTable.getColumns()) {
			column.setWidth(150);
		}
		_componentPropsTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button != 3 || _components.getSelectionIndex() <  0)
					return;
				createParamterBindingMenu();
			}
			
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {}
		});
		
		//updateProperties();
	}
	
	private void createParamterBindingMenu() {
		Menu menu = new Menu(_propsTable);
		Listener listener = new Listener() {
            public void handleEvent(Event e) {
                MenuItem item = (MenuItem) e.widget;
                ComponentParameter param = (ComponentParameter) item.getData("param");
                if (param != null)
              	  processParameterBinding(param);
              }
            };
        for(ParameterContext param : ParameterContext.values()) {
        	int menuCnt = 0;
    		MenuItem mi = new MenuItem(menu, SWT.CASCADE);
    		mi.setText(ComponentParameterTableItem.getParamContextName(param));
    		Menu subMenu = new Menu(menu);
         	for(ComponentParameter cp : _input.getItem().getTest().getParameters()) {
        		if (cp.getContext() != param)
        			continue;
        		menuCnt++;
        		MenuItem ch = new MenuItem(subMenu, SWT.PUSH);
        		ch.setText(cp.getName());
        		ch.setData("param", cp);
        		ch.addListener(SWT.Selection, listener);
        	}
        	if (menuCnt > 0)
           		mi.setMenu(subMenu);
        }
        Point cursorLocation = Display.getCurrent().getCursorLocation();
        menu.setLocation(cursorLocation.x, cursorLocation.y);
        menu.setVisible(true);
	}
	
	private void processParameterBinding(ComponentParameter param) {
		int pos = _componentPropsTable.getSelectionIndex();//script parameter pos
		int selectedScriptPos = _components.getSelectionIndex(); //script pos
		ComponentItem si = _input.getItem().getTest().getComponents().get(selectedScriptPos);
		if (pos == -1 || pos > si.getParameters().size())
			return;
		ComponentParameterItem pi = si.getParameters().get(pos);
		pi.setName(param.getName());
		pi.setContext(param.getContext());
		TableItem paramItem = _componentPropsTable.getItem(pos);
		paramItem.setText(1, getParamName(param));
		setDirty(true);
	}
	
	private static String getParamName(ComponentParameter param) {
		return getParamName(param.getName(), param.getContext());
	}
	
	private static String getParamName(String name, ParameterContext ctx) {
		return String.format("[%s] %s", ComponentParameterTableItem.getParamContextName(ctx), name);
	}
	
	private void createPropertiesTable(Composite parent, GridLayout root) {
		_propsTable = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		_propsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_propsTable.setHeaderVisible(true);
		_propsTable.setLinesVisible (true);
		TableColumn col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Параметр");
		col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Контекст");
		col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Тестовое значение");
		for (TableColumn column : _propsTable.getColumns()) {
			column.setWidth(150);
		}
		updateProperties();
	}
	
	private void updateProperties() {
		while (_propsTable.getItemCount() > 0) {
			TableItem ti = _propsTable.getItem(0);
			ComponentParameterTableItem i = (ComponentParameterTableItem) ti.getData("item");
			i.dispose();
			ti.dispose();
		}
		for (ComponentParameter param : _input.getItem().getTest().getParameters()) {
			ComponentParameterTableItem item = new ComponentParameterTableItem(_propsTable, param);
			item.addListener(new PropertyChangedListener() {
				@Override
				public void raise() {
					setDirty(true);
				}
			});
		   }
		_propsTable.setRedraw(true);
	}

	private void addScriptToComponent(ProjectComponentItem item) {
		if (item == null)
			return;
		ComponentItem si = new ComponentItem(item.getId(), item.getText());
		if (!_componentItems.containsKey(si.getId()))
			loadScriptItem(si);
		else {
			copyParameters(si);
			addScriptItem(si);
		}
	}
	
	private void copyParameters(ComponentItem item) {
		ComponentItem orig = _componentItems.get(item.getId());
		for (ComponentParameterItem pi : orig.getParameters()) {
			 item.addParameter(pi.clone());
		}
	}

	private void addScriptItem(ComponentItem si) {
		addScriptItemInternal(si);
		_input.getItem().getTest().getComponents().add(si);
		setDirty(true);
	}

	private void addScriptItemInternal(ComponentItem si) {
		if (!_componentItems.containsKey(si.getId()))
			_componentItems.put(si.getId(), si);
		_displayItems.add(si);
		_components.add(si.getName());
	}
	
	private void loadScriptItem(ComponentItem si) {
		final ComponentItem _i = si;
		IRunnableWithProgress op = new IRunnableWithProgress() {
			
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				getService().loadComponentParameters(_i, _input.getItem().getId());
				Display.getDefault().asyncExec(new Runnable() {
				    public void run() {
				    	addScriptItem(_i);
				    }
				});
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, false, op);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void selectedScriptChnged(){
		int pos = _components.getSelectionIndex();
		if (pos == -1)
			return;
		java.util.List<ComponentItem> items = _input.getItem().getTest().getComponents();
		if (items.size() < pos)
			return;
		updateScriptPropertiesTable(items.get(pos));
		
	}

	@Override
	public void setFocus() {
		_components.setFocus();
	}
	
	private ProjectLoaderService getService() {
		NamedDbServerInfo info = ConfigurationManager.getServerInfo(_input.getItem().getProject());
		return new ProjectLoaderService(info); 
	}

	private void load() {
		_scriptName.setText(_input.getName());
		for (ComponentItem item : _input.getItem().getTest().getComponents()) {
			addScriptItemInternal(item);
		}
	}
	
	private void clearScriptPropertiesTable() {
		while (_componentPropsTable.getItemCount() > 0) {
			_componentPropsTable.remove(0);
		}
	}
	
	private void updateScriptPropertiesTable(ComponentItem item) {
		clearScriptPropertiesTable();
		for(ComponentParameterItem pi : item.getParameters()) {
			//skip local context
			if (pi.getMappedContext() == ParameterContext.Local)
				continue;
			TableItem ti = new TableItem(_componentPropsTable, SWT.NONE);
			String paramName = "[" + ComponentParameterTableItem.getParamContextName(pi.getMappedContext()) + "] " + pi.getMapped();
			ti.setText(paramName);
			if (pi.getName() == null || pi.getName().isEmpty())
				continue;
			ti.setText(1, getParamName(pi.getName(), pi.getContext()));
		}
	}
	
	private void runComponent() {
		DbServerInfo loaderInfo = ConfigurationManager.getServerInfo(_input.getItem().getProject());
		DbServerInfo execInfo = ConfigurationManager.getExecServerInfo(_input.getItem().getProject());
		Test comp = new Test(_input.getItem().getId(), _input.getName());
		for(ComponentParameter param : _input.getItem().getTest().getParameters()) {
			comp.addParameter(param.clone());
		}
		RunService.run(loaderInfo, execInfo, comp);
	}
}
