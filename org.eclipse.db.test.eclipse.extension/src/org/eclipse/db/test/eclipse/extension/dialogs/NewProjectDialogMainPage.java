package org.eclipse.db.test.eclipse.extension.dialogs;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.db.test.common.dal.ConnectionManager;
import org.eclipse.db.test.eclipse.extension.configuration.ConfigurationManager;
import org.eclipse.db.test.eclipse.extension.preferences.NamedDbServerInfo;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.BidiUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class NewProjectDialogMainPage extends WizardPage {

	private static final String txtProjectName = "Название проекта";//$NON-NLS-1$
	private static final String errProjectAlreadyExists = "Имя проекта уже используется";//$NON-NLS-1$
	private static final String errEnterProjectName = "Пожалуйста укажите название проекта";//$NON-NLS-1$
	private String initialProjectFieldValue;
	private Text projectNameField;
	private Text loginTxt;
	private Text passTxt;

	private Text loginExecTxt;
	private Text passExecTxt;

	private NamedDbServerInfo _server;
	private NamedDbServerInfo _execServer;
	
	private Combo serverList;
	private Combo _execSrverList;
	List<NamedDbServerInfo> _servers;
	//private ProjectContentsLocationArea locationArea;
	
	public NewProjectDialogMainPage(String pageName) {
		super(pageName);
		setPageComplete(false);
		setTitle("Новый проект и сервер хранения тестов");
		
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		
		initializeDialogUnits(parent);
		
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        createProjectNameGroup(composite);
        createExecServerNameGroup(composite);
        
        Button btnCheck = new Button(composite, SWT.PUSH);
        btnCheck.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
        btnCheck.setText("Проверить введенные параметры");
        btnCheck.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		setPageComplete(validatePage());
        	}
		});
        
        setErrorMessage(null);
        setMessage(null);
        setControl(composite);
        Dialog.applyDialogFont(composite);
	}
	
    private final void createProjectNameGroup(Composite parent) {
        // project specification group
        Composite projectGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // new project label
        Label projectLabel = new Label(projectGroup, SWT.NONE);
        projectLabel.setText(txtProjectName);
        projectLabel.setFont(parent.getFont());

        // new project name entry field
        projectNameField = new Text(projectGroup, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = 200;
        projectNameField.setLayoutData(data);
        projectNameField.setFont(parent.getFont());

        if (initialProjectFieldValue != null) {
			projectNameField.setText(initialProjectFieldValue);
		}
        projectNameField.addListener(SWT.Modify, nameModifyListener);
        BidiUtils.applyBidiProcessing(projectNameField, BidiUtils.BTD_DEFAULT);
        
        
        Group gpr = new Group(projectGroup, SWT.SHADOW_ETCHED_IN);
        gpr.setText("Сервер хранения тестов");
        gpr.setLayout(layout);
        gpr.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 2));
        
		Label l = new Label(gpr, SWT.CENTER);
		l.setText("Выберите сервер");
		l.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		
        serverList = new Combo(gpr, SWT.READ_ONLY | SWT.FILL);
        serverList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
        _servers = ConfigurationManager.getServers();
        for (NamedDbServerInfo info : _servers) {
			serverList.add(info.getName());
		}
        serverList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ServerChanged();
			}
		});
        
		l = new Label(gpr, SWT.CENTER);
		l.setText("Имя пользователя");
		l.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		
        loginTxt = new Text(gpr, SWT.BORDER | SWT.READ_ONLY);
        loginTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        loginTxt.addListener(SWT.Modify, nameModifyListener);
        
		l = new Label(gpr, SWT.CENTER);
		l.setText("Пароль");
		l.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		
        passTxt = new Text(gpr, SWT.BORDER);
        passTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        passTxt.addListener(SWT.Modify, nameModifyListener);
        
        /*Button btnCheck = new Button(gpr, SWT.PUSH);
        btnCheck.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
        btnCheck.setText("Проверить соединение");
        btnCheck.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		chechcon
        	}
		});*/
    }

    
    private final void createExecServerNameGroup(Composite parent) {
        // project specification group
        Composite projectGroup = new Composite(parent, SWT.NONE);
        
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Group gpr = new Group(projectGroup, SWT.SHADOW_ETCHED_IN);
        gpr.setText("Сервер выполнения тестов");
        gpr.setLayout(layout);
        gpr.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 2));

        
		Label l = new Label(gpr, SWT.CENTER);
		l.setText("Выберите сервер");
		l.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		
        _execSrverList = new Combo(gpr, SWT.READ_ONLY | SWT.FILL);
        _execSrverList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
        //_servers = ConfigurationManager.getServers();
        for (NamedDbServerInfo info : _servers) {
        	_execSrverList.add(info.getName());
		}
        _execSrverList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExecServerChanged();
			}
		});
        
		l = new Label(gpr, SWT.CENTER);
		l.setText("Имя пользователя");
		l.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		
        loginExecTxt = new Text(gpr, SWT.BORDER | SWT.READ_ONLY);
        loginExecTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        loginExecTxt.addListener(SWT.Modify, nameModifyListener);
        
		l = new Label(gpr, SWT.CENTER);
		l.setText("Пароль");
		l.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		
        passExecTxt = new Text(gpr, SWT.BORDER);
        passExecTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        passExecTxt.addListener(SWT.Modify, nameModifyListener);
    }
    
	private void ServerChanged() {
    	int index = serverList.getSelectionIndex();
    	if (index == -1)
    		return;
    	NamedDbServerInfo info = _servers.get(index);
    	loginTxt.setText(info.getUser());
    	//setPageComplete(validatePage());
    	setPageComplete(false);
    }
	
	private void ExecServerChanged() {
    	int index = _execSrverList.getSelectionIndex();
    	if (index == -1)
    		return;
    	NamedDbServerInfo info = _servers.get(index);
    	loginExecTxt.setText(info.getUser());
    	setPageComplete(false);
    }
    
    private Listener nameModifyListener = new Listener() {
        public void handleEvent(Event e) {
            /*boolean valid = validatePage();
            setPageComplete(valid);*/
        	setPageComplete(false);
        }
    };

    private String getProjectNameFieldValue() {
        if (projectNameField == null) {
			return "";
		}

        return projectNameField.getText().trim();
    }
    
    public IProject getProjectHandle() {
        return ResourcesPlugin.getWorkspace().getRoot().getProject(
                getProjectName());
    }
    
    public String getProjectName() {
        if (projectNameField == null) {
			return initialProjectFieldValue;
		}

        return getProjectNameFieldValue();
    }
    
    protected boolean validatePage() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        String projectFieldContents = getProjectNameFieldValue();
        if (projectFieldContents.equals("")) {
            setErrorMessage(errEnterProjectName);
            //setMessage("Please enter a project name");
            return false;
        }

        IStatus nameStatus = workspace.validateName(projectFieldContents,
                IResource.PROJECT);
        if (!nameStatus.isOK()) {
            setErrorMessage(nameStatus.getMessage());
            return false;
        }

        IProject handle = getProjectHandle();
        if (handle.exists()) {
            setErrorMessage(errProjectAlreadyExists);
            return false;
        }
                
        /*IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				getProjectNameFieldValue());*/

        if (!checkConnection())
        	return false;
        if (!checkExecConnection())
        	return false;
        
        setErrorMessage(null);
        setMessage(null);
        return true;
    }
    
    private boolean checkConnection() {
    	int index = serverList.getSelectionIndex();
    	if (index == -1) {
    		setErrorMessage("Выберите сервер");
    		return false;
    	}
    	NamedDbServerInfo info = _servers.get(index);
    	info.setPassword(passTxt.getText());
    	String res = ConnectionManager.checkConnection(info);
    	if (res != null) {
    		setErrorMessage(res);
    		return false;
    	}
    	_server = info;
    	return true;
    }
    
    private boolean checkExecConnection() {
    	int index = _execSrverList.getSelectionIndex();
    	if (index == -1) {
    		setErrorMessage("Выберите сервер");
    		return false;
    	}
    	NamedDbServerInfo info = _servers.get(index);
    	info.setPassword(passExecTxt.getText());
    	String res = ConnectionManager.checkConnection(info);
    	if (res != null) {
    		setErrorMessage(res);
    		return false;
    	}
    	_execServer = info;
    	return true;
    }
    
    public NamedDbServerInfo getServer() {
    	return _server;
    }

    public NamedDbServerInfo getExecServer() {
    	return _execServer;
    }
    
}
