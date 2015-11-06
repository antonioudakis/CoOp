package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

public class JobPartReportsTableComponent
extends ReportsTableComponent<JobPart>
{
	private static final long serialVersionUID = 1L;

	public JobPartReportsTableComponent(ReportScope reportScope)
	{
		super(ReportScope.JobPart);
	}

	@Override
	protected void addToParent(Report addedItem)
	{
		addedItem.setJobPart(getParentModel());
	}

	@Override
	protected void removeFromParent(Report item)
	{
		item.setJobPart(null);
	}

}
