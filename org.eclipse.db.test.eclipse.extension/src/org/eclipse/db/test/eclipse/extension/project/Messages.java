package org.eclipse.db.test.eclipse.extension.project;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.db.test.eclipse.extension.project.messages"; //$NON-NLS-1$
	public static String ProjectComponentCategory_Components;
	public static String ProjectScriptCategory_Scripts;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
