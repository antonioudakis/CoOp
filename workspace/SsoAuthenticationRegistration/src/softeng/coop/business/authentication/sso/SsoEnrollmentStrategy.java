package softeng.coop.business.authentication.sso;

import java.util.*;

import javax.persistence.*;

import softeng.coop.business.*;
import softeng.coop.business.authentication.*;

import softeng.coop.dataaccess.*;

abstract class SsoEnrollmentStrategy implements IEnrollmentByAffiliationStrategy
{
	private ResourceBundle resourceBundle;
	
	private boolean capitalized;
	
	SsoEnrollmentStrategy()
	{
		this.capitalized = false;
	}
	
	/**
	 * If true, the user will be enrolled
	 * with name and surname capitalized.
	 */
	public boolean isCapitalized()
	{
		return capitalized;
	}
	
	/**
	 * If true, the user will be enrolled
	 * with name and surname capitalized.
	 */
	void setCapitalized(boolean value)
	{
		this.capitalized = value;
	}
	
	@Override
	public RegistrationResult enroll(EntityManager entityManager, IUser user, Locale locale)
	{
		RegistrationResult result = new RegistrationResult();
		
		resourceBundle = ResourceBundle.getBundle("enrollMessages", locale);
		
		TypedQuery<AuthenticatedUser> existingUserQuery = 
			entityManager
			.createQuery("SELECT eu FROM AuthenticatedUser eu WHERE eu.userName = :userName", AuthenticatedUser.class)
			.setParameter("userName", user.getUserName());
		
		if (existingUserQuery.getResultList().size() > 0)
		{
			String messageText = 
				resourceBundle.getString("USER_ALREADY_REGISTERED");
			
			InformationMessage message = new InformationMessage();
			
			message.setLevel(InformationMessageLevel.Information);
			message.setText(messageText);
			result.setStatus(RegistrationStatus.AlreadyRegistered);
			
			result.getInformationMessages().add(message);
			
			return result;
		}
		
		Department department = XmlSettings.getUserDepartment(user, entityManager);
		
		if (department == null)
		{
			String messageText = 
				resourceBundle.getString("NO_APPROPRIATE_DEPARTMENT_FOR_USER");
			
			InformationMessage message = new InformationMessage();
			
			message.setLevel(InformationMessageLevel.Error);
			message.setText(messageText);
			result.setStatus(RegistrationStatus.RegistrationFailed);
			
			result.getInformationMessages().add(message);
			
			return result;
		}
		
		Language preferredLanguage = 
			XmlSettings.getLanguageOfLocale(locale, entityManager);
		
		entityManager.getTransaction().begin();
		
		try
		{
			AuthenticatedUser authenticatedUser = this.createAuthenticatedUser(user, entityManager, preferredLanguage, department);
			
			if (capitalized)
			{
				authenticatedUser.setName(authenticatedUser.getName().toUpperCase(locale));
				authenticatedUser.setSurname(authenticatedUser.getSurname().toUpperCase(locale));
			}
			
			entityManager.getTransaction().commit();
		}
		catch (RuntimeException ex)
		{
			if (entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();

			throw ex;
		}
		
		result.setStatus(RegistrationStatus.RegistrationSucceeded);
		
		return result;
	}

	/**
	 * Create the authenticated user for a given IUser.
	 * All entities created must be persisted through the supplied
	 * entityManager.
	 */
	protected abstract AuthenticatedUser createAuthenticatedUser(
			IUser user, 
			EntityManager entityManager, 
			Language preferredLanguage,
			Department department);
	
	/**
	 * Handle registration common to all types of authenticated users.
	 */
	protected void basicUserSetup(
			IUser user,
			AuthenticatedUser authenticatedUser,
			EntityManager entityManager,
			Language preferredLanguage,
			Department department)
	{
		authenticatedUser.setPreferredLanguage(preferredLanguage);
		authenticatedUser.setDepartment(department);
		
		authenticatedUser.setUserName(user.getUserName());
		authenticatedUser.setName(user.getFirstName());
		authenticatedUser.setSurname(user.getLastName());
		authenticatedUser.setGender(Gender.Unspecified);
		authenticatedUser.setEmail(user.getEmail());
		
		entityManager.persist(authenticatedUser);
		
		Tracking tracking = new Tracking();
		Date now = new Date();
		tracking.setCreated(now);
		tracking.setModified(now);
		
		authenticatedUser.setTracking(tracking);
		
		Set<Role> authenticatedUserRoles = 
			XmlSettings.getUserRoles(user, entityManager);
		
		authenticatedUser.setRoles(authenticatedUserRoles);
		
	}
}
