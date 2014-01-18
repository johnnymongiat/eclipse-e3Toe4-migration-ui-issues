package com.example.rcp.workbench.internal.actions;

import java.util.Collections;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.actions.ContributionItemFactory;

public class OpenPerspectiveContributionItem extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		IWorkbenchWindow wbWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IContributionItem item = ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(wbWindow);
		return Collections.singletonList(item).toArray(new IContributionItem[1]);
	}

}
