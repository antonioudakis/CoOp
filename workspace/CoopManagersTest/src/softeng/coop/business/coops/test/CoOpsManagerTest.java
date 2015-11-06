package softeng.coop.business.coops.test;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.test.*;
import softeng.coop.business.universities.*;
import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;

import org.junit.*;

public class CoOpsManagerTest extends ManagerTestBase
{
	@Test
	public void acquireManager()
	{
		getCoopsManager();
	}
	
	@Test
	public void searhByDepartment()
	{
		UniversitiesManager universitiesManager = getUniversitiesManager();

		DepartmentsSearchCriteria departmentsCriteria = 
			new DepartmentsSearchCriteria();
		
		departmentsCriteria.setUniversitiesName("NTU");
		
		departmentsCriteria.setDepartmentsName("сглл");
		
		departmentsCriteria.setRetrievingTotalCount(true);
		
		SearchResult<Department> departmentsResult = universitiesManager.searchDepartments(departmentsCriteria);
		
		List<Department> departments = departmentsResult.getList();
		
		Assert.assertTrue("A Department should have been found.", departments.size() > 0);
		
		Department department = departments.get(0);
		
		Assert.assertTrue(
				"Department name should be сгллу", 
				universitiesManager.getLiteral(department.getName()).equals("сгллу"));
		
		CoOpsManager coOpsManager = getCoopsManager();
		
		CoOpSearchCriteria coopCriteria = new CoOpSearchCriteria();
		
		coopCriteria.setDepartment(department);
		coopCriteria.setActiveOnly(true);
		coopCriteria.setRetrievingTotalCount(true);
		
		SearchResult<CoOp> coopResults = coOpsManager.searchCoOps(coopCriteria);
		
		List<CoOp> coops = coopResults.getList();
		
		Assert.assertTrue("Should have found coop.", coops.size() > 0);
	}

	private UniversitiesManager getUniversitiesManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		UniversitiesManager universitiesManager = session.getUniversitiesManager();
		
		Assert.assertNotNull("aqcuisition of universities manager failed", universitiesManager);

		return universitiesManager;
	}

	private CoOpsManager getCoopsManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		CoOpsManager coopsManager = session.getCoOpsManager();
		
		Assert.assertNotNull("aqcuisition of universities manager failed", coopsManager);

		return coopsManager;
	}

}
