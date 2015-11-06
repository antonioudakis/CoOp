package softeng.coop.business.reporting.test;

import softeng.coop.business.test.*;
import softeng.coop.business.reporting.*;
import softeng.coop.dataaccess.*;
import org.junit.*;

public class ReportsManagerTest extends ManagerTestBase
{
	@Test
	public void aqcuireManager()
	{
		getReportsManager();
	}
	
	@Test
	public void getReportTypeByCodeName()
	{
		ReportsManager reportsManager = getReportsManager();
		
		String codeName = "mock";
		
		ReportType reportType = reportsManager.getReportType(codeName);
		
		Assert.assertNotNull("Should have found mock report type.", reportType);
	}

	private ReportsManager getReportsManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);

		ReportsManager manager = session.getReportsManager();

		Assert.assertNotNull("aqcuisition of reports manager failed", manager);

		return manager;
	}
}
