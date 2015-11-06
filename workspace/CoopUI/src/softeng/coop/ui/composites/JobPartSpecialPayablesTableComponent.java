package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

@SuppressWarnings("serial")
public class JobPartSpecialPayablesTableComponent
extends SpecialPayablesTableComponent<JobPart, JobPartSpecialPayable>
{

	public JobPartSpecialPayablesTableComponent(CoOp coop)
	{
		super(JobPartSpecialPayable.class, coop);
	}

	@Override
	protected JobPartSpecialPayable createNewElement()
	{
		return Constructor.createJobPartSpecialPayable();
	}

	@Override
	protected void addToParent(JobPartSpecialPayable item)
	{
		item.setJobPart(getParentModel());
	}

	@Override
	protected void removeFromParent(JobPartSpecialPayable item)
	{
		item.setJobPart(null);
	}
}
