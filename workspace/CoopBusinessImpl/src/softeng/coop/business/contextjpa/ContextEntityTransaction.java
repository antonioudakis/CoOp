package softeng.coop.business.contextjpa;

import softeng.coop.business.*;

import javax.persistence.*;

/**
 * Decorator for EntityTransaction which provides 
 * AuthorizationContext to the current thread.
 */
public class ContextEntityTransaction 
	extends ContextComponent<EntityTransaction, Session> implements EntityTransaction
{
	public ContextEntityTransaction(
			EntityTransaction inner, 
			Session context)
	{
		super(inner, context);
	}

	public ContextEntityTransaction(
			EntityTransaction inner)
	{
		super(inner);
	}

	@Override
	public void begin()
	{
		inner.begin();
	}

	@Override
	public void commit()
	{
		CurrentContext.set(this.context);

		try
		{
			this.inner.commit();
		}
		finally
		{
			CurrentContext.set(null);
		}
	}

	@Override
	public boolean getRollbackOnly()
	{
		return inner.getRollbackOnly();
	}

	@Override
	public boolean isActive()
	{
		return inner.isActive();
	}

	@Override
	public void rollback()
	{
		inner.rollback();
	}

	@Override
	public void setRollbackOnly()
	{
		inner.setRollbackOnly();
	}

}
