package org.eclipse.db.test.eclipse.extension;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.db.test.eclipse.extension"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		System.out.println("Requested image ");
		System.out.println("Requested image " + path);
		/*try {
			throw new Exception("Requested image " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		Bundle bundle = getDefault().getBundle();
		System.out.println("This bundle " + bundle.getSymbolicName());
		URL file = FileLocator.find(getDefault().getBundle(), new Path(path), null);
		System.out.println("URL " + file.toExternalForm());
		ImageDescriptor myImage = ImageDescriptor.createFromURL(file);
		return myImage;
	}
	
    public static Image getImage(String imagePath) {
        ImageDescriptor imageDescriptor = getImageDescriptor(imagePath);
        Image image = imageDescriptor.createImage();
        return image;
    }
}
