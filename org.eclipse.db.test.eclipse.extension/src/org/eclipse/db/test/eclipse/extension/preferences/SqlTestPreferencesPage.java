package org.eclipse.db.test.eclipse.extension.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.db.test.common.dal.DbServerInfo;
import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SqlTestPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button btnAdd;
	private Button btnEdit;
	private Button btnDel;
	private List list;
	private Map<String, NamedDbServerInfo> servers;
	
	public SqlTestPreferencesPage() {
		servers = new HashMap<String, NamedDbServerInfo>();
	}

	public SqlTestPreferencesPage(String title) {
		super(title);
		servers = new HashMap<String, NamedDbServerInfo>();
	}

	public SqlTestPreferencesPage(String title, ImageDescriptor image) {
		super(title, image);
		servers = new HashMap<String, NamedDbServerInfo>();
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {
		GridLayout root = new GridLayout(1, true);
		root.numColumns = 1;
		parent.setLayout(root);
		Label l = new Label(parent, SWT.CENTER);
		l.setText("Серверы хранения тестов");
		list = new List(parent, NONE);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		Composite buttons = new Composite(parent, SWT.FILL);
		RowLayout rl = new RowLayout(SWT.FILL);
		rl.type = SWT.HORIZONTAL;
		rl.pack = true;
		rl.fill = true;
		rl.center = true;
		rl.justify = true;
		buttons.setLayout(rl);
		btnAdd = new Button(buttons, SWT.PUSH);
		btnAdd.setText("Добавить новый...");
		//btnAdd.setLayoutData(new RowData());
		btnEdit = new Button(buttons, SWT.PUSH);
		btnEdit.setText("Изменить...");
		btnDel = new Button(buttons, SWT.PUSH);
		btnDel.setText("Удалить...");
		
		addListeners();
		loadServers();
		
		return new Composite(parent, SWT.NULL);
	}

	private void addListeners() {
		btnAdd.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExecuteAddButton();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				ExecuteAddButton();
			}
		});
		
		btnEdit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExecuteEditButton();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				ExecuteEditButton();
			}
		});
		
		btnDel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExecuteDeleteButton();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				ExecuteDeleteButton();
			}
		});
		
		list.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				ListSelectionChanged();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				ListSelectionChanged();
			}
		});
	}
	
	private void ExecuteAddButton() {
		EditServerDialog dialog = new EditServerDialog(getShell());
		if (dialog.open() != Window.OK)
			return;
		NamedDbServerInfo info = dialog.getConnInfo();
		servers.put(info.getName(), info);
		list.add(info.getName());
	}
	
	private void ExecuteEditButton() {
		int pos = list.getSelectionIndex();
		if (pos == -1)
			return;
		String name = list.getItem(pos);
		NamedDbServerInfo info = servers.get(name);
		EditServerDialog dialog = new EditServerDialog(getShell());
		dialog.setConnInfo(info);
		if (dialog.open() != Window.OK)
			return;
		NamedDbServerInfo newinfo = dialog.getConnInfo();
		if (name == newinfo.getName()) {
			servers.replace(newinfo.getName(), newinfo);
		} else {
			list.remove(name);
			servers.put(newinfo.getName(), newinfo);
			list.add(newinfo.getName());
		}
	}
	
	private void ExecuteDeleteButton() {
		int index =  list.getSelectionIndex();
		if (index == -1)
			return;
		String key = list.getItem(index);
		servers.remove(key);
		list.remove(index);;
	}
	
	private void ListSelectionChanged() {
		int index =  list.getSelectionIndex();
		if (index == -1)
			return;
	}
	
	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}
	
	@Override
	public boolean performOk() {
		String data = getServerString();
		getPreferenceStore().putValue("servers", data);
		return true;
	}

	private String getServerString() {
		String str = "";
		for(String alias : list.getItems()) {
			if (!servers.containsKey(alias))
				continue;
			DbServerInfo info = servers.get(alias);
			if (info == null)
				continue;
			String data = "{" + /*alias + "=" +*/ info.toString() + "}";
			str += data;
		}
		return str;
	}
	
	private void loadServers() {
		java.util.List<NamedDbServerInfo> items = ConfigurationManager.getServers();
		servers.clear();
		list.removeAll();
		for (NamedDbServerInfo info : items) {
			servers.put(info.getName(), info);
			list.add(info.getName());
		}
		/*if (str == null || str.length() == 0)
			return;
		String[] servs = str.split("}");
		for (String srv : servs) {
			srv = srv.substring(1, srv.length());
			NamedDbServerInfo info = NamedDbServerInfo.fromString(srv);
			servers.put(info.getName(), info);
			list.add(info.getName());
		}*/
	}
	
}
