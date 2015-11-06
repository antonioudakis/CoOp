package softeng.coop.business;

import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;

import javax.persistence.*;

class CoOpsWriterManagerImpl extends CoOpsManagerImpl implements CoOpsWriterManager
{
	public CoOpsWriterManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public void persistCoOp(CoOp coOp)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.persist(coOp);
			
			this.markAsChanged(coOp);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}

	}

	@Override
	public void deleteCoOp(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM CoOp c WHERE c.id = :id")
				.setParameter("id", id)
				.executeUpdate();
			
			transactionScope.commit();
		}
		catch (PersistenceException ex)
		{
			throw TransactionHelper.translateException(ex);
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public boolean isWriteable()
	{
		return true;
	}

	@Override
	public CoOpsWriterManager getWriterManager()
	{
		return this;
	}

	@Override
	public void persistLesson(Lesson lesson) 
	{
		if (lesson == null) throw new IllegalArgumentException("Argument 'lesson' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			markAsChanged(lesson);
			
			this.getSession().getEntityManager().persist(lesson);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteLesson(int id) 
	{
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager()
				.createQuery("DELETE FROM Lesson ls WHERE ls.id = :id")
				.setParameter("id", id)
				.executeUpdate();
			
			transactionScope.commit();
		}
		catch (PersistenceException ex)
		{
			throw TransactionHelper.translateException(ex);
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteLesson(Lesson lesson)
	{
		if (lesson == null) 
			throw new IllegalArgumentException("Argument 'lesson' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(lesson);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteCoOp(CoOp coOp)
	{
		if (coOp == null) 
			throw new IllegalArgumentException("Argument 'coOp' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(coOp);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}
}
