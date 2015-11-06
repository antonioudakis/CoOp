package softeng.coop.business.contextjpa;

import javax.persistence.*;
import javax.persistence.criteria.*;

import softeng.coop.business.*;
import softeng.coop.dataaccess.*;

import softeng.caching.Cache;
import softeng.caching.CacheEntry;

class UserDepartmentCache
	extends Cache<Integer, Integer>
{
	private static final int TIME_TO_LIVE = 3600 * 1000;
	
	private static final int MAX_ENTRIES_COUNT = 1024;
	
	private static final UserDepartmentCache instance =
		new UserDepartmentCache();
	
	private static class LoadStrategy implements ILoadStrategy<Integer, Integer>
	{
		@Override
		public CacheEntry<Integer, Integer> loadItem(Integer key)
		{
			EntityManager entityManager = EntityManagerProvider.createEntityManager();
			
			try
			{
				CriteriaBuilder builder = entityManager.getCriteriaBuilder();
				
				CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
				
				Root<AuthenticatedUser> authenticatedUserRoot = 
					query.from(AuthenticatedUser.class);
				
				query.where(builder.equal(
						authenticatedUserRoot.get(AuthenticatedUser_.id), 
						builder.literal(key)));
				
				query.select(
						authenticatedUserRoot
						.get(AuthenticatedUser_.department)
						.get(Department_.id));
				
				TypedQuery<Integer> typedQuery = entityManager.createQuery(query);
				
				Integer departmentId = typedQuery.getSingleResult();
				
				CacheEntry<Integer, Integer> entry =
					new CacheEntry<Integer, Integer>(key, departmentId, TIME_TO_LIVE);
				
				return entry;
			}
			finally
			{
				entityManager.close();
			}
		}
	}

	private UserDepartmentCache()
	{
		super(MAX_ENTRIES_COUNT, new LoadStrategy());
	}
	
	public static UserDepartmentCache getInstance()
	{
		return instance;
	}
}
