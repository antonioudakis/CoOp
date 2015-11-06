package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

@SuppressWarnings("serial")
public class JobPostingPartSpecialPayablesTableComponent
extends SpecialPayablesTableComponent<JobPostingPart, JobPostingPartSpecialPayable>
{
	public JobPostingPartSpecialPayablesTableComponent(CoOp coop)
	{
		super(JobPostingPartSpecialPayable.class, coop);
	}

	@Override
	protected JobPostingPartSpecialPayable createNewElement()
	{
		return Constructor.createJobPostingPartSpecialPayable();
	}

	@Override
	protected void addToParent(JobPostingPartSpecialPayable item)
	{
		item.setJobPostingPart(getParentModel());
	}

	@Override
	protected void removeFromParent(JobPostingPartSpecialPayable item)
	{
		item.setJobPostingPart(null);
	}
}
