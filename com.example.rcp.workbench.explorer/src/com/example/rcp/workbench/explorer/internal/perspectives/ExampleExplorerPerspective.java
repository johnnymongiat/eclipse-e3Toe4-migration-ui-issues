package com.example.rcp.workbench.explorer.internal.perspectives;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ExampleExplorerPerspective implements IPerspectiveFactory {
	
	public static final String ID = "com.example.rcp.workbench.explorer.perspectives.ExampleExplorer";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		// this is done via extension points
	}

}
