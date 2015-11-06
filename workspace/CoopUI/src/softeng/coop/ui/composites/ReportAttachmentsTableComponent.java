package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

public class ReportAttachmentsTableComponent
extends AttachmentsTableComponent<Report>
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void addToParent(Attachment item)
	{
		item.setReport(getParentModel());
	}

	@Override
	protected void removeFromParent(Attachment item)
	{
		item.setReport(null);
	}
}
