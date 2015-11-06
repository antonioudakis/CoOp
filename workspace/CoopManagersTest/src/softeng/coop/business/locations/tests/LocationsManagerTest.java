package softeng.coop.business.locations.tests;

import org.junit.*;
import softeng.coop.dataaccess.*;
import softeng.coop.business.*;
import softeng.coop.business.locations.*;
import softeng.coop.business.test.ManagerTestBase;

public class LocationsManagerTest extends ManagerTestBase
{
	@Test
	public void acquireManager()
	{
		getManager();
	}
	
	@Test
	public void testRootLocations()
	{
		LocationsManager locationsManager = getManager();

		Assert.assertTrue(
				"Expected root locations.", 
				locationsManager.getRootLocations().size() > 0);
	}

	@Test
	public void searchLocation()
	{
		LocationsManager locationsManager = getManager();
		
		LocationsSearchCriteria criteria = new LocationsSearchCriteria();
		
		criteria.setName("Κάτω μαχαλάς");
		SearchResult<Location> result = locationsManager.searchLocations(criteria);
		
		Assert.assertTrue("Specific location not found.", result.getList().size() == 1);
	}

	private LocationsManager getManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		LocationsManager locationsManager = session.getLocationsManager();
		
		Assert.assertNotNull("aqcuisition of locations manager failed", locationsManager);

		return locationsManager;
	}

}
