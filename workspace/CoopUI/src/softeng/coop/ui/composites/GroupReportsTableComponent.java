package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

public class GroupReportsTableComponent
extends ReportsTableComponent<Group>
{
	private static final long serialVersionUID = 1L;

	public GroupReportsTableComponent()
	{
		super(ReportScope.Group);
	}

	@Override
	protected void addToParent(Report addedItem)
	{
		addedItem.setGroup(getParentModel());
	}

	@Override
	protected void removeFromParent(Report item)
	{
		item.setGroup(null);
	}

}
