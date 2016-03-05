package org.eclipse.db.test.eclipse.extension.editors;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.db.test.common.domain.ParameterDirection;
import org.eclipse.db.test.common.domain.Script;
import org.eclipse.db.test.common.domain.ScriptParameter;
import org.eclipse.db.test.common.domain.ScriptType;
import org.eclipse.db.test.eclipse.extension.cnf.misc.ViewHelper;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.db.test.eclipse.extension.preferences.NamedDbServerInfo;
import org.eclipse.db.test.eclipse.extension.project.editor.ScriptParameterTableItem;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectExecutionService;
import org.eclipse.db.test.eclipse.extension.project.service.ProjectLoaderService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class SqlScriptEditor extends EditorPart {
	public static final String ID = "org.eclipse.db.test.eclipse.extension.editors.sqlscripteditor";
	
	private Text _content;
	private Table _propsTable;
	private Text _scriptName;
	private Text _stateParamValue;
	private Combo _type;
	private Combo _stateParam;
	private boolean _dirty;

	private SqlScriptEditorInput _input;
	private IPartListener _partListener;
	
	private static List<String> _types;
	
	static {
		_types = new ArrayList<String>();
		Field[] fields = Types.class.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			_types.add(name);
		}
		Collections.sort(_types);
	}
	
	public SqlScriptEditor() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof SqlScriptEditorInput))
			return;
		_input = (SqlScriptEditorInput) input;
		
        setSite(site);
        setInput(input);
        setPartName(_input.getName());
        
        _input.getItem().addListener(new PropertyChangedListener() {
			@Override
			public void raise() {
				setDirty(true);
			}
		});
        
        final SqlScriptEditor _this = this;
        
        _partListener = new IPartListener() {
			
			@Override
			public void partOpened(IWorkbenchPart part) { }
			
			@Override
			public void partDeactivated(IWorkbenchPart part) { }
			
			@Override
			public void partClosed(IWorkbenchPart part) {
				if (_input != null)
					_input.getItem().setLoaded(false);
				getSite().getPage().removePartListener(_partListener);
				ViewHelper.refreshProperties();
			}
			
			@Override
			public void partBroughtToTop(IWorkbenchPart part) {}

			@Override
			public void partActivated(IWorkbenchPart part) {
				if (part != _this)
					return;
				ViewHelper.selectExplorer(_input.getItem());
				ViewHelper.refreshProperties();
			}
		};
		site.getPage().addPartListener(_partListener);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout root = new GridLayout(1, false);
		root.marginWidth = 5;
		root.marginHeight = 5;
		root.verticalSpacing = 3;
		root.horizontalSpacing = 0;
		parent.setLayout(root);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		
		GridLayout commonLayout = new GridLayout(6, true);
		commonLayout.marginWidth = 5;
		commonLayout.marginHeight = 5;
		commonLayout.verticalSpacing = 3;
		commonLayout.horizontalSpacing = 5;
		Composite common = new Composite(parent, SWT.FILL);
		common.setLayout(commonLayout);
		Label txtName = new Label(common, SWT.NONE);
		txtName.setText("Название скрипта");
		_scriptName = new Text(common, SWT.FILL);
		_scriptName.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 5, 1));
		_scriptName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				_input.getItem().getScript().setName(_scriptName.getText());
				setDirty(true);
			}
		});
		
		
		Label txtType = new Label(common, SWT.NONE);
		txtType.setText("Тип");
		_type = new Combo(common, SWT.DROP_DOWN | SWT.READ_ONLY);
		_type.setItems(new String[] {"Действие", "Событие"});
		_type.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
					_input.getItem().getScript().setType(ScriptType.values()[_type.getSelectionIndex()]);
					_stateParam.setEnabled(_type.getSelectionIndex() != 0);
					_stateParamValue.setEnabled(_type.getSelectionIndex() != 0);
					if (_type.getSelectionIndex() == 0) {
						_stateParam.deselectAll();
						_stateParamValue.setText("");
					}
					setDirty(true);
		      }
		    });
		
		Label txtParam = new Label(common, SWT.NONE);
		txtParam.setText("Параметр");
		_stateParam = new Combo(common, SWT.DROP_DOWN | SWT.READ_ONLY);
		_stateParam.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
					_input.getItem().getScript().setStateParam(_stateParam.getItem(_stateParam.getSelectionIndex()));
					setDirty(true);
		      }
		    });
		
		Label txtNParam = new Label(common, SWT.NONE);
		txtNParam.setText("Значение параметра");
		_stateParamValue = new Text(common, SWT.FILL);
		_stateParamValue.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1));
		_stateParamValue.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				_input.getItem().getScript().setStateValue(_stateParamValue.getText());
				setDirty(true);
			}
		});
		
		//_stateParam.setItems(new String[] {"Действие", "Событие"});
		//parent.setLayout(commonLayout);
		
		
        _content = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        _content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        Composite buttons = new Composite(parent, SWT.NONE);
        GridLayout chi = new GridLayout(2, true);
        buttons.setLayout(chi);
        buttons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        Button btn = new Button(buttons, SWT.PUSH);
        btn.setText("Найти параметры");
        btn.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
        btn.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
        		parseParams();
    		}
		});
        
        Button btnExec = new Button(buttons, SWT.PUSH);
        btnExec.setText("Выполнить");
        btnExec.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 1, 1));
        btnExec.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
        		execute();
    		}
		});
        
        _content.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				_input.getItem().getScript().setText(_content.getText());
				setDirty(true);
			}
		});
        createPropertiesTable(parent, root);
        load();
        setDirty(false);
        ViewHelper.refreshProperties();
	}
	
	private void createPropertiesTable(Composite parent, GridLayout root) {
		_propsTable = new Table(parent, SWT.VIRTUAL | SWT.BORDER);
		_propsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_propsTable.setHeaderVisible(true);
		_propsTable.setLinesVisible (true);
		TableColumn col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Параметр");
		col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Тип");
		col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Размерность");
		col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Направление");
		col = new TableColumn(_propsTable, SWT.NONE);
		col.setText("Тестовое значение");
		for (TableColumn column : _propsTable.getColumns()) {
			column.setWidth(100);
		}
		updateProperties();
	}
	
	private void execute() {
		ProjectExecutionService servive = getExecService();
		servive.execute(_input.getItem());
		updatePropertyValues();
	}
	
	private void updatePropertyValues() {
		for (int x = 0; x < _propsTable.getItemCount(); x++) {
			TableItem ti = _propsTable.getItem(x);
			ScriptParameterTableItem i = (ScriptParameterTableItem) ti.getData("item");
			i.updateValue();
		}
	}
	
	private void updateProperties() {
		//_propsTable.clearAll();
		while (_propsTable.getItemCount() > 0) {
			TableItem ti = _propsTable.getItem(0);
			ScriptParameterTableItem i = (ScriptParameterTableItem) ti.getData("item");
			i.dispose();
			ti.dispose();
		}
		for (ScriptParameter param : _input.getItem().getScript().getParameters()) {
			ScriptParameterTableItem item = new ScriptParameterTableItem(_propsTable, param);
			item.addListener(new PropertyChangedListener() {
				@Override
				public void raise() {
					setDirty(true);
				}
			});
		   }
		_propsTable.setRedraw(true);
		updateStatePrams();
	}

	private void load() {
		String content = _input.getContent();
		if (content != null)
			_content.setText(content);
		_scriptName.setText(_input.getName());
		_type.select(_input.getItem().getScript().getType().ordinal());
		String stateParam = _input.getItem().getScript().getStateParam();
		if (stateParam != null) {
			for (ScriptParameter p : _input.getItem().getScript().getParameters()) {
				if (!p.getName().equals(stateParam))
					continue;
				_stateParam.select(_input.getItem().getScript().getParameters().indexOf(p));
				break;
			}
		}
		String paramValue = _input.getItem().getScript().getStateValue();
		if (paramValue != null)
			_stateParamValue.setText(paramValue);
		updateStatePrams();
	}
	
	private void updateStatePrams() {
		_stateParam.removeAll();
		_stateParam.setEnabled(_type.getSelectionIndex() != 0);
		_stateParamValue.setEnabled(_type.getSelectionIndex() != 0);
		List<String> pars = new ArrayList<String>();
		for (ScriptParameter sp : _input.getItem().getScript().getParameters()) {
			pars.add(sp.getName());
		}
		_stateParam.setItems(pars.toArray(new String[] {}));
		String param = _input.getItem().getScript().getStateParam();
		if (param == null)
			return;
		_stateParam.select(pars.indexOf(param));
	}
	
	@Override
	public void setFocus() {
		_content.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		ProjectLoaderService service = getService();
		service.saveScript(_input.getItem());
		setDirty(false);
		ViewHelper.refreshExplorer(_input.getItem());
		setPartName(_input.getName());
	}

	@Override
	public void doSaveAs() {
		doSave(null);
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}
	
	private void parseParams() {
		List<String> params = getParameters();
		Script sc = _input.getItem().getScript();
		sc.getParameters().clear();
		for (String p : params) {
			ScriptParameter sp = new ScriptParameter(p, ParameterDirection.In);
			sc.addParameter(sp);
		}
		updateProperties();
		setDirty(true);
	}
	
	private List<String> getParameters() {
		List<String> items = new ArrayList<String>();
		String txt = _content.getText();
		Matcher matcher = Pattern.compile(":(\\w+)").matcher(txt);
		while (matcher.find()) {
			  items.add(matcher.group(1));
			}
		return items;
	}
	
    protected void setDirty(boolean value) {
    	if (value == _dirty)
    		return;
        _dirty = value;
        firePropertyChange(PROP_DIRTY);
     }
    
	private ProjectLoaderService getService() {
		NamedDbServerInfo info = ConfigurationManager.getServerInfo(_input.getItem().getProject());
		return new ProjectLoaderService(info); 
	}
	
	private ProjectExecutionService getExecService() {
		NamedDbServerInfo info = ConfigurationManager.getExecServerInfo(_input.getItem().getProject());
		return new ProjectExecutionService(info); 
	}
	

}
