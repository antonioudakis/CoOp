package softeng.coop.business.reporting.test;

import softeng.coop.business.test.*;
import softeng.coop.business.reporting.*;
import softeng.coop.dataaccess.*;
import java.util.*;

import org.junit.*;

public class ReportsWriterManagerTest extends ManagerTestBase
{
	@Test
	public void getAllowedReportTypes()
	{
		ReportsWriterManager manager = getReportsManager();
		
		Set<ReportType> allowedReportTypes =
			manager.getAllowedReportTypes();
		
		Assert.assertTrue(
				"Expected to find at least one allowed report type.", 
				allowedReportTypes.size() > 0);
		
		boolean foundMockReportType = false;
		
		for (ReportType reportType : allowedReportTypes)
		{
			if (reportType.getCodeName().equals("mock"))
			{
				foundMockReportType = true;
				break;
			}
		}
		
		Assert.assertTrue(
				"The mock report type was not among the allowed report types.", 
				foundMockReportType);
	}
	
	@Test
	public void getParametersSpecification()
	{
		ReportsWriterManager manager = getReportsManager();
		
		ReportType reportType = manager.getReportType("mock");
		
		Assert.assertNotNull(
				"Should have access to 'mock' report type.", 
				reportType);
		
		List<IParameterType> parameterTypes =
			manager.getParametersSpecification(reportType);
		
		Assert.assertNotNull(
				"The mock report factory has non-null parameters specification but null was given.", 
				parameterTypes);

		Assert.assertTrue(
				String.format(
						"The mock report factory specifies one parameter but %d were found", 
						parameterTypes.size()), 
				parameterTypes.size() == 1);
		
		IParameterType parameterType = parameterTypes.get(0);
		
		Assert.assertEquals(
				String.format(
						"Expected parameter with key 'location' but '%s' was found instead.",
						parameterType.getKey()), 
				parameterType.getKey(), 
				"location");
	}
	
	@Test
	public void createReport()
	{
		ReportsWriterManager manager = getReportsManager();
		
		ReportType reportType = manager.getReportType("mock");
		
		Assert.assertNotNull(
				"Should have access to 'mock' report type.", 
				reportType);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("location", null);
		
		manager.createReport(reportType, parameters);
	}
	
	@Test
	public void createDefaultReport()
	{
		ReportsWriterManager manager = getReportsManager();
		
		ReportType reportType = manager.getReportType("default");
		
		Assert.assertNotNull(
				"Should have access to 'default' report type.", 
				reportType);
		
		manager.createReport(reportType, null);
	}
	
	private ReportsWriterManager getReportsManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);

		ReportsManager manager = session.getReportsManager();

		Assert.assertNotNull("aqcuisition of reports manager failed", manager);

		ReportsWriterManager writerManager = manager.getWriterManager();
		
		Assert.assertNotNull("A writer manager was expected.", writerManager);
		
		return writerManager;
	}

}
