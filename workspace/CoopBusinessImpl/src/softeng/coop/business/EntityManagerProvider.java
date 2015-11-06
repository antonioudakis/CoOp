package softeng.coop.business;

import javax.persistence.*;
import softeng.coop.business.contextjpa.*;
//import org.springframework.beans.factory.*;
//import org.springframework.orm.jpa.SharedEntityManagerCreator;

public class EntityManagerProvider
{
	private static EntityManagerFactory factory = initializeFactory();
	
//	public static EntityManagerFactory getFactory()
//	{
//		return factory;
//	}
	
	public static EntityManager createEntityManager()
	{
		return factory.createEntityManager();

		// Spring's shared entity manager.
//		EntityManager entityManager = 
//			SharedEntityManagerCreator.createSharedEntityManager(factory);
//		
//		return entityManager;
	}
	
	public static ContextEntityManager createContextEntityManager()
	{
		EntityManager innerEntityManager = createEntityManager();
		
		return new ContextEntityManager(innerEntityManager);
	}

	private static EntityManagerFactory initializeFactory()
	{
		// The following gets an EntityManagerFactory directly from JPA.
		return Persistence.createEntityManagerFactory("coop");
		
		// This one gets an EntityManagerFactory defined in coopBusinessFactory.xml
		// via Spring.
//		BeanFactory beanFactory = FactoriesRepository.getCoopBusinessFactory();
//		
//		return beanFactory.getBean("coopEntityManagerFactory", EntityManagerFactory.class);
	}
}
