package softeng.coop.ui.viewdefinitions.viewmodels;

import java.util.*;

import softeng.coop.dataaccess.*;

public class ReportTypeParameters
{
	private ReportType reportType;
	
	private Map<String, Object> parametersMap; 
	
	public ReportTypeParameters(ReportType reportType, Map<String, Object> parametersMap)
	{
		if (reportType == null) 
			throw new IllegalArgumentException("Argument 'reportType' must not be null.");
		
		this.reportType = reportType;
		this.parametersMap = parametersMap;
	}
	
	public ReportType getReportType()
	{
		return reportType;
	}
	
	public Map<String, Object> getParametersMap()
	{
		return parametersMap;
	}
}
