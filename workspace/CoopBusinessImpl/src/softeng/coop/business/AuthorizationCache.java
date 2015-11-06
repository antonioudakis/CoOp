package softeng.coop.business;

import softeng.caching.*;
import softeng.caching.Cache;

import javax.persistence.*;
import javax.persistence.criteria.*;

import softeng.coop.dataaccess.*;

/**
 * Singleton cache holding AuthorizationContexts keyed by user name. Null entries are returned
 * if the key does not correspond to a registered user.
 */
public class AuthorizationCache extends Cache<String, AuthorizationContextImpl>
{
	private static final int TIME_TO_LIVE_WHEN_REGISTERED = 240 * 1000;
	
	private static final int TIME_TO_LIVE_WHEN_NOT_REGISTERED = 2 * 1000;
	
	private static final int MAX_ENTRIES_COUNT = 1024;
	
	private static AuthorizationCache instance = new AuthorizationCache();
	
	/**
	 * The strategy used to load an authorization context during a cache miss.
	 */
	private static class LoadStrategy implements ILoadStrategy<String, AuthorizationContextImpl>
	{

		@Override
		public CacheEntry<String, AuthorizationContextImpl> loadItem(String key)
		{
			EntityManager entityManager = EntityManagerProvider.createEntityManager();
			
			try
			{
				
				TypedQuery<AuthenticatedUser> query = createQuery(key, entityManager);
				
				AuthenticatedUser registeredUser = QueryHelper.getFirstOrDefault(query);
				
				AuthorizationContextImpl authorizationContext;
				
				int timeToLive;
				
				if (registeredUser != null)
				{
					authorizationContext = new AuthorizationContextImpl(registeredUser);
					timeToLive = TIME_TO_LIVE_WHEN_REGISTERED;
				}
				else
				{
					authorizationContext = null;
					timeToLive = TIME_TO_LIVE_WHEN_NOT_REGISTERED;
				}
				
				CacheEntry<String, AuthorizationContextImpl> cacheEntry = 
					new CacheEntry<String, AuthorizationContextImpl>(key, authorizationContext, timeToLive);
				
				return cacheEntry;

			}
			finally
			{
				entityManager.close();
			}
			
		}

		@SuppressWarnings("unused")
		private TypedQuery<AuthenticatedUser> createJpqlQuery(String key, EntityManager entityManager)
		{
			TypedQuery<AuthenticatedUser> query = entityManager.createQuery(
					"select au from AuthenticatedUser au " +
					" left join fetch au.department " +
					" left join fetch au.roles role " +
					" left join fetch role.permissions permission " +
					" left join fetch permission.entityAccesses " +
					" where au.userName = :userName ", 
					AuthenticatedUser.class)
					.setParameter("userName", key);
			return query;
		}
		
		private TypedQuery<AuthenticatedUser> createQuery(String key, EntityManager entityManager)
		{
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<AuthenticatedUser> query = builder.createQuery(AuthenticatedUser.class);
			
			Root<AuthenticatedUser> root = query.from(AuthenticatedUser.class);
			
			Fetch<Role, Permission> permissionFetch =
				root
				.fetch(AuthenticatedUser_.roles, JoinType.LEFT)
				.fetch(Role_.permissions, JoinType.LEFT);
			
			permissionFetch.fetch(Permission_.entityAccesses, JoinType.LEFT);
			
			root
			.fetch(AuthenticatedUser_.department, JoinType.LEFT);

			query.where(builder.equal(root.get(AuthenticatedUser_.userName), key));

			return entityManager.createQuery(query);
		}
		
	}

	private AuthorizationCache()
	{
		super(MAX_ENTRIES_COUNT, new LoadStrategy());
	}
	
	
	/**
	 * Get the singleton instance of the cache.
	 */
	public static AuthorizationCache getInstance()
	{
		return instance;
	}
}
