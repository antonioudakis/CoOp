package softeng.coop.business.contextjpa;

import javax.persistence.*;
import softeng.coop.dataaccess.*;
import softeng.coop.business.*;

/**
 * Filter JPA persistence events of an entity by consulting the EntityAccess
 * reported by the current AuthorizationContext. 
 */
public class EntityAccessControlListener
{
	@PrePersist
	public void onPrePersist(Object entity)
	{
		addToOutstandingAdded(entity);
		
		if (!canWrite(entity))
			throw new AccessDeniedException(entity);
	}
	
	@PostPersist
	public void onPostPersist(Object entity)
	{
		removeFromOutstandingAdded(entity);
	}
	
	@PostLoad
	public void onPostLoad(Object entity)
	{
		if (!canRead(entity))
			throw new AccessDeniedException(entity);
	}
	
	@PreUpdate
	public void onPreUpdate(Object entity)
	{
		addToOutstandingEdited(entity);
		
		if (!canWrite(entity))
			throw new AccessDeniedException(entity);
	}
	
	@PostUpdate
	public void onPostUpdate(Object entity)
	{
		removeFromOutstandingEdited(entity);
	}
	
	@PreRemove
	public void onPreRemove(Object entity)
	{
		addToOutstandingDeleted(entity);
		
		if (!canWrite(entity))
			throw new AccessDeniedException(entity);
	}
	
	@PostRemove
	public void onPostRemove(Object entity)
	{
		removeFromOutstandingDeleted(entity);
	}
	
	private String getEntityName(Object entity)
	{
		Class<? extends Object> type = entity.getClass();
		
		String pack = type.getPackage().getName();
		
		if (!pack.equals("softeng.coop.dataaccess")) return null;
		
		return type.getSimpleName();
	}
	
	private EntityAccess getEntityAccess(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return null;
		
		AuthorizationContext context = session.getAuthorizationContext();
		
		if (context == null) return null;
		
		String entityName = getEntityName(entity);
		
		if (entityName == null) return null;
		
		return context.hasEntityAccess(entityName);
	}
	
	/**
	 * If current context exists, check if the entity can be read
	 * by the current user. If no context exists, allow everything.
	 * Note: The above behavior is subject to change.
	 */
	private boolean canRead(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return true;
		
		AuthorizationContext context = session.getAuthorizationContext();
		
		EntityAccess entityAccess = getEntityAccess(entity);
		
		if (entityAccess == null) return false;

		if (entity instanceof ITrackedEntity)
		{
			ITrackedEntity trackedEntity = (ITrackedEntity)entity;
			
			if (entityAccess.isReadable())
			{
				if (entityAccess.isAccessingAllDepartments()) return true;
				
				AuthenticatedUser createdBy = getCreator(trackedEntity);
				
				if (createdBy != null)
				{
					UserDepartmentCache userDepartmentCache = UserDepartmentCache.getInstance();
					
					int creatorDepartmentId =
						userDepartmentCache.get(createdBy.getId());

					return creatorDepartmentId == context.getDepartmentId();
				}
				else
				{
					return true;
				}
			}
			else if (entityAccess.isOwnReadable())
			{
				AuthenticatedUser createdBy = getCreator(trackedEntity);
				
				if (createdBy != null)
				{
					return createdBy.getId() == context.getUserId();
				}
				else
				{
					return true;
				}
			}
			
		}
		else
		{
			if (entityAccess.isReadable()) return true;
		}
		
		return false;
	}

	private AuthenticatedUser getCreator(ITrackedEntity trackedEntity)
	{
		Tracking tracking = trackedEntity.getTracking();
		
		if (tracking == null) return null;
		
		AuthenticatedUser createdBy = tracking.getCreatedBy();
		
		return createdBy;
	}
	
	/**
	 * If current context exists, check if the entity can be read
	 * by the current user. If no context exists, allow everything.
	 * Note: The above behavior is subject to change.
	 */
	private boolean canWrite(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return true;
		
		AuthorizationContext context = session.getAuthorizationContext();
		
		EntityAccess entityAccess = getEntityAccess(entity);
		
		if (entityAccess == null) return false;

//		if (entity instanceof ITrackedEntity)
//		{
//			ITrackedEntity trackedEntity = (ITrackedEntity)entity;
//			
//			AuthenticatedUser createdBy = getCreator(trackedEntity);
//			
//			if (createdBy != null)
//			{
//				if (entityAccess.isWritable())
//				{
//					if (entityAccess.isAccessingAllDepartments()) return true;
//					
//					UserDepartmentCache userDepartmentCache = UserDepartmentCache.getInstance();
//					
//					int creatorDepartmentId =
//						userDepartmentCache.get(createdBy.getId());
//
//					if (creatorDepartmentId == context.getDepartmentId())
//						return true;
//				}
//				else if (entityAccess.isOwnWritable())
//				{
//					return createdBy.getId() == context.getUserId();
//				}
//			}
//			else
//			{
//				if (entityAccess.isWritable() || entityAccess.getId() == 0) 
//					return true;
//			}
//		}
//		else
//		{
//			if (entityAccess.isWritable()) return true;
//		}
		
		if (entity instanceof ITrackedEntity)
		{
			ITrackedEntity trackedEntity = (ITrackedEntity)entity;
			
			if (entityAccess.isWritable())
			{
				if (entityAccess.isAccessingAllDepartments()) return true;
				
				AuthenticatedUser createdBy = getCreator(trackedEntity);
				
				if (createdBy != null)
				{
					UserDepartmentCache userDepartmentCache = UserDepartmentCache.getInstance();
					
					int creatorDepartmentId =
						userDepartmentCache.get(createdBy.getId());

					return creatorDepartmentId == context.getDepartmentId();
				}
				else
				{
					return true;
				}
			}
			else if (entityAccess.isOwnWritable())
			{
				AuthenticatedUser createdBy = getCreator(trackedEntity);
				
				if (createdBy != null)
				{
					return createdBy.getId() == context.getUserId();
				}
				else
				{
					return true;
				}
			}
			
		}
		else
		{
			if (entityAccess.isWritable()) return true;
		}

		return false;
	}

	private void addToOutstandingAdded(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return;
		
		session.getOutstandingAdded().add(entity);
	}

	private void removeFromOutstandingAdded(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return;
		
		if (!session.isInTransaction())
			session.getOutstandingAdded().remove(entity);
	}

	private void addToOutstandingEdited(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return;
		
		session.getOutstandingEdited().add(entity);
	}

	private void removeFromOutstandingEdited(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return;
		
		if (!session.isInTransaction())
			session.getOutstandingEdited().remove(entity);
	}

	private void addToOutstandingDeleted(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return;
		
		session.getOutstandingDeleted().add(entity);
	}

	private void removeFromOutstandingDeleted(Object entity)
	{
		Session session = CurrentContext.get();
		
		if (session == null) return;
		
		if (!session.isInTransaction())
			session.getOutstandingDeleted().remove(entity);
	}

}
