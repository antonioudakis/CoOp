package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;

public class RegistrationReportsTableComponent
extends ReportsTableComponent<Registration>
{
	private static final long serialVersionUID = 1L;

	public RegistrationReportsTableComponent()
	{
		super(ReportScope.Registration);
	}

	@Override
	protected void addToParent(Report addedItem)
	{
		addedItem.setRegistration(getParentModel());
	}

	@Override
	protected void removeFromParent(Report item)
	{
		item.setRegistration(null);
	}
}
