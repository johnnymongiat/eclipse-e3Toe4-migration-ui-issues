package com.example.rcp.workbench.internal;

import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.osgi.framework.Bundle;

/**
 * This workbench advisor creates the window advisor, and specifies the
 * perspective id for the initial window.
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	final static String ICONS_PATH = "icons/full/"; //$NON-NLS-1$
	final static String PATH_OBJECT = ICONS_PATH + "obj16/"; //$NON-NLS-1$
	final static String PATH_ETOOL = ICONS_PATH + "etool16/"; //Enabled toolbar icons.//$NON-NLS-1$

	final static String IMG_OBJS_ERROR_PATH = "IMG_OBJS_ERROR_PATH"; //$NON-NLS-1$
	final static String IMG_OBJS_WARNING_PATH = "IMG_OBJS_WARNING_PATH"; //$NON-NLS-1$
	final static String IMG_OBJS_INFO_PATH = "IMG_OBJS_INFO_PATH"; //$NON-NLS-1$
	final static String IMG_ETOOL_PROBLEM_CATEGORY = "IMG_ETOOL_PROBLEM_CATEGORY"; //$NON-NLS-1$

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		configurer.setSaveAndRestore(true);

		// this hooks up the adapters from the core (mainly resource) components
		// to the navigator support
		IDE.registerAdapters();

		// this gets the icons for projects correctly registered
		Bundle ideBundle = Platform.getBundle(IDE.class.getPackage().getName());
		declareWorkbenchImage(configurer, ideBundle,
				IDE.SharedImages.IMG_OBJ_PROJECT,
				PATH_OBJECT + "prj_obj.gif", true); //$NON-NLS-1$
		declareWorkbenchImage(configurer, ideBundle,
				IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED, PATH_OBJECT
						+ "cprj_obj.gif", //$NON-NLS-1$
				true);

		// fixes for problems view icons
		declareWorkbenchImage(configurer, ideBundle,
				ApplicationWorkbenchAdvisor.IMG_OBJS_ERROR_PATH, PATH_OBJECT
						+ "error_tsk.gif", true); //$NON-NLS-1$

		declareWorkbenchImage(configurer, ideBundle,
				ApplicationWorkbenchAdvisor.IMG_OBJS_WARNING_PATH, PATH_OBJECT
						+ "warn_tsk.gif", true); //$NON-NLS-1$

		declareWorkbenchImage(configurer, ideBundle,
				ApplicationWorkbenchAdvisor.IMG_OBJS_INFO_PATH, PATH_OBJECT
						+ "info_tsk.gif", true); //$NON-NLS-1$

		declareWorkbenchImage(configurer, ideBundle,
				ApplicationWorkbenchAdvisor.IMG_ETOOL_PROBLEM_CATEGORY,
				PATH_ETOOL + "problem_category.gif", true); //$NON-NLS-1$
	}

	public String getInitialWindowPerspectiveId() {
		return null; // defer to the consumers to set the default perspective
	}

	public IAdaptable getDefaultPageInput() {
		// this hooks the root of the Common Navigator up to the workspace
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	public void postStartup() {
		super.postStartup();

		// load existing projects into the Project Explorer (if the view is
		// restored upon start-up)
		IWorkbenchWindow[] workbenchWindows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (IWorkbenchWindow wbWindow : workbenchWindows) {
			for (IWorkbenchPage wbPage : wbWindow.getPages()) {
				ProjectExplorer projExplorer = (ProjectExplorer) wbPage
						.findView(ProjectExplorer.VIEW_ID);
				if (projExplorer != null) {
					projExplorer.getCommonViewer().setInput(
							getDefaultPageInput());
				}
				break;
			}
		}

		// register the WorkbenchPartListener
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (window == null) {
					StatusManager
							.getManager()
							.handle(new Status(
									Status.WARNING,
									Activator.PLUGIN_ID,
									"Could not register WorkbenchPartListener since active workbench window is null!"),
									StatusManager.LOG);
					return;
				}
				final IPartService partService = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getPartService();
				partService.addPartListener(new WorkbenchPartListener());
			}
		});

		// set the "Insert spaces for tabs" Text Editor preference to true by
		// default
		IPreferenceStore editorsUIPreferenceStore = EditorsUI
				.getPreferenceStore();
		editorsUIPreferenceStore
				.setDefault(
						AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS,
						true);
	}

	/**
	 * Declares an image to be present within the Workbench.
	 * 
	 * @param configurer
	 * @param ideBundle
	 * @param symbolicName
	 * @param path
	 * @param shared
	 */
	private void declareWorkbenchImage(IWorkbenchConfigurer configurer,
			Bundle ideBundle, String symbolicName, String path, boolean shared) {
		URL url = ideBundle.getEntry(path);
		ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(url);
		configurer.declareImage(symbolicName, imgDescriptor, shared);
	}

	private static final class WorkbenchPartListener implements IPartListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart
		 * )
		 */
		public void partActivated(IWorkbenchPart workbenchPart) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.
		 * IWorkbenchPart)
		 */
		public void partBroughtToTop(IWorkbenchPart workbenchPart) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart
		 * )
		 */
		public void partClosed(IWorkbenchPart workbenchPart) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.
		 * IWorkbenchPart)
		 */
		public void partDeactivated(IWorkbenchPart workbenchPart) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart
		 * )
		 */
		public void partOpened(IWorkbenchPart workbenchPart) {
			// load existing projects into the Project Explorer (if the view is
			// opened post-startup)
			if (workbenchPart.getSite().getId().equals(ProjectExplorer.VIEW_ID)) {
				ProjectExplorer projExplorer = (ProjectExplorer) workbenchPart;
				if (projExplorer != null) {
					projExplorer.getCommonViewer().setInput(
							ResourcesPlugin.getWorkspace().getRoot());
				}
			}
		}
	}

}
