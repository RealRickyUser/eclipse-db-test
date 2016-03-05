package org.eclipse.db.test.eclipse.extension.preferences;

import org.eclipse.db.test.common.dal.ConnectionManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EditServerDialog extends Dialog {

	private Text aliasTxt;
	private Text serverTxt;
	private Text instanceTxt;
	private Text portTxt;
	private Text databaseTxt;
	private Text userTxt;
	private Text passTxt;
	private Label error;
	
	private NamedDbServerInfo _info;
	
	public EditServerDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		GridLayout root = new GridLayout(2, true);
		area.setLayout(root);
		createText(area, "Название");
		aliasTxt = new Text(area, SWT.NULL);
		aliasTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		createText(area, "Сервер");
		serverTxt = new Text(area, SWT.NULL);
		serverTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		createText(area, "Инстанс");
		instanceTxt = new Text(area, SWT.NULL);
		instanceTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		createText(area, "Порт");
		portTxt = new Text(area, SWT.NULL);
		portTxt.setText("0");
		portTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		createText(area, "База");
		databaseTxt = new Text(area, SWT.NULL);
		databaseTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		createText(area, "Логин");
		userTxt = new Text(area, SWT.NULL);
		userTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		createText(area, "Пароль");
		passTxt = new Text(area, SWT.NULL);
		passTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		error = new Label(parent, SWT.FILL | SWT.WRAP);
		error.setText("");
		error.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		error.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
		loadText();
		return area;
	}
	
	private void loadText() {
		if (_info == null)
			return;
		aliasTxt.setText(_info.getName());
		serverTxt.setText(_info.getServer());
		instanceTxt.setText(_info.getInstance());
		portTxt.setText(Integer.toString(_info.getPort()));
		databaseTxt.setText(_info.getDatabase());
		userTxt.setText(_info.getUser());
		if (_info.getPassword() != null)
			passTxt.setText(_info.getPassword());
	}
	
	private void createText(Composite root, String txt) {
		Label l = new Label(root, SWT.FILL);
		l.setText(txt);
		l.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}
	
	  @Override
	  protected void configureShell(Shell newShell) {
	    super.configureShell(newShell);
	    newShell.setText("Редактирование сервера");
	    newShell.layout(true, true);
	    setShellStyle(getShellStyle() | SWT.RESIZE | SWT.SHELL_TRIM); 
	  }
	  
	  @Override
	  protected void okPressed() {
		  String res = checkConnection();
		  if (res == null) {
			  _info.setName(aliasTxt.getText());
			  super.okPressed();
		  } else {
			  MessageDialog.openError(getShell(), "Ошибка при подключении к серверу", res);
			  
		  }
	  }
	  
	  private String checkConnection() {
		  _info = getConnectionInfo();
		  return ConnectionManager.checkConnection(_info);
	  }
	  
	  private NamedDbServerInfo getConnectionInfo() {
		  NamedDbServerInfo info = new NamedDbServerInfo();
		  info.setServer(serverTxt.getText());
		  info.setInstance(instanceTxt.getText());
		  info.setDatabase(databaseTxt.getText());
		  info.setUser(userTxt.getText());
		  info.setPassword(passTxt.getText());
		  info.setPort(Integer.parseInt(portTxt.getText()));
		  info.setName(aliasTxt.getText());
		  return info;
	  }
	  
	  public NamedDbServerInfo getConnInfo() {
		  return _info;
	  }

	  public void setConnInfo(NamedDbServerInfo info) {
		  _info = info;
	  }

}
