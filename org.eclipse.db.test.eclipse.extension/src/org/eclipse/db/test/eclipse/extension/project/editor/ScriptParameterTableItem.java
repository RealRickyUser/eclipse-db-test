package org.eclipse.db.test.eclipse.extension.project.editor;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.db.test.common.domain.ParameterDirection;
import org.eclipse.db.test.common.domain.ScriptParameter;
import org.eclipse.db.test.eclipse.extension.editors.PropertyChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

public class ScriptParameterTableItem {
	private final static String _exclude = "ARRAY,DATALINK,DISTINCT,JAVA_OBJECT,NULL,LONGVARCHAR,OTHER,STRUCT,TIMESTAMP,DOUBLE,LONGVARBINARY,BLOB,CLOB,REF,BOOLEAN,ROWID,LONGNVARCHAR,NCLOB,REF_CURSOR,TIMESTAMP_WITH_TIMEZONE";
	private Table _parent;
	private ScriptParameter _param;
	private TableItem ti;
	private List<Widget> _childs;
	private List<ControlEditor> _childEditors;

	private static List<String> _types;
	private static List<String> _excludeTypes;
	
	private List<PropertyChangedListener> _listeners;
	
	static {
		String[] data = _exclude.split(",");
		_excludeTypes = Arrays.asList(data);
		loadTypes();
		Collections.sort(_types);
	}
	
	private static void loadTypes() {
		_types = new ArrayList<String>();
		Field[] fields = Types.class.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if (_excludeTypes.contains(name))
				continue;
			_types.add(name);
		}
	}
	
	public ScriptParameterTableItem(Table parent, ScriptParameter param) {
		_parent = parent;
		_param = param;
		_childs = new ArrayList<Widget>();
		_childEditors = new ArrayList<ControlEditor>();
		_listeners = new ArrayList<PropertyChangedListener>();
		createControls();
	}
	
	private void createControls() {
		ti = new TableItem(_parent, SWT.NONE);
		ti.setData("item", this);
		ti.setText(0, _param.getName());
		
		TableEditor editor = new TableEditor(_parent);
		CCombo combo = new CCombo(_parent, SWT.READ_ONLY);
		combo.setItems(_types.toArray(new String[0]));
		//combo.select(0);
		combo.clearSelection();
		if (_param.getType() != null) {
			int pos = _types.indexOf(_param.getType().toUpperCase());
			if (pos > 0)
				combo.select(pos);
		}
		ti.setData("sqltype", combo);
		editor.grabHorizontal = true;
	    editor.setEditor(combo, ti, 1);
	    combo.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
    			updateParamType((CCombo) e.getSource());
    		}
		});
	    _childs.add(combo);
	    _childEditors.add(editor);
	    
	    editor = new TableEditor(_parent);
	    Text size = new Text(_parent, SWT.NONE);
	    if (_param.getSize() != null)
	    	size.setText(_param.getSize());
	    editor.grabHorizontal = true;
	    ti.setData("size", size);
	    editor.setEditor(size, ti, 2);
	    size.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateParamSize((Text)e.getSource());
			}
		});
	    _childs.add(size);
	    _childEditors.add(editor);

	    
	    editor = new TableEditor(_parent);
		CCombo comboDir = new CCombo(_parent, SWT.READ_ONLY);
		comboDir.add("Входной");
		comboDir.add("Сквозной");
		comboDir.add("Выходной");
		comboDir.select(_param.getDirection().ordinal());
		ti.setData("direction", comboDir);
		editor.grabHorizontal = true;
	    editor.setEditor(comboDir, ti, 3);
	    comboDir.addSelectionListener(new SelectionAdapter() {
        	@Override
    		public void widgetSelected(SelectionEvent e) {
    			updateParamDirection((CCombo) e.getSource());
    		}
		});
	    _childs.add(comboDir);
	    _childEditors.add(editor);
	    
	    editor = new TableEditor(_parent);
	    Text val = new Text(_parent, SWT.NONE);
	    if (_param.getValue() != null)
	    	val.setText(_param.getValue().toString());
	    editor.grabHorizontal = true;
	    ti.setData("value", val);
	    editor.setEditor(val, ti, 4);
	    val.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateParamValue((Text)e.getSource());
			}
		});
	    _childs.add(val);
	    _childEditors.add(editor);


	}
	
	protected void updateParamSize(Text source) {
		_param.setSize(source.getText());
		raise();
	}
	
	protected void updateParamValue(Text source) {
		_param.setValue(source.getText());
	}

	protected void updateParamType(CCombo source) {
		int pos = source.getSelectionIndex();
		_param.setType(_types.get(pos));
		raise();
	}

	private void updateParamDirection(CCombo source) {
		int pos = source.getSelectionIndex();
		_param.setDirection(ParameterDirection.values()[pos]);
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
		ti = null;
	}
	
	public void addListener(PropertyChangedListener listener) {
		_listeners.add(listener);
	}
	
	private void raise() {
		for (PropertyChangedListener i : _listeners) {
			i.raise();
		}
	}
	
	public void updateValue() {
		Object value = _param.getValue();
		Text val = (Text) ti.getData("value");
		val.setText("");
		if (value == null)
			return;
		val.setText(value.toString());
	}
	
}
