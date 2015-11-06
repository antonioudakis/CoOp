package softeng.coop.business;

import softeng.coop.business.authentication.*;
import org.springframework.beans.factory.*;

/**
 * Supplies the IRegistrationStrategy as defined in the configuration.
 */
public class AuthenticationFactory
{
	public static IAuthenticationProvider getAuthenticationProvider()
	{
		BeanFactory beanFactory = FactoriesRepository.getAuthenticationFactory();
		
		return beanFactory.getBean("authenticationProvider", IAuthenticationProvider.class);
	}
}
