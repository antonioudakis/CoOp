package softeng.coop.business.authentication.test;

import javax.persistence.*;

import softeng.coop.dataaccess.*;

import org.junit.*;
import org.springframework.transaction.annotation.*;

//@ContextConfiguration(locations = { "springJpaSetup.xml" })
public class SpringTransactedController implements IController
{
	private EntityManager entityManager;
	
	public SpringTransactedController()
	{
		System.out.printf("Got in constructor SpringTransactedController\n");
	}
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
		
		System.out.print("In setEntityManager\n");
	}

	/* (non-Javadoc)
	 * @see softeng.coop.business.authentication.test.IController#addLocation()
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addLocation()
	{
//		this.entityManager.getTransaction().begin();
		
		Location location = new Location();
		
		location.setName("Κολοπετινίτσα");
		location.setPath("#");
		
		this.entityManager.persist(location);
		
		try
		{
		
			this.nestedLocation();
		
		}
		catch (RuntimeException ex)
		{
			System.out.printf("Swallowed exception: %s.", ex.getMessage());
		}
		
//		this.entityManager.getTransaction().commit();
	}
	
	/* (non-Javadoc)
	 * @see softeng.coop.business.authentication.test.IController#nestedLocation()
	 */
	@Override
	@Transactional(propagation=Propagation.MANDATORY)
	public void nestedLocation()
	{
		Location location = new Location();
		
		location.setName("Τρούμπα");
		location.setPath("#");
		
		this.entityManager.persist(location);
		// throw new RuntimeException("Error");
	}
	
	/* (non-Javadoc)
	 * @see softeng.coop.business.authentication.test.IController#ensureExtendedManager()
	 */
	@Override
	public void ensureExtendedManager()
	{
		Assert.assertTrue(entityManager.isOpen());
	}

	@Override
	public void close()
	{
		this.entityManager.close();
		
		System.out.printf("closed entityManager.\n");
	}

}
