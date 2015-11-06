package softeng.coop.business.authentication.test;

import org.junit.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.*;

import softeng.coop.dataaccess.*;
//import softeng.coop.business.*;

public class EntityManagerSeparationTest
{
	private ApplicationContext factory = createFactory();
	
	private static ApplicationContext createFactory()
	{
		return 
			new ClassPathXmlApplicationContext("springTransactedController.xml");
	}
	
	private IController createController()
	{
		return factory.getBean("controller", IController.class);
	}
	
	//@Test
	public void maliciousLocationCreation()
	{
		EntityManager entityManager = createEntityManager();
		
		Location maliciousLocation = new Location();
		maliciousLocation.setName("Μια ωραία πεταλούδα");
		maliciousLocation.setPath("hell#");
		maliciousLocation.setType(LocationType.Municipality);
		
		entityManager.persist(maliciousLocation);
		
		IController controller = createController();
		
		controller.addLocation();
	}

	@SuppressWarnings("unused")
	@Test
	public void maliciousLocationTransaction()
	{
		EntityManager entityManager = createEntityManager();
		
		DefaultTransactionDefinition transactionDefinition =
			new DefaultTransactionDefinition();
		
		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
		PlatformTransactionManager transactionManager = 
			factory.getBean("transactionManager", PlatformTransactionManager.class);
		
		TransactionStatus transactionStatus = 
			transactionManager.getTransaction(transactionDefinition);
		
		try
		{
		
			Location maliciousLocation = new Location();
			maliciousLocation.setName("Μια άλλη ωραία πεταλούδα");
			maliciousLocation.setPath("heaven#");
			maliciousLocation.setType(LocationType.Municipality);
			
			entityManager.persist(maliciousLocation);
			
			IController controller = createController();
			
			controller.addLocation();
			
			if (controller != null) throw new RuntimeException("Sabotage!");
			
			transactionManager.commit(transactionStatus);
		
		}
		catch (RuntimeException ex)
		{
			transactionManager.rollback(transactionStatus);
			throw ex;
		}
		
		
	}

	private EntityManager createEntityManager()
	{
		EntityManagerFactory entityManagerFactory =
			factory.getBean("coopEntityManagerFactory", EntityManagerFactory.class);

		EntityManager entityManager = 
			SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
		
		return entityManager;
	}

}
