package softeng.coop.business;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import softeng.coop.dataaccess.*;

class ManagerBaseImpl implements ManagerBase
{
	private Session session;
	
	ManagerBaseImpl(Session session)
	{
		if (session == null) throw new IllegalArgumentException("Argument 'session' must not be null.");
		
		this.session = session;
	}

	@Override
	public String getLiteral(Multilingual multilingual)
	{
		Language preferredLanguage = 
			this.getSession().getAuthenticatedUser().getPreferredLanguage();
		
		return LiteralsManager.getLiteralString(multilingual, preferredLanguage);
	}

	@Override
	public String getLiteral(Multilingual multilingual, Language preferredLanguage)
	{
		return LiteralsManager.getLiteralString(multilingual, preferredLanguage);
	}

	@Override
	public final Session getSession()
	{
		return this.session;
	}
	
	// Secret WriterManagerBase implementation section.
	
	public void setDefaultLiteral(Multilingual multilingual, String value)
	{
		LiteralsManager.setDefaultLiteral(
				this.session.getAuthenticatedUser(), 
				multilingual, 
				value, 
				this.session.getEntityManager());
	}
	
	public void setLiteral(Multilingual multilingual, String value, Language language, boolean isDefault)
	{
		LiteralsManager.setLiteral(
				multilingual, 
				language, 
				value, 
				isDefault, 
				this.session.getEntityManager());
	}
	
	public TransactionScope beginTransaction()
	{
		session.enterTransactionScope();
		return new TransactionScopeImpl(session);
	}

	public TransactionScope beginTransaction(PropagationType propagationType)
	{
		switch (propagationType)
		{
			case Nested:
			case RequiresNew:
				throw new CoOpException("The given propagationType is not supported.");
				
			case Required:
				session.enterTransactionScope();
				break;
				
			case Mandatory:
				session.enterMandatoryTransactionScope();
				break;
		}
		
		return new TransactionScopeImpl(session);
	}

	public void markAsChanged(ITrackedEntity entity)
	{
		Session session = this.getSession();
		AuthenticatedUser user = session.getAuthenticatedUser();
		
		Tracking tracking = entity.getTracking();
		
		if (tracking == null)
		{
			tracking = new Tracking();
			entity.setTracking(tracking);
		}
		
		tracking.setModifiedBy(user);
		Date now = new Date();
		tracking.setModified(now);
		if (tracking.getCreatedBy()== null)
		{
			tracking.setCreatedBy(user);
			tracking.setCreated(now);
		}
	}

	public void validate(Object entity)
	{
		// TODO Auto-generated method stub

	}
	
	/**
	 * Return the localized resources associated with this manager.
	 * The current locale is as defined in the current user's session.
	 */
	protected ResourceBundle getResources()
	{
		Locale locale = this.getSession().getUserLocale();
		
		return ResourceBundle.getBundle(
				"softeng.coop.business.resources." + this.getResourceBundleBaseName(), 
				locale);
	}
	
	/**
	 * Get the localized string that corresponds to a given resource key.
	 * Throws MissingResourceException when the key is not found.
	 */
	protected String getLocalizedString(String key)
	{
		return this.getResources().getString(key);
	}
	
	/**
	 * Specify the base name for the resources associated with this manager.
	 */
	protected String getResourceBundleBaseName()
	{
		return "";
	}

	@Override
	public boolean isWriteable()
	{
		return false;
	}

	@Override
	public Set<Language> getAvailableLanguages()
	{
		EntityManager entityManager = getSession().getEntityManager();
		
		TypedQuery<Language> query = 
			entityManager
			.createQuery("SELECT lan FROM Language lan ORDER BY lan.localeCode", Language.class);
		
		return new LinkedHashSet<Language>(query.getResultList());
	}

}
