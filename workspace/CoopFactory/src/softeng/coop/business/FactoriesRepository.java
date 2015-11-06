package softeng.coop.business;

import org.springframework.beans.factory.access.*;
import org.springframework.context.*;
import org.springframework.context.access.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * <p>A central factories repository. Expects the following files in the class path</p>
 * <ul>
 * <li>authenticationProvider.xml: Spring bean XML file that setups an AuthenticationFactory.</li>
 * <li>registrationProvider.xml: Spring bean XML file that setups an RegistrationFactory.</li>
 * </ul>
 */
public class FactoriesRepository
{
	static BeanFactoryLocator factoryLocator = initializeFactoryLocator();

	private static BeanFactoryLocator initializeFactoryLocator()
	{
		return ContextSingletonBeanFactoryLocator.getInstance("classpath*:beanRefFactory.xml");
	}
	
	public static BeanFactoryLocator getFactoryLocator()
	{
		return factoryLocator;
	}
	
	public static ApplicationContext getAuthenticationFactory()
	{
		BeanFactoryReference factoryReference = factoryLocator.useBeanFactory("authenticationProvider");
		
		return (ApplicationContext) factoryReference.getFactory();
	}

	public static ApplicationContext getRegistrationFactory()
	{
		BeanFactoryReference factoryReference = factoryLocator.useBeanFactory("registrationStrategy");
		
		return (ApplicationContext) factoryReference.getFactory();
	}

	public static ApplicationContext getCoopBusinessFactory()
	{
		BeanFactoryReference factoryReference = factoryLocator.useBeanFactory("coopBusinessFactory");
		
		return (ApplicationContext) factoryReference.getFactory();
	}
	
	public static ApplicationContext getReportsFactory()
	{
		BeanFactoryReference factoryReference = factoryLocator.useBeanFactory("reportsFactory");
		
		return (ApplicationContext) factoryReference.getFactory();
	}
	
	public static ApplicationContext getBaseFactory()
	{
		BeanFactoryReference factoryReference = factoryLocator.useBeanFactory("baseFactory");
		
		return (ApplicationContext)factoryReference.getFactory();
	}
	
	public static ApplicationContext getGroupStrategyFactory()
	{
		BeanFactoryReference factoryReference = factoryLocator.useBeanFactory("coopBusinessFactory");
		
		return (ApplicationContext)factoryReference.getFactory();
	}
	
	public static ApplicationContext getInvitationStrategyFactory()
	{
		BeanFactoryReference factoryReference = factoryLocator.useBeanFactory("invitationStrategy");
		
		return (ApplicationContext)factoryReference.getFactory();
	}
	
	public static ApplicationContext createChildFactory(String childFactoryXmlFile, ApplicationContext parentFactory)
	{
		if (childFactoryXmlFile == null) 
			throw new IllegalArgumentException("Argument 'childFactoryXmlFile' must not be null.");
		
		if (parentFactory == null) 
			throw new IllegalArgumentException("Argument 'parentFactory' must not be null.");
		
		String[] locations = { childFactoryXmlFile };
		
		ClassPathXmlApplicationContext context = 
			new ClassPathXmlApplicationContext(locations, parentFactory);
		
		return context;
	}
	
	public static ApplicationContext createChildFactory(String childFactoryXmlFile)
	{
		return createChildFactory(childFactoryXmlFile, getBaseFactory());
	}
}
