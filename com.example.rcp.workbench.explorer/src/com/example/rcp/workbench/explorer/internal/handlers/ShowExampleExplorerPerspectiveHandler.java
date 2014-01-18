package com.example.rcp.workbench.explorer.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.example.rcp.workbench.explorer.internal.perspectives.ExampleExplorerPerspective;

public class ShowExampleExplorerPerspectiveHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		try {
			workbench.showPerspective(ExampleExplorerPerspective.ID, workbench.getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			throw new ExecutionException("Unable to open Example Explorer perspective", e);
		}
		return null;
	}

}
