package softeng.coop.business.authentication.test;

import org.springframework.context.support.*;
import org.junit.*;

//import softeng.coop.dataaccess.*;
//
//import org.springframework.beans.factory.*;
//import org.springframework.test.annotation.ExpectedException;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.*;
//import org.springframework.test.context.junit4.*;
//import org.springframework.test.context.transaction.*;
//import org.springframework.transaction.annotation.*;
//import org.junit.runner.RunWith;
//
//import javax.persistence.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "springJpaSetup.xml" })
//@TransactionConfiguration(defaultRollback=false)
public class SpringTest
{
//	private EntityManager entityManager;
//	
//	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
//	public void setEntityManager(EntityManager entityManager)
//	{
//		this.entityManager = entityManager;
//	}
	
	private IController controller;
	
	private static ClassPathXmlApplicationContext context = createContext();
	
	private static IController createController()
	{
		return context.getBean("controller", IController.class);
	}
	
	private static ClassPathXmlApplicationContext createContext()
	{
		return new ClassPathXmlApplicationContext("springTransactedController.xml");
	}

	public SpringTest()
	{
	}
	
	@Before
	public void setUp()
	{
		this.controller = createController();
		
//		ClassPathXmlApplicationContext context = 
//			new ClassPathXmlApplicationContext("springTransactedController.xml");
//		
//		controller = context.getBean("controller", SpringTransactedController.class);
		
//		this.entityManagerFactory = 
//			context.getBean("coopEntityManagerFactory", EntityManagerFactory.class);
		
//		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
	
	@After
	public void tearDown()
	{
//		this.entityManager.close();
		controller.close();
	}
	
	@Test
	public void addLocation()
	{
		controller.addLocation();
	}
	
	@Test
	public void ensureExtendedManager()
	{
		controller.ensureExtendedManager();
	}
}
