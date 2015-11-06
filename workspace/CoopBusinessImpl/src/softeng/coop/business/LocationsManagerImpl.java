package softeng.coop.business;

import java.util.*;

import softeng.coop.business.locations.*;
import softeng.coop.dataaccess.*;
import javax.persistence.*;
import javax.persistence.criteria.*;

class LocationsManagerImpl extends ManagerBaseImpl implements LocationsManager
{
	private static class LocationsSearchAssembler
		extends QueryHelper.Assembler<Location, LocationsSearchCriteria>
	{
	
		@Override
		public <Q> Root<Location> build(LocationsSearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<Location> locationRoot = query.from(Location.class);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (criteria.getName() != null)
			{
				String namePattern = QueryHelper.getPrefixPattern(criteria.getName());
				
				restrictions.add(
						builder.like(
								locationRoot.<String>get("name"),
//								locationRoot.get(Location_.name), 
								builder.literal(namePattern)
						)
				);
			}
			
			query.where(QueryHelper.listToArray(restrictions));
			
			query.orderBy(builder.asc(locationRoot.get("name")));
			
			query.distinct(true);
			
			return locationRoot;
		}
		
	}

	private static LocationsSearchAssembler locationsSearchAssembler =
		new LocationsSearchAssembler();

	LocationsManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public Location getLocation(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		Location location = entityManager.find(Location.class, id);
		
		return location;
	}
	
	@Override
	public SearchResult<Location> searchLocations(LocationsSearchCriteria searchCriteria)
	{
		return QueryHelper.builderSearch(
				searchCriteria, 
				locationsSearchAssembler, 
				this.getSession().getEntityManager());
	}

	@Override
	public LocationsWriterManager getWriterManager()
	{
		return null;
	}

	@Override
	public List<Location> getRootLocations()
	{
		TypedQuery<Location> query = 
			this.getSession().getEntityManager().createQuery(
					"SELECT loc FROM Location loc WHERE loc.parentLocation IS NULL ORDER BY loc.name", 
					Location.class);
		
		return query.getResultList();
	}

	@Override
	public boolean isWriteable()
	{
		return false;
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "LocationsManager";
	}

}
