package org.eclipse.db.test.eclipse.extension.configuration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.db.test.eclipse.extension.Activator;
import org.eclipse.db.test.eclipse.extension.preferences.NamedDbServerInfo;
import org.osgi.service.prefs.BackingStoreException;

public class ConfigurationManager {

	/*public static void loadConfig() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID + "/sqlserver");
		if (prefs == null)
			return;
		String[] keys = null;
		try {
			keys = prefs.keys();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		for (String key : keys) {
			String val = prefs.get(key, "");
		}
	}*/
	
	public static void saveProjectData(IProject project, String key, String value) {
		IScopeContext projectScope = new ProjectScope(project);
		IEclipsePreferences prefs = projectScope.getNode(Activator.PLUGIN_ID);
		prefs.put(key, value);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	public static String loadProjectData(IProject project, String key) {
		IScopeContext projectScope = new ProjectScope(project);
		IEclipsePreferences prefs = projectScope.getNode(Activator.PLUGIN_ID);
		return prefs.get(key, "");
	}
	
	public static List<NamedDbServerInfo> getServers() {
		List<NamedDbServerInfo> items = new ArrayList<NamedDbServerInfo>();
		String str = Activator.getDefault().getPreferenceStore().getString("servers");
		if (str == null || str.length() == 0)
			return items;
		String[] servs = str.split("}");
		for (String srv : servs) {
			srv = srv.substring(1, srv.length());
			NamedDbServerInfo info = NamedDbServerInfo.fromString(srv);
			items.add(info);
		}
		return items;
	}
	
	/**
	 * ServerInfo for test storage
	 * @param project
	 * @return
	 */
	public static NamedDbServerInfo getServerInfo(IProject project) {
		if (project == null)
			return null;
		String serverName = ConfigurationManager.loadProjectData(project, "server");
		String serverPass = ConfigurationManager.loadProjectData(project, "server_password");
		if (serverName == null || serverPass == null)
			return null;
		List<NamedDbServerInfo> servers = getServers();
		for (NamedDbServerInfo srv : servers) {
			if (!srv.getName().equals(serverName))
				continue;
			srv.setPassword(serverPass);
			return srv;
		}
		return null;
	}
	
	/**
	 * ServerInfo for test execution
	 * @param project
	 * @return
	 */
	public static NamedDbServerInfo getExecServerInfo(IProject project) {
		if (project == null)
			return null;
		String serverName = ConfigurationManager.loadProjectData(project, "exec_server");
		String serverPass = ConfigurationManager.loadProjectData(project, "exec_server_password");
		if (serverName == null || serverPass == null)
			return null;
		List<NamedDbServerInfo> servers = getServers();
		for (NamedDbServerInfo srv : servers) {
			if (!srv.getName().equals(serverName))
				continue;
			srv.setPassword(serverPass);
			return srv;
		}
		return null;
	}
}
