package softeng.coop.business;

import softeng.coop.business.locations.*;
import softeng.coop.dataaccess.*;
import javax.persistence.*;

class LocationsWriterManagerImpl extends LocationsManagerImpl implements LocationsWriterManager
{

	@Override
	public LocationsWriterManager getWriterManager()
	{
		return this;
	}

	@Override
	public boolean isWriteable()
	{
		return true;
	}

	LocationsWriterManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public void cascadeDeleteLocation(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			Location location = entityManager.find(Location.class, id);
			
			if (location != null) deleteLocation(location);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	private void deleteLocation(Location location)
	{
		for (Location childLocation : location.getChildLocations())
		{
			this.deleteLocation(childLocation);
		}
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		entityManager.remove(location);
	}

	@Override
	public boolean moveLocation(Location location, Location newParent)
	{
		if (location == null) 
			throw new IllegalArgumentException("Argument 'location' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			if (!ensureNewParentIsNotChild(location, newParent)) return false;
			
			Location oldParent = location.getParentLocation();
			
			if (oldParent != null)
			{
				oldParent.getChildLocations().remove(location);
			}
	
			if (newParent != null)
			{
				newParent.getChildLocations().add(location);
			}
			
			location.setParentLocation(newParent);
			
			adjustDescendantsPaths(location);
			
			this.markAsChanged(location);

			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
		return true;
	}
	
	private void adjustDescendantsPaths(Location location)
	{
		String parentPath = null;
		
		Location parentLocation = location.getParentLocation();
		
		if (parentLocation != null)
			parentPath = parentLocation.getPath();
		else
			parentPath = "";
		
		location.setPath(parentPath + getPathFragment(location));
		
		for (Location childLocation : location.getChildLocations())
		{
			this.adjustDescendantsPaths(childLocation);
		}
	}

	private boolean ensureNewParentIsNotChild(Location location, Location newParent)
	{
		if (newParent == null) return true;
		
		return !newParent.getPath().startsWith(location.getPath());
	}

	@Override
	public void persistLocation(Location location)
	{
		if (location == null) 
			throw new IllegalArgumentException("Argument 'location' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(location);
			
			location.setPath(computePath(location));
		
			this.markAsChanged(location);
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
	}
	
	private String computePath(Location location)
	{
		StringBuilder stringBuilder = new StringBuilder(90);
		
		for (Location ancestor = location; ancestor != null; ancestor = ancestor.getParentLocation())
		{
			stringBuilder.insert(0, getPathFragment(ancestor));
		}
		
		return stringBuilder.toString();
	}
	
	private String getPathFragment(Location location)
	{
		return String.format("%08x#", location.getId());
	}

	@Override
	public void cascadeDeleteLocation(Location location)
	{
		if (location == null) 
			throw new IllegalArgumentException("Argument 'location' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			deleteLocation(location);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}
	
}
