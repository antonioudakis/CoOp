package softeng.coop.ui.viewdefinitions;

import java.util.*;

import softeng.coop.dataaccess.*;

public interface IReportsTableView<P>
extends ITableView<P, Report>
{
	public interface IReportCreator
	{
		Report createReport(ReportType reportType, Map<String, Object> parametersMap);
	}
	
	ReportScope getReportScope();
	
	IReportCreator getReportCreator();
	void setReportCreator(IReportCreator reportCreator);
}
