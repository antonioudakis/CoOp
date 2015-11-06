package softeng.coop.ui.composites;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.dialogs.*;

public class CoopReportsTableComponent
extends ReportsTableComponent<CoOp>
{
	private static final long serialVersionUID = 1L;

	public CoopReportsTableComponent()
	{
		super(ReportScope.CoOp);
	}

	@Override
	protected void addToParent(Report addedItem)
	{
		addedItem.setCoOp(getParentModel());
	}

	@Override
	protected ChooseReportTypeDialog createChooseReportTypeDialog()
	{
		ChooseReportTypeDialog chooseReportTypeDialog = new ChooseReportTypeDialog(reportScope, getParentModel());
		
		return chooseReportTypeDialog;
	}

	@Override
	protected void removeFromParent(Report item)
	{
		item.setCoOp(null);
	}
}
