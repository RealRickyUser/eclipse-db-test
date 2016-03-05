package org.eclipse.db.test.eclipse.extension.project.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.db.test.common.domain.ComponentParameter;
import org.eclipse.db.test.common.domain.ParameterContext;
import org.eclipse.db.test.eclipse.extension.editors.PropertyChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class ComponentParameterTableItem {
	
	private Table _parent;
	private ComponentParameter _param;
	private TableItem ti;
	private Text _paramName;
	private Text _value;
	private CCombo combo;
	
	private List<Widget> _childs;
	private List<ControlEditor> _childEditors;
	private List<PropertyChangedListener> _listeners;
	private List<String> _types;
	private FocusListener _listener;

	public ComponentParameterTableItem(Table parent, ComponentParameter param) {
		_parent = parent;
		_param = param;
		_types = new ArrayList<String>();
		_childs = new ArrayList<Widget>();
		_childEditors = new ArrayList<ControlEditor>();
		_listeners = new ArrayList<PropertyChangedListener>();
		loadTypes();
		createControls();
		loadValues();
	}
	
	private void loadValues() {
		if (_param.getName() != null)
			_paramName.setText(_param.getName());
		if (_param.getValue() != null)
			_value.setText(_param.getValue().toString());
	}

	private void loadTypes() {
		for (ParameterContext param : ParameterContext.values()) {
			_types.add(getParamContextName(param));
		}
	}
	
	public static String getParamContextName(ParameterContext context) {
		switch (context) {
		case Etalon:
			return "Эталон";
		case Global:
			return "Глобальный";
		case Local:
			return "Локальный";
		case Runtime:
			return "Тест";
		case Constant:
			return "Константа";
		}
		return null;
	}

	private void createControls() {
		ti = new TableItem(_parent, SWT.NONE);
		ti.setData("item", this);
		//ti.setText("item");
		
		_listener = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				_parent.setSelection(ti);
			}
		};
		
		
		TableEditor editor = new TableEditor(_parent);
	    _paramName = new Text(_parent, SWT.NONE);

	    editor.grabHorizontal = true;
	    ti.setData("txtItem", _paramName);
	    editor.setEditor(_paramName, ti, 0);
	    _paramName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateParamName();
			}
		});
	    _paramName.addFocusListener(_listener);
	    _childs.add(_paramName);
	    _childEditors.add(editor);
	    
	    editor = new TableEditor(_parent);
		combo = new CCombo(_parent, SWT.READ_ONLY);
		combo.setItems(_types.toArray(new String[0]));
		combo.clearSelection();
		if (_param.getContext() != null) {
			int pos = _param.getContext().ordinal();
			combo.select(pos);
		}
		ti.setData("context", combo);
		editor.grabHorizontal = true;
	    editor.setEditor(combo, ti, 1);
	    combo.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
    			updateContext();
    		}
		});
	    combo.addFocusListener(_listener);
	    _childs.add(combo);
	    _childEditors.add(editor);
	    
		editor = new TableEditor(_parent);
	    _value = new Text(_parent, SWT.NONE);

	    editor.grabHorizontal = true;
	    ti.setData("value", _value);
	    editor.setEditor(_value, ti, 2);
	    _value.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateValue();
			}
		});
	    _value.addFocusListener(_listener);
	    
	    _childs.add(_value);
	    _childEditors.add(editor);
	}
	
	protected void updateValue() {
		_param.setValue(_value.getText());
	}

	private void updateContext() {
		_param.setContext(ParameterContext.values()[combo.getSelectionIndex()]);
		raise();
	}

	private void updateParamName() {
		_param.setName(_paramName.getText());
		raise();
	}

	public void dispose() {
		for (Widget item : _childs) {
				item.dispose();
			}
		for (ControlEditor controlEditor : _childEditors) {
			controlEditor.dispose();
		}
		_listeners.clear();
		_listener = null;
		ti = null;
	}
	
	private void raise() {
		for (PropertyChangedListener i : _listeners) {
			i.raise();
		}
	}
	
	public void addListener(PropertyChangedListener listener) {
		_listeners.add(listener);
	}

}
