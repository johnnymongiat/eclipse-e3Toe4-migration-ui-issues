package com.example.rcp.workbench.internal;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    // Actions - important to allocate these only in makeActions, and then use them
    // in the fill methods.  This ensures that the actions aren't recreated
    // when fillActionBars is called with FILL_PROXY.
    private IWorkbenchAction copyAction;
	private IWorkbenchAction cutAction;
	private IWorkbenchAction deleteAction;
	private IWorkbenchAction dynamicHelpAction;
	private IWorkbenchAction findAction;
	private IWorkbenchAction helpAction;
    private IWorkbenchAction introAction;
	private IWorkbenchAction pasteAction;
	private IWorkbenchAction printAction;
	private IWorkbenchAction redoAction;
	private IWorkbenchAction resetPerspectiveAction;
    private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAllAction;
    private IWorkbenchAction saveAsAction;
	private IWorkbenchAction savePerspectiveAction;
	private IWorkbenchAction searchHelpAction;
    private IWorkbenchAction undoAction;
    private IWorkbenchAction exitAction;
    

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(final IWorkbenchWindow window) {
        // Creates the actions and registers them.
        // Registering is needed to ensure that key bindings work.
        // The corresponding commands keybindings are defined in the plugin.xml file.
        // Registering also provides automatic disposal of the actions when
        // the window is closed.
    	
    	copyAction = ActionFactory.COPY.create(window);
		register(copyAction);
		cutAction = ActionFactory.CUT.create(window);
		register(cutAction);
		deleteAction = ActionFactory.DELETE.create(window);
		register(deleteAction);
		dynamicHelpAction = ActionFactory.DYNAMIC_HELP.create(window);
		register(dynamicHelpAction);
		findAction = ActionFactory.FIND.create(window);
		register(findAction);
		helpAction = ActionFactory.HELP_CONTENTS.create(window);
		register(helpAction);
		if (window.getWorkbench().getIntroManager().hasIntro()) {
            introAction = ActionFactory.INTRO.create(window);
            register(introAction);
        }
		pasteAction = ActionFactory.PASTE.create(window);
		register(pasteAction);
		printAction = ActionFactory.PRINT.create(window);
		register(printAction);
		redoAction = ActionFactory.REDO.create(window);
		register(redoAction);
		resetPerspectiveAction = ActionFactory.RESET_PERSPECTIVE.create(window);
		register(resetPerspectiveAction);
		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);
		saveAllAction = ActionFactory.SAVE_ALL.create(window);
		register(saveAllAction);
		saveAsAction = ActionFactory.SAVE_AS.create(window);
		register(saveAsAction);
		savePerspectiveAction = ActionFactory.SAVE_PERSPECTIVE.create(window);
		register(savePerspectiveAction);
		searchHelpAction = ActionFactory.HELP_SEARCH.create(window);
		register(searchHelpAction);
		undoAction = ActionFactory.UNDO.create(window);
		register(undoAction);
        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
    }
    
    /*protected void fillMenuBar(IMenuManager menuBar) {
        menuBar.add(new MenuManager("&File", IWorkbenchActionConstants.M_FILE));
        menuBar.add(new MenuManager("&Edit", IWorkbenchActionConstants.M_EDIT));
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.M_NAVIGATE));
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW));
        menuBar.add(new MenuManager("&Help", IWorkbenchActionConstants.M_HELP));
    }*/

    protected void fillCoolBar(ICoolBarManager coolBar) {
    	IToolBarManager saveToolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
	    saveToolbar.add(saveAction);
	    saveToolbar.add(saveAllAction);
		saveToolbar.add(printAction);
		coolBar.add(new ToolBarContributionItem(saveToolbar, ActionFactory.SAVE.getId()));
    }
    
}
