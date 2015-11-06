package softeng.coop.business;

import java.util.ArrayList;

import javax.persistence.*;

import softeng.coop.business.jobpostings.JobPostingsWriterManager;
import softeng.coop.dataaccess.*;

public class JobPostingsWriterManagerImpl extends JobPostingsManagerImpl
		implements JobPostingsWriterManager 
{

	@Override
	public boolean isWriteable() 
	{
		return true;
	}

	@Override
	public JobPostingsWriterManager getWriterManager() 
	{
		return this;
	}

	public JobPostingsWriterManagerImpl(Session session) 
	{
		super(session);
	}

	@Override
	public void persistJobPosting(JobPosting jobPosting) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope scope = this.beginTransaction();
		
		try
		{
			entityManager.persist(jobPosting);
			
			markAsChanged(jobPosting);
			
			scope.commit();
		}
		finally
		{
			scope.dispose();
		}

	}

	@Override
	public void deleteJobPosting(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.createQuery("DELETE FROM JobPosting jp WHERE jp.id = :jid")
				.setParameter("jid", id)
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
	public void persistJobPostingPart(JobPostingPart jobPostingPart) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.persist(jobPostingPart);
			
			markAsChanged(jobPostingPart);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}	
	}

	@Override
	public void deleteJobPostingPart(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.createQuery("DELETE FROM JobPostingPart jpp WHERE jpp.id= :jpid")
				.setParameter("jpid", id)
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
	public void deleteJobPosting(JobPosting jobPosting)
	{
		if (jobPosting == null) 
			throw new IllegalArgumentException("Argument 'jobPosting' must not be null.");

		EntityManager entityManager = this.getSession().getEntityManager();

		TransactionScope firstAttemptScope = this.beginTransaction();

		try
		{
			try
			{
				entityManager.remove(jobPosting);
				entityManager.flush();
			}
			finally
			{
				firstAttemptScope.dispose();
			}
		}
		catch (RuntimeException ex)
		{
			if (!entityManager.getClass().getName().contains(".hibernate."))
				throw ex;
			// ...else swallow.
		}

		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			try
			{
			
				/* Horrible dirty patch for hibernate bug:
				 * Hibernate does nothing after above call, so execute native commands to do the work.
				 * Standard ANSI 92 SQL please, to keep the project portable...
				 */
				
				if (entityManager.getClass().getName().contains(".hibernate."))
				{
					ArrayList<Integer> multilingualIds = new ArrayList<Integer>(2 + jobPosting.getJobPostingParts().size());
					
					multilingualIds.add(jobPosting.getName().getId());
					multilingualIds.add(jobPosting.getDescription().getId());
					
					// Gather all involved multilingual IDs.
					for (JobPostingPart jobPostingPart : jobPosting.getJobPostingParts())
					{
						multilingualIds.add(jobPostingPart.getDescription().getId());
					}
					
					// First, cascade delete all special payables in job posting parts. 
					Query specialPayablesDeletion = entityManager.createNativeQuery(
							String.format(
								"DELETE FROM \"JobPostingPartSpecialPayable\" sp \n" +
								"WHERE sp.jobpostingpart_id IN \n" +
								"( \n" +
								"\tSELECT jpp.id FROM \"JobPostingPart\" jpp \n" +
								"\tWHERE jpp.jobposting_id = %d \n" +
								"); \n",
								jobPosting.getId())
							);
					
					specialPayablesDeletion.executeUpdate();
					
					Query jobPostingPartsDescriptionsLiteralsDeletion = entityManager.createNativeQuery(
							String.format(
									"DELETE FROM \"Literal\" lit \n" +
									"WHERE lit.multilingual_id IN \n" +
									"( \n" +
									"\tSELECT jpp.description_id FROM \"JobPostingPart\" jpp \n" +
									"\tWHERE jpp.jobposting_id = %d \n" +
									"); \n",
									jobPosting.getId())
								);
					
					jobPostingPartsDescriptionsLiteralsDeletion.executeUpdate();
					
					Query jobPostingPartsDeletion = entityManager.createNativeQuery(
							String.format(
									"DELETE FROM \"JobPostingPart\" jpp \n" +
									"WHERE jpp.jobposting_id = %d;",
									jobPosting.getId())
								);
					
					jobPostingPartsDeletion.executeUpdate();
					
					Query jobPostingDescriptionLiteralsDeletion = entityManager.createNativeQuery(
							String.format(
									"DELETE FROM \"Literal\" lit \n" +
									"WHERE lit.multilingual_id IN \n" +
									"( \n" +
									"\tSELECT jp.description_id FROM \"JobPosting\" jp \n" +
									"\tWHERE jp.id = %d \n" +
									"); \n",
									jobPosting.getId())
								);
					
					jobPostingDescriptionLiteralsDeletion.executeUpdate();
					
					Query jobPostingNameLiteralsDeletion = entityManager.createNativeQuery(
							String.format(
									"DELETE FROM \"Literal\" lit \n" +
									"WHERE lit.multilingual_id IN \n" +
									"( \n" +
									"\tSELECT jp.name_id FROM \"JobPosting\" jp \n" +
									"\tWHERE jp.id = %d \n" +
									"); \n",
									jobPosting.getId())
								);
					
					jobPostingNameLiteralsDeletion.executeUpdate();
					
					Query jobPostingPreferencesDeletion = entityManager.createNativeQuery(
							String.format(
									"DELETE FROM \"Registration_JobPosting\" rjp \n" +
									"WHERE rjp.preferredjobpostings_id = %d; \n;",
									jobPosting.getId()
							)
					);
					
					jobPostingPreferencesDeletion.executeUpdate();
					
					Query jobPostingDeletion = entityManager.createNativeQuery(
							String.format(
									"DELETE FROM \"JobPosting\" jp \n" +
									"WHERE jp.id = %d;",
									jobPosting.getId())
								);
					
					jobPostingDeletion.executeUpdate();
					
					for (Integer multilingualId : multilingualIds)
					{
						Query multilingualDeletion = entityManager.createNativeQuery(
								String.format(
										"DELETE FROM \"Multilingual\" mul \n" +
										"WHERE mul.id = %d;",
										multilingualId)
									);
						
						multilingualDeletion.executeUpdate();
					}
					
				}
				
				transactionScope.commit();
			}
			catch (PersistenceException ex)
			{
				throw TransactionHelper.translateException(ex);
			}
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteJobPostingPart(JobPostingPart jobPostingPart)
	{
		if (jobPostingPart == null) 
			throw new IllegalArgumentException("Argument 'jobPostingPart' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(jobPostingPart);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

}
