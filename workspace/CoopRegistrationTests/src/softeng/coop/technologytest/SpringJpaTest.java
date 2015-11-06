package softeng.coop.technologytest;

import org.junit.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import softeng.coop.dataaccess.*;

import javax.persistence.*;

public class SpringJpaTest
{
	private static EntityManagerFactory entityManagerFactory;
	
	private static ApplicationContext context;
	
	private static ApplicationContext createContext()
	{
		return 
			new ClassPathXmlApplicationContext("springTransactedController.xml");
	}
	
	@BeforeClass
	public static void createEntityManagerFactory()
	{
//		return
//			Persistence.createEntityManagerFactory("coop");
		context = createContext();
		entityManagerFactory = context.getBean("coopEntityManagerFactory", EntityManagerFactory.class);
	}
	
	
	@Before
	public void setup()
	{
		EntityManager entityManager = createEntityManager();
		
		DefaultTransactionDefinition transactionDefinition =
			new DefaultTransactionDefinition();
		
		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		
		PlatformTransactionManager transactionManager = 
			context.getBean("transactionManager", PlatformTransactionManager.class);
		
		TransactionStatus transactionStatus = 
			transactionManager.getTransaction(transactionDefinition);
		
		entityManager.joinTransaction();

		try
		{
			Location parentLocation = new Location();
			parentLocation.setName("Μια ωραία πεταλούδα");
			parentLocation.setPath("hell#");
			parentLocation.setType(LocationType.Municipality);
			
			entityManager.persist(parentLocation);
		
			Location childLocation = new Location();
			childLocation.setName("Μια άλλη ωραία πεταλούδα");
			childLocation.setPath("heaven#");
			childLocation.setType(LocationType.Municipality);
			childLocation.setParentLocation(parentLocation);
			
			entityManager.persist(childLocation);
			
			transactionManager.commit(transactionStatus);
		
		}
		catch (RuntimeException ex)
		{
			transactionManager.rollback(transactionStatus);
			throw ex;
		}
		finally
		{
			entityManager.close();
		}
		

//		EntityManager entityManager = createRawEntityManager();
//
//		entityManager.getTransaction().begin();
//		
//		try
//		{
//			Location parentLocation = new Location();
//			parentLocation.setName("Μια ωραία πεταλούδα");
//			parentLocation.setPath("hell#");
//			parentLocation.setType(LocationType.Municipality);
//			
//			entityManager.persist(parentLocation);
//		
//			Location childLocation = new Location();
//			childLocation.setName("Μια άλλη ωραία πεταλούδα");
//			childLocation.setPath("heaven#");
//			childLocation.setType(LocationType.Municipality);
//			childLocation.setParentLocation(parentLocation);
//			
//			entityManager.persist(childLocation);
//			
//			entityManager.getTransaction().commit();
//		
//		}
//		catch (RuntimeException ex)
//		{
//			entityManager.getTransaction().rollback();
//			throw ex;
//		}
//		finally
//		{
//			entityManager.close();
//		}
//		
	}
	
	@Test
	public void fetchParent()
	{
//		DefaultTransactionDefinition transactionDefinition =
//			new DefaultTransactionDefinition();
//		
//		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		
//		PlatformTransactionManager transactionManager = 
//			factory.getBean("transactionManager", PlatformTransactionManager.class);
//		
//		TransactionStatus transactionStatus = 
//			transactionManager.getTransaction(transactionDefinition);

		EntityManager entityManager = createEntityManager();
		
		try
		{
			TypedQuery<Location> query = 
				entityManager.createQuery(
						"SELECT loc FROM Location loc WHERE loc.name = :name", 
						Location.class)
				.setParameter("name", "Μια ωραία πεταλούδα")
				.setMaxResults(1);
			
			Location location = query.getSingleResult();
			
			Assert.assertTrue("The session should have been open.", entityManager.isOpen());
			
			Assert.assertTrue(
					"Must have one child.", 
					location.getChildLocations().size() == 1);
			
			if (location.getChildLocations().size() > 0)
			{
				Location childLocation = location.getChildLocations().iterator().next();
				
				Assert.assertEquals(
						"Expected child locationname 'Μια άλλη ωραία πεταλούδα'", 
						childLocation.getName(), 
						"Μια άλλη ωραία πεταλούδα");
			}
			
//			transactionManager.commit(transactionStatus);
		}
		catch (RuntimeException ex)
		{
//			transactionManager.rollback(transactionStatus);
			throw ex;
		}
		finally
		{
			entityManager.close();
		}
	}
	
	
	private EntityManager createEntityManager()
	{
		EntityManager entityManager = 
			//SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
			ExtendedEntityManagerCreator.createApplicationManagedEntityManager(createRawEntityManager(), null, null);
		
		return entityManager;
	}

	private EntityManager createRawEntityManager()
	{
		EntityManager entityManager =
			entityManagerFactory.createEntityManager();
		
		return entityManager;
	}

}
