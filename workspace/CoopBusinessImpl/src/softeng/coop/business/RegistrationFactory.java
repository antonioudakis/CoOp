package softeng.coop.business;

import org.springframework.beans.factory.*;

import softeng.coop.business.authentication.*;


/**
 * Supplies the IRegistrationStrategy as defined in the configuration.
 */
public class RegistrationFactory
{
	public static IRegistrationStrategy getRegistrationStrategy()
	{
		BeanFactory beanFactory = FactoriesRepository.getRegistrationFactory();
		
		return beanFactory.getBean("registrationStrategy", IRegistrationStrategy.class);
	}
}
