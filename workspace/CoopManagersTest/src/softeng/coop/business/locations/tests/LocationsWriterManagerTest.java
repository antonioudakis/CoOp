package softeng.coop.business.locations.tests;

import softeng.coop.business.*;
import softeng.coop.business.locations.*;
import softeng.coop.business.test.ManagerTestBase;
import softeng.coop.dataaccess.*;
import org.junit.*;
import java.util.*;

public class LocationsWriterManagerTest extends ManagerTestBase
{
	private static boolean hasCreatedHierarchy = false;
	
	@Before
	public void createSomeHierarchy()
	{
		if (hasCreatedHierarchy) return;
		
		hasCreatedHierarchy = true;
		
		Location country = new Location();
		
		country.setName("Iceland");
		country.setType(LocationType.Country);
		country.setChildLocations(new HashSet<Location>());
		
		Location city = new Location();
		city.setName("reykjavik");
		city.setType(LocationType.Country);
		city.setParentLocation(country);
		city.setChildLocations(new HashSet<Location>());
		country.getChildLocations().add(city);
		
		LocationsWriterManager manager = getManager();
		
		TransactionScope transactionScope = manager.beginTransaction();
		
		try
		{
			manager.persistLocation(country);
			manager.persistLocation(city);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}
	
	@Test
	public void modify()
	{
		LocationsWriterManager manager = getManager();
		
		LocationsSearchCriteria criteria = new LocationsSearchCriteria();
		
		criteria.setName("UNIV-LOC-MOCK");
		
		SearchResult<Location> result = manager.searchLocations(criteria);
		
		Assert.assertTrue("Didn't find location.", result.getList().size() > 0);
		
		Location location = result.getList().get(0);
		
		location.setName("UNIV-LOC-MOCK-modified");

		manager.persistLocation(location);
		
	}
	
	@Test
	public void cascadeDelete()
	{
		LocationsWriterManager manager = getManager();
		
		LocationsSearchCriteria criteria = new LocationsSearchCriteria();
		
		criteria.setName("Iceland");
		
		SearchResult<Location> result = manager.searchLocations(criteria);
		
		Assert.assertTrue("Didn't find Iceland.", result.getList().size() > 0);
		
		Location iceland = result.getList().get(0);
		
		manager.cascadeDeleteLocation(iceland.getId());
	}
	
	@Test
	public void createLocationsInTransaction()
	{
		Location location = new Location();
		location.setName("Κρυονέρι");
		location.setPath("#");
		location.setType(LocationType.Municipality);
		
		Location otherLocation = new Location();
		otherLocation.setName("Γλυκά νερά");
		location.setPath("#");
		location.setType(LocationType.Country);
		
		LocationsWriterManager manager = getManager();
		
		TransactionScope transactionScope = manager.beginTransaction();
		
		try
		{
			manager.persistLocation(location);
			manager.persistLocation(otherLocation);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
	}
	
	@Test
	public void autoCommitCreateLocation()
	{
		Location location = new Location();
		location.setName("Καβάλα");
		location.setPath("#");
		location.setType(LocationType.Municipality);
		
		LocationsWriterManager manager = getManager();
		
		manager.persistLocation(location);
	}
	
	@Test(expected = IntegrityConstraintViolationException.class)
	public void attemptToDeleteLocationInUse()
	{
		LocationsSearchCriteria criteria = new LocationsSearchCriteria();
		
		criteria.setName("Student city");
		
		LocationsWriterManager manager = getManager();
		
		SearchResult<Location> result = manager.searchLocations(criteria);
		
		Assert.assertTrue("Student city was not found.", result.getList().size() > 0);
		
		Location location = result.getList().get(0);
		
		manager.cascadeDeleteLocation(location.getId());
	}
	
	@Test
	public void moveLocation()
	{
		LocationsWriterManager manager = getManager();
		
		LocationsSearchCriteria criteria = new LocationsSearchCriteria();
		
		criteria.setName("Καβάλα");
		
		SearchResult<Location> result = manager.searchLocations(criteria);
		
		Assert.assertTrue("Καβάλα was not found.", result.getList().size() > 0);
		
		Location kavalaLocation = result.getList().get(0);
		
		criteria.setName("Κρυονέρι");
		
		result = manager.searchLocations(criteria);
		
		Assert.assertTrue("Κρυονέρι was not found.", result.getList().size() > 0);
		
		Location kryoneriLocation = result.getList().get(0);
		
		manager.moveLocation(kryoneriLocation, kavalaLocation);
		
	}
	
	private LocationsWriterManager getManager()
	{
		Assert.assertNotNull("Session aquisition failed", session);
		
		LocationsManager manager = session.getLocationsManager();
		
		Assert.assertNotNull("aqcuisition of locations manager failed", manager);
		
		Assert.assertTrue("Manager should be writable", manager.isWriteable());
		
		LocationsWriterManager writerManager = manager.getWriterManager();
		
		Assert.assertNotNull("Should be able to get the writer manager.", writerManager);
		
		return writerManager;
	}
}
