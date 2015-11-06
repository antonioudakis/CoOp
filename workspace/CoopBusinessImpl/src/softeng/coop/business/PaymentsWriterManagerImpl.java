package softeng.coop.business;

import javax.persistence.*;

import softeng.coop.business.payments.PaymentsWriterManager;
import softeng.coop.dataaccess.*;

public class PaymentsWriterManagerImpl extends PaymentsManagerImpl implements
		PaymentsWriterManager 
{

	PaymentsWriterManagerImpl(Session session) 
	{
		super(session);
	}
	
	@Override
	public boolean isWriteable() 
	{
		return true;
	}

	@Override
	public PaymentsWriterManager getWriterManager() 
	{
		return this;
	}

	@Override
	public void persistPayment(Payment payment) 
	{
		if (payment == null) 
			throw new IllegalArgumentException("Argument 'payment' must not be null.");
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.persist(payment);
			
			markAsChanged(payment);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}

	}

	@Override
	public void deletePayment(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.createQuery("DELETE FROM Payment pa WHERE pa.id = :id")
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
	public void persistFinancialSource(FinancialSource source) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.persist(source);
			
			markAsChanged(source);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteFinancialSource(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.createQuery("DELETE FROM FinancialSource fs WHERE fs.id :finId")
				.setParameter("finId", id)
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
	public void deletePayment(Payment payment)
	{
		if (payment == null) 
			throw new IllegalArgumentException("Argument 'payment' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();

		try
		{
			this.getSession().getEntityManager().remove(payment);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteFinancialSource(FinancialSource source)
	{
		if (source == null) 
			throw new IllegalArgumentException("Argument 'source' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(source);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

}
