package softeng.coop.business.universities.test;

import softeng.coop.dataaccess.*;
import softeng.coop.business.*;
import softeng.coop.business.locations.LocationsWriterManager;
import softeng.coop.business.test.*;
import softeng.coop.business.universities.*;
import org.junit.*;

public class UniversitiesWriterManagerTest extends ManagerTestBase
{
	
	@Test
	public void createUniversity()
	{
		
		UniversitiesWriterManager manager = getManager();
				
		University university = new University();
		
		//set the UniversityName
//		Language language = this.session.getCurrentUser().getPreferredLanguage();
//		Multilingual multilingual = new Multilingual();
//		multilingual.setLiterals(new HashSet<Literal>());
//		this.session.getEntityManager().persist(multilingual);
//		
//		LiteralsManager litManager = new LiteralsManager();
//		litManager.setLiteral(multilingual, language, "University of Athens", true, this.session.getEntityManager());
//		university.setName(multilingual);
		
		university.setName(new Multilingual());
		manager.setDefaultLiteral(university.getName(), "University of Athens");

		university.setAddress(createEmbedAddress());
		university.setDepartments(null);
		
		TransactionScope transactionScope = manager.beginTransaction();
		
		try 
		{
			manager.persistUniversity(university);
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
		
	}

	@Test
	public void deleteUniversity()
	{
		
		UniversitiesWriterManager manager = getManager();
		
		UniversitiesSearchCriteria universitiesSearchCriteria = new UniversitiesSearchCriteria();
		universitiesSearchCriteria.setUniversitiesName("University of Athens");
		
		SearchResult<University> result = manager.searchUniversities(universitiesSearchCriteria);
		
		Assert.assertTrue("Should return one university", result.getList().size() > 0);

		manager.deleteUniversity(result.getList().get(0).getId());
	}
	
	
	private UniversitiesWriterManager getManager() {
		Assert.assertNotNull("Session aquisition failed", session);
		
		UniversitiesManager manager = session.getUniversitiesManager();
		
		Assert.assertNotNull("aqcuisition of locations manager failed", manager);
		
		Assert.assertTrue("Manager should be writable", manager.isWriteable());
		
		UniversitiesWriterManager writerManager = manager.getWriterManager();
		
		Assert.assertNotNull("Should be able to get the writer manager.", writerManager);
		
		return writerManager;
	}
	
	private EmbeddableAddress createEmbedAddress()
	{
		EmbeddableAddress address = new EmbeddableAddress();
		
		address.setCity("Athens");
		address.setCountry("Greece");
		address.setNumber("15");
		address.setStreet("Acadimias");
		address.setPoBox("174 342");
		address.setType(AddressType.Work);
		address.setLocation(createLocation());
		
		GeoLocation geo = new GeoLocation();
		geo.setLatitude(5);
		geo.setLongtitude(5);
		
		address.setGeoLocation(geo);
		
		return address;

	}

	private Location createLocation()
	{
		LocationsWriterManager locationsManager = 
			session.getLocationsManager().getWriterManager();
		
		Location cityLocation = new Location();
		
		cityLocation.setName("Athens");
		cityLocation.setType(LocationType.Municipality);
		cityLocation.setPath("Athens#");
		
		locationsManager.persistLocation(cityLocation);
		
		return cityLocation;
	}
}
