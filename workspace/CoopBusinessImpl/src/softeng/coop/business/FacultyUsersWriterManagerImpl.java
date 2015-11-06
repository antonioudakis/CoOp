package softeng.coop.business;

import javax.persistence.*;

import softeng.coop.business.faculties.*;
import softeng.coop.dataaccess.FacultyUser;
import softeng.coop.dataaccess.Professor;

public class FacultyUsersWriterManagerImpl
	extends FacultyUsersManagerImpl
	implements FacultyUsersWriterManager
{

	@Override
	public boolean isWriteable()
	{
		return true;
	}

	@Override
	public FacultyUsersWriterManager getWriterManager()
	{
		return this;
	}

	public FacultyUsersWriterManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public void persistFacultyUser(FacultyUser facultyUser)
	{
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.markAsChanged(facultyUser);
			
			this.getSession().getEntityManager().persist(facultyUser);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteFacultyUser(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM FacultyUser fu WHERE fu.id = :id")
				.setParameter("id", id)
				.executeUpdate();
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
	public void persistProfessor(Professor professor)
	{
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.markAsChanged(professor);
			
			this.getSession().getEntityManager().persist(professor);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteProfessor(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM Professor prof WHERE prof.id = :id")
				.setParameter("id", id)
				.executeUpdate();
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
	public void deleteFacultyUser(Object facultyUser)
	{
		if (facultyUser == null) 
			throw new IllegalArgumentException("Argument 'facultyUser' must not be null.");
		
		String userName = ((FacultyUser)facultyUser).getUserName();
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.remove(facultyUser);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
			
			AuthorizationCache authorizationCache = AuthorizationCache.getInstance();
			
			authorizationCache.flush(userName);
		}
	}

}
