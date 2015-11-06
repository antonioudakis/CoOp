package softeng.coop.business;

import softeng.coop.business.authentication.*;
import softeng.coop.business.contextjpa.ContextEntityManager;

import javax.persistence.*;

import org.springframework.context.ApplicationContext;

import java.util.*;

public class SessionFactoryImpl extends SessionFactory
{

	@Override
	public Session login()
	{
		IUser user = getCurrentlyAuthenticatedUser();

		if (user == null) return null;

		AuthorizationContextImpl authorizationContext = null;

		authorizationContext = findRegisteredUser(user);

		if (authorizationContext == null)
		{
			return null;
		}
		
		ApplicationContext applicationContext =
			FactoriesRepository.getCoopBusinessFactory();
		
		SessionSettings sessionSettings = 
			applicationContext.getBean("sessionSettings", SessionSettings.class);
		
		if (sessionSettings.isAutoContext())
		{
			ContextEntityManager entityManager = 
				EntityManagerProvider.createContextEntityManager();

			SessionImpl session = new SessionImpl(authorizationContext, entityManager);
			
			entityManager.setContext(session);
			
			return session;
		}
		else
		{
			EntityManager entityManager =
				EntityManagerProvider.createEntityManager();
			
			return new SessionImpl(authorizationContext, entityManager);
		}
		
	}

	@Override
	public Boolean isUserRegistered()
	{
		IUser user = getCurrentlyAuthenticatedUser();

		if (user == null) return false;

		AuthorizationContextImpl authorizationContext = findRegisteredUser(user);

		return authorizationContext != null;
	}

	@Override
	public RegistrationResult register(Locale locale)
	{
		if (locale == null) 
			throw new IllegalArgumentException("Argument 'locale' must not be null.");
		
		IUser user = getCurrentlyAuthenticatedUser();
		
		if (user == null)
		{
			throw new CoOpException("No authenticated user.");
		}
		
		IRegistrationStrategy registrationStrategy = RegistrationFactory.getRegistrationStrategy();
		
		if (registrationStrategy == null)
		{
			throw new CoOpException(
					"Registration strategy has not been defined in registrationStrategy.xml.");
		}
		
		IEnrollmentByAffiliationStrategy enrollmentStrategy =
			registrationStrategy.getEnrollmentStrategy(user);
		
		if (enrollmentStrategy == null)
		{
			String messageTemplate = getLocalizedString(locale, "ENROLLMENT_DENIED");
			
			String message = 
				String.format(
						messageTemplate, 
						user.getOrganizationUnit().trim(), 
						user.getPrimaryAffiliation().trim());
			
			RegistrationResult registrationResult = new RegistrationResult();
			registrationResult.setStatus(RegistrationStatus.RegistrationFailed);
			
			InformationMessage informationMessage = new InformationMessage();
			informationMessage.setLevel(InformationMessageLevel.Error);
			informationMessage.setText(message);
			
			registrationResult.getInformationMessages().add(informationMessage);
			
			return registrationResult;
		}
		
		EntityManager entityManager = EntityManagerProvider.createEntityManager();
		
		try
		{
			RegistrationResult registrationResult = 
				enrollmentStrategy.enroll(entityManager, user, locale);
			
			switch (registrationResult.getStatus())
			{
				case RegistrationSucceeded:
					AuthorizationCache.getInstance().flush(user.getUserName());
					break;
			}
			
			return registrationResult;
		}
		finally
		{
			entityManager.close();
		}
	}
	
	@Override
	public Boolean isUserAuthenticated()
	{
		return getCurrentlyAuthenticatedUser() != null;
	}
	
	private AuthorizationContextImpl findRegisteredUser(IUser user)
	{
		return AuthorizationCache.getInstance().get(user.getUserName());
	}
	
	private IUser getCurrentlyAuthenticatedUser()
	{
		IAuthenticationProvider authenticationProvider = AuthenticationFactory.getAuthenticationProvider();

		if (authenticationProvider == null) 
			throw new CoOpException("No authentication provider is configured in authenticationProvider.xml.");

		IUser user = authenticationProvider.getAthenticatedUser();
		
		return user;
	}

	/**
	 * Return the localized resources associated with this session factory.
	 */
	protected ResourceBundle getResources(Locale locale)
	{
		return ResourceBundle.getBundle(
				"softeng.coop.business.resources.SessionFactory", 
				locale);
	}

	/**
	 * Get the localized string that corresponds to a given resource key.
	 * Throws MissingResourceException when the key is not found.
	 */
	protected String getLocalizedString(Locale locale, String key)
	{
		return this.getResources(locale).getString(key);
	}
	
}
