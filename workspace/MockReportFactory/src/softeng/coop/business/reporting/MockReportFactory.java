package softeng.coop.business.reporting;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;

public class MockReportFactory implements IReportFactory
{
	private static class LocationParameterType implements IParameterType
	{

		@Override
		public String getName(Locale locale)
		{
			return "Some location";
		}

		@Override
		public String getDescription(Locale locale)
		{
			return "Demo description";
		}

		@Override
		public String getKey()
		{
			return "location";
		}

		@Override
		public DataType getDataType()
		{
			return DataType.Location;
		}

		@Override
		public boolean isRequired() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getDefaultValue(Locale locale)
		{
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Override
	public Report createReport(Session session, ReportType reportType, Object parameters)
	{
		Report report = new Report();
		
		report.setReportType(reportType);
		
		report.setTitle("an epic report");
		
		report.setAttachments(new HashSet<Attachment>());
		
		Attachment attachment = new Attachment();
		
		attachment.setName("the document");
		
		attachment.setReport(report);
		report.getAttachments().add(attachment);
		
		return report;
	}

	@Override
	public List<IParameterType> getParametersSpecification()
	{
		ArrayList<IParameterType> parameters = new ArrayList<IParameterType>(1);
		
		parameters.add(new LocationParameterType());
		
		return parameters;
	}

}
