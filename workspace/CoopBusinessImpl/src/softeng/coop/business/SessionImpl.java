package softeng.coop.business;

import java.lang.reflect.Method;
import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import softeng.coop.business.companies.CompaniesManager;
import softeng.coop.business.companies.CompaniesWriterManager;
import softeng.coop.business.coops.CoOpsManager;
import softeng.coop.business.coops.CoOpsWriterManager;
import softeng.coop.business.faculties.FacultyUsersManager;
import softeng.coop.business.faculties.FacultyUsersWriterManager;
import softeng.coop.business.jobpostings.JobPostingsManager;
import softeng.coop.business.jobpostings.JobPostingsWriterManager;
import softeng.coop.business.jobs.JobsManager;
import softeng.coop.business.jobs.JobsWriterManager;
import softeng.coop.business.locations.LocationsManager;
import softeng.coop.business.locations.LocationsWriterManager;
import softeng.coop.business.payments.PaymentsManager;
import softeng.coop.business.payments.PaymentsWriterManager;
import softeng.coop.business.reporting.ReportsManager;
import softeng.coop.business.reporting.ReportsWriterManager;
import softeng.coop.business.students.StudentsManager;
import softeng.coop.business.students.StudentsWriterManager;
import softeng.coop.business.universities.UniversitiesManager;
import softeng.coop.business.universities.UniversitiesWriterManager;
import softeng.coop.dataaccess.*;

public class SessionImpl extends Session
{
	/**
	 * Associates a system language with a locale.
	 */
	private static class LanguageLocale
	{
		private int languageId;
		private Locale locale;
		private boolean isDefault;
		
		public LanguageLocale(Language language, Locale locale)
		{
			if (language == null) 
				throw new IllegalArgumentException("Argument 'language' must not be null.");
			if (locale == null) 
				throw new IllegalArgumentException("Argument 'locale' must not be null.");
			
			this.languageId = language.getId();
			this.locale = locale;
			this.isDefault = language.is_default();
		}

		public int getLanguageId()
		{
			return languageId;
		}
		public Locale getLocale()
		{
			return locale;
		}

		@SuppressWarnings("unused")
		public boolean isDefault()
		{
			return isDefault;
		}

	}
	
	private EntityManager entityManager;
	
	private int transactionCount;
	
	private List<Locale> availableLocales;
	
	private static Object languagesLock = new Object();
	
	private static Map<String, LanguageLocale> languagesMap = null;
	
	private static LanguageLocale defaultLanguageLocale = null;
	
	private AuthenticatedUser authenticatedUser;
	
	private Set<Object> outstandingAdded = new HashSet<Object>();
	
	private Set<Object> outstandingDeleted = new HashSet<Object>();
	
	private Set<Object> outstandingEdited = new HashSet<Object>();
	
	private PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
	
	public SessionImpl(AuthorizationContextImpl authorizationContext, EntityManager entityManager)
	{
		if (authorizationContext == null) throw new IllegalArgumentException("Argument 'authorizationContext' must not be null.");
		if (entityManager == null) throw new IllegalArgumentException("Argument 'entityManager' must not be null.");
		
		this.authorizationContext = authorizationContext;
		this.entityManager = entityManager;
		this.transactionCount = 0;
	}
	
	@Override
	void enterTransactionScope()
	{
		if (++this.transactionCount == 1)
		{
			this.entityManager.getTransaction().begin();
		}
	}
	
	@Override
	void enterMandatoryTransactionScope()
	{
		if (this.transactionCount == 0)
			throw new CoOpException(
					"No active transaction exists. An existing transaction is mandatory.");

		this.transactionCount++;
	}
	
	@Override
	boolean exitTransactionScope(boolean shouldCommit)
	{
		EntityTransaction transaction = this.entityManager.getTransaction();
		
		if (!shouldCommit) transaction.setRollbackOnly();
		
		boolean rollbackVoteExists = transaction.getRollbackOnly();

		if (this.transactionCount <= 1)
		{
			if (this.transactionCount <= 0)
			{
				this.transactionCount = 0;

				throw new CoOpException(
						"exitTransactionScope invokation does not match corresponding enterTransactionScope.");
			}
			
			try
			{
				if (!rollbackVoteExists)
				{
					transaction.commit();
				}
				else
				{
					transaction.rollback();
				}
				
			}
			catch (PersistenceException ex)
			{
				throw TransactionHelper.translateException(ex);
			}
			finally
			{
				this.transactionCount = 0;
			}
			
			outstandingAdded.clear();
			outstandingDeleted.clear();
			outstandingEdited.clear();
		}
		else
		{
			this.transactionCount--;
		}
		
		return !rollbackVoteExists;
	}

	@Override
	public void dispose()
	{
		this.entityManager.close();
	}

	@Override
	public PermissionCheck hasPermission(String name)
	{
		PermissionCheck check = new PermissionCheck();

		check.setPermissionName(name);
		check.setGranted(this.authorizationContext.hasPermission(name));
		
		return check;
	}

	@Override
	public List<PermissionCheck> hasPermissions(Permission... permissions)
	{
		ArrayList<PermissionCheck> checks = new ArrayList<PermissionCheck>(permissions.length);
		
		for (Permission permission : permissions)
		{
			checks.add(hasPermission(permission.getName()));
		}
		
		return checks;
	}

	@Override
	public List<PermissionCheck> hasPermissions(String... permissionNames)
	{
		ArrayList<PermissionCheck> checks = new ArrayList<PermissionCheck>(permissionNames.length);
		
		for (String permissionName : permissionNames)
		{
			checks.add(hasPermission(permissionName));
		}
		
		return checks;
	}

	@Override
	public CompaniesManager getCompaniesManager()
	{
		if (this.authorizationContext.hasManager(CompaniesWriterManager.class.getSimpleName()))
		{
			return new CompaniesWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(CompaniesManager.class.getSimpleName()))
		{
			return new CompaniesManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public LocationsManager getLocationsManager()
	{
		if (this.authorizationContext.hasManager(LocationsWriterManager.class.getSimpleName()))
		{
			return new LocationsWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(LocationsManager.class.getSimpleName()))
		{
			return new LocationsManagerImpl(this);
		}
		
		return null;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private <T> T weave(T instance)
	{
		Object obj = instance;
		
		ClassPathXmlApplicationContext factory = 
			(ClassPathXmlApplicationContext)FactoriesRepository.getCoopBusinessFactory();
		
		AbstractBeanFactory abf = (AbstractBeanFactory)factory.getBeanFactory();
		
		for (BeanPostProcessor processor : abf.getBeanPostProcessors())
		{
			obj = processor.postProcessAfterInitialization(obj, "");
		}
		
		return (T)obj;
	}

	@Override
	public StudentsManager getStudentsManager()
	{
		if (this.authorizationContext.hasManager(StudentsWriterManager.class.getSimpleName()))
		{
			return new StudentsWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(StudentsManager.class.getSimpleName()))
		{
			return new StudentsManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public UniversitiesManager getUniversitiesManager()
	{
		if (this.authorizationContext.hasManager(UniversitiesWriterManager.class.getSimpleName()))
		{
			return new UniversitiesWriterManagerImp(this);
		}
		else if (this.authorizationContext.hasManager(UniversitiesManager.class.getSimpleName()))
		{
			return new UniversitiesManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public FacultyUsersManager getFacultyUsersManager()
	{
		if (this.authorizationContext.hasManager(FacultyUsersWriterManager.class.getSimpleName()))
		{
			return new FacultyUsersWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(FacultyUsersManager.class.getSimpleName()))
		{
			return new FacultyUsersManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public CoOpsManager getCoOpsManager()
	{
		if (this.authorizationContext.hasManager(CoOpsWriterManager.class.getSimpleName()))
		{
			return new CoOpsWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(CoOpsManager.class.getSimpleName()))
		{
			return new CoOpsManagerImpl(this);
		}
		
		return null;
	}
	
	@Override
	public JobsManager getJobsManager() 
	{
		if (this.authorizationContext.hasManager(JobsWriterManager.class.getSimpleName()))
		{
			return new JobsWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(JobsManager.class.getSimpleName()))
		{
			return new JobsManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public JobPostingsManager getJobPostingsManager()
	{
		if (this.authorizationContext.hasManager(JobPostingsWriterManager.class.getSimpleName()))
		{
			return new JobPostingsWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(JobPostingsManager.class.getSimpleName()))
		{
			return new JobPostingsManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public PaymentsManager getPaymentsManager()
	{
		if (this.authorizationContext.hasManager(PaymentsWriterManager.class.getSimpleName()))
		{
			return new PaymentsWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(PaymentsManager.class.getSimpleName()))
		{
			return new PaymentsManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public ReportsManager getReportsManager()
	{
		if (this.authorizationContext.hasManager(ReportsWriterManager.class.getSimpleName()))
		{
			return new ReportsWriterManagerImpl(this);
		}
		else if (this.authorizationContext.hasManager(ReportsManager.class.getSimpleName()))
		{
			return new ReportsManagerImpl(this);
		}
		
		return null;
	}

	@Override
	public CoOp getDefaultCoop()
	{
		return this.getAuthenticatedUser().getDefaultCoOp();
	}

	@Override
	public void setDefaultCoop(CoOp coop)
	{
		AuthenticatedUser user = this.getAuthenticatedUser();
		
		boolean shouldCommit = false;

		this.enterTransactionScope();
		
		try
		{
			user.setDefaultCoOp(coop);
			
			shouldCommit = true;
		}
		finally
		{
			this.exitTransactionScope(shouldCommit);
		}
	}

	@Override
	public ManagerCheck hasManager(String managerName)
	{
		ManagerCheck check = new ManagerCheck();
		
		check.setManagerName(managerName);
		check.setGranted(this.authorizationContext.hasManager(managerName));
		
		return check;
	}

	@Override
	public List<ManagerCheck> hasManagers(String... managerNames)
	{
		ArrayList<ManagerCheck> checks = new ArrayList<ManagerCheck>(managerNames.length);
		
		for (String managerName: managerNames)
		{
			checks.add(hasManager(managerName));
		}
		
		return checks;
	}

	@Override
	public boolean isLocaleAvailable(Locale locale)
	{
		Map<String, LanguageLocale> map = this.getLanguagesMap();
		
		return map.containsKey(locale.getLanguage());
	}

	@Override
	public boolean setUserLocale(Locale locale)
	{
		this.enterTransactionScope();
		
		boolean shouldCommit = false;
		
		try
		{
			AuthenticatedUser user = this.getAuthenticatedUser();
			
			Language language = this.getLanguageByLocale(locale);
			
			if (language == null)
			{
				// Get default language.
				
				locale = getDefaultLocale();
				
				if (locale == null)
				{
					// No default language found. Dirty: Pick the first.
					List<Locale> availableLocales = getAvailableLocales();
					
					if (availableLocales.size() > 0)
						locale = availableLocales.get(0);
					else
						throw new CoOpException("No system languages defined.");
				}
				
				language = this.getLanguageByLocale(locale);
				
				user.setPreferredLanguage(language);

				shouldCommit = true;
				
				return false;
			}
			else
			{
				user.setPreferredLanguage(language);
				
				shouldCommit = true;
				
				return true;
			}
			
		}
		finally
		{
			this.exitTransactionScope(shouldCommit);
		}
	}

	@Override
	public Locale getUserLocale()
	{
		AuthenticatedUser user = this.getAuthenticatedUser();
		
		return new Locale(user.getPreferredLanguage().getLocaleCode());
	}

	@Override
	public AuthenticatedUser getAuthenticatedUser()
	{
		if (this.authenticatedUser == null)
		{
			// Due to a horrible bug in hibernate, the query polymorphism fails 
			// when loading AuthenticatedUser. For example, if the curent user
			// is a Student, the polymorphic query would load a dynamic subclass of
			// the abstract AuthenticatedUser instead!
			// So attempt to load possible subclasses one by one.
			
			// First, try with the statistically more frequent visitors: Students.
			this.authenticatedUser = loadUser(Student.class);
			
			if (this.authenticatedUser == null)
				this.authenticatedUser = loadUser(Professor.class);
			
			if (this.authenticatedUser == null)
				this.authenticatedUser = loadUser(FacultyUser.class);

		}
		
		return this.authenticatedUser;
	}

	private <T extends AuthenticatedUser> AuthenticatedUser loadUser(Class<T> type)
	{
		TypedQuery<T> query = 
			entityManager.createQuery(
					String.format(
						"SELECT au FROM %s au " +
						"LEFT JOIN FETCH au.preferredLanguage WHERE au.id = :userId",
						type.getCanonicalName()),
					type)
			.setParameter("userId", this.getAuthorizationContext().getUserId());
		
		return QueryHelper.getFirstOrDefault(query);
	}
	
	private Map<String, LanguageLocale> getLanguagesMap()
	{
		synchronized (languagesLock)
		{
			if (languagesMap == null)
			{
				TypedQuery<Language> query = 
					this.entityManager.createQuery(
							"SELECT lan FROM Language lan", 
							Language.class);
				
				List<Language> languages = query.getResultList();
				
				languagesMap = new HashMap<String, LanguageLocale>();
				
				for (Language language : languages)
				{
					LanguageLocale languageLocale = new LanguageLocale(
							language, 
							new Locale(language.getLocaleCode())
					);
					
					languagesMap.put(
							language.getLocaleCode(), 
							languageLocale
					);
					
					if (language.is_default()) defaultLanguageLocale = languageLocale;
				}
			}
			
		}
		
		return languagesMap;
	}
	
	private Language getLanguageByLocale(Locale locale)
	{
		String localeCode = locale.getLanguage();
		
		Map<String, LanguageLocale> map = this.getLanguagesMap();
		
		LanguageLocale languageLocale = map.get(localeCode);
		
		if (languageLocale != null)
			return this.entityManager.getReference(Language.class, languageLocale.getLanguageId());
		else
			return null;
	}

	@Override
	public List<Locale> getAvailableLocales()
	{
		if (this.availableLocales == null)
		{
			Map<String, LanguageLocale> map = this.getLanguagesMap();
			
			this.availableLocales = new ArrayList<Locale>(map.values().size());
			
			for (LanguageLocale languageLocale : map.values())
			{
				availableLocales.add(languageLocale.getLocale());
			}
		}
		
		return this.availableLocales;
	}
	
	private Locale getDefaultLocale()
	{
		// Ensure that the available languages have been loaded.
		this.getLanguagesMap();
		
		return defaultLanguageLocale.getLocale();
	}

	@Override
	public void refreshEntity(Object entity)
	{
		this.entityManager.refresh(entity);
	}

	@Override
	public boolean containsEntity(Object entity)
	{
		return this.entityManager.contains(entity);
	}

	@Override
	public ManagerBase getBaseManager()
	{
		return new ManagerBaseImpl(this);
	}

	@Override
	public Set<Object> getOutstandingAdded()
	{
		return outstandingAdded;
	}

	@Override
	public Set<Object> getOutstandingDeleted()
	{
		return outstandingDeleted;
	}

	@Override
	public Set<Object> getOutstandingEdited()
	{
		return outstandingEdited;
	}

	@Override
	public boolean revertOutstanding()
	{
		// TODO: This requires extensive testing.
		try
		{
			for (Object added : outstandingAdded)
			{
				try
				{
					//entityManager.remove(added);
					entityManager.detach(added);
					
					resetId(added);
				}
				catch (RuntimeException ex)
				{
					// Swallow everything.
				}
			}
			
			outstandingAdded.clear();
			
			for (Object edited : outstandingEdited)
			{
				try
				{
					if (entityManager.contains(edited))
						entityManager.refresh(edited);
				}
				catch (RuntimeException ex)
				{
					// Swallow everything.
				}
			}
			
			outstandingEdited.clear();
			
			for (Object deleted : outstandingDeleted)
			{
				try
				{
					//entityManager.merge(deleted);
					entityManager.persist(deleted);
					//entityManager.detach(deleted);
				}
				catch (RuntimeException ex)
				{
					// Swallow everything.
				}
			}
			
			outstandingDeleted.clear();
		}
		catch (RuntimeException ex)
		{
			return false;
		}
		
		return true;
	}

	private void resetId(Object added)
	{
		// Use reflection to find primary key and reset it.

		if (added == null) return;
		
		try
		{
			Method idSetterMethod = added.getClass().getMethod("setId", new Class<?>[] { int.class });
			
			idSetterMethod.invoke(added, new Integer(0));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	EntityManager getEntityManager()
	{
		return this.entityManager;
	}

	@Override
	public Set<Role> getRoles()
	{
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Role> query = builder.createQuery(Role.class);
		
		Root<Role> roleRoot = query.from(Role.class);
		
		query.orderBy(builder.asc(roleRoot.get(Role_.name)));
		
		return new LinkedHashSet<Role>(entityManager.createQuery(query).getResultList());
	}

	@Override
	public Role getRole(int id)
	{
		return entityManager.find(Role.class, id);
	}

	@Override
	public Role getRole(String name)
	{
		if (name == null) 
			throw new IllegalArgumentException("Argument 'name' must not be null.");
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Role> query = builder.createQuery(Role.class);
		
		Root<Role> roleRoot = query.from(Role.class);
		
		query.where(builder.equal(roleRoot.get(Role_.name), name));
		
		return QueryHelper.getFirstOrDefault(entityManager.createQuery(query));
	}

	@Override
	public boolean isLoaded(Object entity, String propertyName)
	{
		if (entity == null) 
			throw new IllegalArgumentException("Argument 'entity' must not be null.");
		
		if (propertyName == null) 
			throw new IllegalArgumentException("Argument 'propertyName' must not be null.");
		
		return persistenceUtil.isLoaded(entity, propertyName);
	}

	@Override
	public void clean()
	{
		// Force reloading of authenticated user upon next invocation of getAuthenticatedUser().
		authenticatedUser = null;
		
		entityManager.clear();
	}

	@Override
	public Object attach(Object entity)
	{
		if (entity == null) 
			throw new IllegalArgumentException("Argument 'entity' must not be null.");

		return entityManager.merge(entity);
	}

	@Override
	public void detach(Object entity)
	{
		if (entity == null) 
			throw new IllegalArgumentException("Argument 'entity' must not be null.");
		
		entityManager.detach(entity);
	}

	@Override
	public final boolean isInTransaction()
	{
		return transactionCount > 0;
	}

}
