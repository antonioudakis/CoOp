package softeng.coop.business.contextjpa;

import softeng.coop.business.*;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;

/**
 * Decorator for EntityManager which provides 
 * AuthorizationContext to the current thread.
 */
public class ContextEntityManager 
	extends ContextComponent<EntityManager, Session> implements EntityManager
{
	private ContextEntityTransaction transaction;
	
	public ContextEntityManager(EntityManager inner)
	{
		super(inner);
	}
	
	public ContextEntityManager(EntityManager inner, Session context)
	{
		super(inner, context);
	}

	@Override
	public void clear()
	{
		inner.clear();
	}

	@Override
	public void close()
	{
		inner.close();
	}

	@Override
	public boolean contains(Object arg0)
	{
		return inner.contains(arg0);
	}

	@Override
	public Query createNamedQuery(String arg0)
	{
		return inner.createNamedQuery(arg0);
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1)
	{
		return inner.createNamedQuery(arg0, arg1);
	}

	@Override
	public Query createNativeQuery(String arg0)
	{
		return inner.createNamedQuery(arg0);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Query createNativeQuery(String arg0, Class arg1)
	{
		return inner.createNativeQuery(arg0, arg1);
	}

	@Override
	public Query createNativeQuery(String arg0, String arg1)
	{
		return inner.createNativeQuery(arg0, arg1);
	}

	@Override
	public Query createQuery(String arg0)
	{
		return inner.createQuery(arg0);
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0)
	{
		return inner.createQuery(arg0);
	}

	@Override
	public <T> TypedQuery<T> createQuery(String arg0, Class<T> arg1)
	{
		return inner.createQuery(arg0, arg1);
	}

	@Override
	public void detach(Object arg0)
	{
		inner.detach(arg0);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1)
	{
		CurrentContext.set(context);
		
		try
		{
			return this.inner.find(arg0, arg1);
		}
		finally
		{
			CurrentContext.set(null);
		}
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2)
	{
		CurrentContext.set(context);
		
		try
		{
			return this.inner.find(arg0, arg1, arg2);
		}
		finally
		{
			CurrentContext.set(null);
		}
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2)
	{
		CurrentContext.set(context);
		
		try
		{
			return this.inner.find(arg0, arg1, arg2);
		}
		finally
		{
			CurrentContext.set(null);
		}
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2, Map<String, Object> arg3)
	{
		CurrentContext.set(context);
		
		try
		{
			return this.inner.find(arg0, arg1, arg2, arg3);
		}
		finally
		{
			CurrentContext.set(null);
		}
	}

	@Override
	public void flush()
	{
		inner.flush();
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder()
	{
		return inner.getCriteriaBuilder();
	}

	@Override
	public Object getDelegate()
	{
		return inner.getDelegate();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory()
	{
		return inner.getEntityManagerFactory();
	}

	@Override
	public FlushModeType getFlushMode()
	{
		return inner.getFlushMode();
	}

	@Override
	public LockModeType getLockMode(Object arg0)
	{
		return inner.getLockMode(arg0);
	}

	@Override
	public Metamodel getMetamodel()
	{
		return inner.getMetamodel();
	}

	@Override
	public Map<String, Object> getProperties()
	{
		return inner.getProperties();
	}

	@Override
	public <T> T getReference(Class<T> arg0, Object arg1)
	{
		return inner.getReference(arg0, arg1);
	}

	@Override
	public EntityTransaction getTransaction()
	{
		if (this.transaction == null)
		{
			this.transaction = 
				new ContextEntityTransaction(inner.getTransaction(), context);
		}
		
		return this.transaction;
	}

	@Override
	public boolean isOpen()
	{
		return inner.isOpen();
	}

	@Override
	public void joinTransaction()
	{
		inner.joinTransaction();
	}

	@Override
	public void lock(Object arg0, LockModeType arg1)
	{
		inner.lock(arg0, arg1);
	}

	@Override
	public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2)
	{
		inner.lock(arg0, arg1, arg2);
	}

	@Override
	public <T> T merge(T arg0)
	{
		CurrentContext.set(this.context);
		
		try
		{
			return inner.merge(arg0);
		}
		finally
		{
			CurrentContext.set(null);
		}
	}

	@Override
	public void persist(Object arg0)
	{
		inner.persist(arg0);
	}

	@Override
	public void refresh(Object arg0)
	{
		inner.refresh(arg0);
	}

	@Override
	public void refresh(Object arg0, Map<String, Object> arg1)
	{
		inner.refresh(arg0, arg1);
	}

	@Override
	public void refresh(Object arg0, LockModeType arg1)
	{
		inner.refresh(arg0, arg1);
	}

	@Override
	public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2)
	{
		inner.refresh(arg0, arg1, arg2);
	}

	@Override
	public void remove(Object arg0)
	{
		CurrentContext.set(this.context);
		
		try
		{
			this.inner.remove(arg0);
		}
		finally
		{
			CurrentContext.set(null);
		}
	}

	@Override
	public void setFlushMode(FlushModeType arg0)
	{
		inner.setFlushMode(arg0);
	}

	@Override
	public void setProperty(String arg0, Object arg1)
	{
		inner.setProperty(arg0, arg1);
	}

	@Override
	public <T> T unwrap(Class<T> arg0)
	{
		return inner.unwrap(arg0);
	}

}
