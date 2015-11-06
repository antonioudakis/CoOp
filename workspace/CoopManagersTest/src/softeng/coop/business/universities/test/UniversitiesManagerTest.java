package softeng.coop.business.universities.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import softeng.coop.business.SearchResult;
import softeng.coop.business.locations.LocationsManager;
import softeng.coop.business.locations.LocationsSearchCriteria;
import softeng.coop.business.test.ManagerTestBase;
import softeng.coop.business.universities.DepartmentsSearchCriteria;
import softeng.coop.business.universities.UniversitiesManager;
import softeng.coop.business.universities.UniversitiesSearchCriteria;
import softeng.coop.dataaccess.Department;
import softeng.coop.dataaccess.Location;
import softeng.coop.dataaccess.University;

public class UniversitiesManagerTest extends ManagerTestBase
{
	@Test
	public void acquireManager()
	{
		getUniversitiesManager();
	}
	
	@Test
	public void searchUniversitiesByName()
	{
		UniversitiesManager manager = getUniversitiesManager();
		
		UniversitiesSearchCriteria criteria = new UniversitiesSearchCriteria();
		
		criteria.setUniversitiesName("NTU");
		
		SearchResult<University> result = manager.searchUniversities(criteria);
		
		Assert.assertTrue("Should have found university 'NTUA'", result.getList().size() == 1);
		
		University university = result.getList().get(0);
		
		String name = manager.getLiteral(university.getName());
		
		Assert.assertTrue("University's name should be NTUA", name.equals("NTUA"));
	}
	
	@Test
	public void searchUniversitiesByLocation()
	{
		UniversitiesManager universitiesManager = getUniversitiesManager();
		
		LocationsManager locationsManager = getLocationsManager();
		
		LocationsSearchCriteria locationsCriteria = new LocationsSearchCriteria();
		
		locationsCriteria.setName("UNIV-LOC-MOCK");
		
		SearchResult<Location> locationsResult = 
			locationsManager.searchLocations(locationsCriteria);
		
		Assert.assertTrue("Specific location not found.", locationsResult.getList().size() == 1);
		
		Location location = locationsResult.getList().get(0);

		UniversitiesSearchCriteria criteria = new UniversitiesSearchCriteria();
		
		criteria.setUniversitiesName("NTU");
		criteria.setLocation(location);
		
		SearchResult<University> result = universitiesManager.searchUniversities(criteria);
		
		Assert.assertTrue("Should have found university 'NTUA'", result.getList().size() == 1);
		
		University university = result.getList().get(0);
		
		String name = universitiesManager.getLiteral(university.getName());
		
		Assert.assertTrue("University's name should be NTUA", name.equals("NTUA"));
	}
	
	@Test
	public void searchDepartmentsByName()
	{
		UniversitiesManager manager = getUniversitiesManager();

		DepartmentsSearchCriteria criteria = new DepartmentsSearchCriteria();
		
		criteria.setUniversitiesName("NTU");
		
		criteria.setDepartmentsName("сглл");
		
		SearchResult<Department> result = manager.searchDepartments(criteria);
		
		List<Department> departments = result.getList();
		
		Assert.assertTrue("A Department should have been found.", departments.size() > 0);
		
		Department department = departments.get(0);
		
		Assert.assertTrue(
				"Department name should be сгллу", 
				manager.getLiteral(department.getName()).equals("сгллу"));
	}
	
	@Test
	public void searchDepartmentsByLocation()
	{
		UniversitiesManager universitiesManager = getUniversitiesManager();

		LocationsManager locationsManager = getLocationsManager();
		
		LocationsSearchCriteria locationsCriteria = new LocationsSearchCriteria();
		
		locationsCriteria.setName("UNIV-LOC-MOCK");
		
		SearchResult<Location> locationsResult = 
			locationsManager.searchLocations(locationsCriteria);
		
		Assert.assertTrue("Specific location not found.", locationsResult.getList().size() == 1);
		
		Location location = locationsResult.getList().get(0);

		DepartmentsSearchCriteria departmentsCriteria = new DepartmentsSearchCriteria();
		
		departmentsCriteria.setUniversitiesName("NTU");
		departmentsCriteria.setLocation(location);
		
		SearchResult<Department> result = universitiesManager.searchDepartments(departmentsCriteria);
		
		List<Department> departments = result.getList();
		
		Assert.assertTrue("A Department should have been found.", departments.size() > 0);
		
		Department department = departments.get(0);
		
		Assert.assertTrue(
				"Department name should be сгллу", 
				universitiesManager.getLiteral(department.getName()).equals("сгллу"));
	}

	private LocationsManager getLocationsManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		LocationsManager locationsManager = this.session.getLocationsManager();

		Assert.assertNotNull("aqcuisition of locations manager failed", locationsManager);

		return locationsManager;
	}
	
	private UniversitiesManager getUniversitiesManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		UniversitiesManager universitiesManager = session.getUniversitiesManager();
		
		Assert.assertNotNull("aqcuisition of universities manager failed", universitiesManager);

		return universitiesManager;
	}


}
