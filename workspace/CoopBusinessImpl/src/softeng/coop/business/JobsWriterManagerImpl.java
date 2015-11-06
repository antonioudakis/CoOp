package softeng.coop.business;

import java.util.*;

import javax.persistence.*;

import softeng.coop.business.jobs.*;
import softeng.coop.dataaccess.*;

public class JobsWriterManagerImpl extends JobsManagerImpl implements JobsWriterManager
{
	public JobsWriterManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public boolean isWriteable()
	{
		return true;
	}

	@Override
	public JobsWriterManager getWriterManager()
	{
		return this;
	}

	@Override
	public Job createJob(JobPosting jobPosting, Group group, Date startDate)
	{
		if (jobPosting == null) 
			throw new IllegalArgumentException("Argument 'jobPosting' must not be null.");
		
		if (group == null) 
			throw new IllegalArgumentException("Argument 'group' must not be null.");
		
		if (startDate == null) 
			throw new IllegalArgumentException("Argument 'startDate' must not be null.");
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			Job job = Constructor.createJob();
			
			job.setGroup(group);
			group.setJob(job);
			
			job.setJobPosting(jobPosting);
			jobPosting.getJobs().add(job);
			job.setState(JobStateType.Assigned);
			
			job.setStartDate(startDate);
			
			job.setSupervisingProfessor(jobPosting.getSupervisingProfessor());
			
			entityManager.persist(job);
			
			// If the job posting has parts,
			// create corresponding job parts 
			// and make an initial calculation of dates.
			if (jobPosting.getJobPostingParts() != null)
			{
				Calendar minStartCalendar = null;
				
				Calendar maxEndCalendar = null;
				
				/* INCREDIBLE BOGUS HIBERNATE BEHAVIOR:
				 * If the collection of job posting parts was fetched eagerly, Hibernate
				 * might reduplicate the collection's items because
				 * Hibernate doesn't filter out the multiple rows produced by SQL joins,
				 * even if CriteriaQuery<JobPosting>.distinct(true) is specified.
				 * For this reason, we keep a set of the already seen job posting parts
				 * to skip duplicates.   
				 */
				Set<JobPostingPart> seenJobPostingParts = new HashSet<JobPostingPart>(jobPosting.getJobPostingParts().size());
				
				for (JobPostingPart jobPostingPart : jobPosting.getJobPostingParts())
				{
					if (seenJobPostingParts.contains(jobPostingPart)) continue;
					
					seenJobPostingParts.add(jobPostingPart);
					
					JobPart jobPart = Constructor.createJobPart();
					
					jobPart.setJob(job);
					job.getJobParts().add(jobPart);
					
					jobPart.setBranch(jobPostingPart.getBranch());
					
					jobPart.setSiteType(jobPostingPart.getSiteType());
					
					jobPart.setPaidDays(jobPostingPart.getPaidDays());
					
					for (JobPostingPartSpecialPayable jobPostingPartSpecialPayable : jobPostingPart.getSpecialPayables())
					{
						JobPartSpecialPayable jobPartSpecialPayable = Constructor.createJobPartSpecialPayable();
						
						jobPartSpecialPayable.setFinancialSource(jobPostingPartSpecialPayable.getFinancialSource());
						jobPartSpecialPayable.setPaidDays(jobPostingPartSpecialPayable.getPaidDays());
						
						jobPart.getSpecialPayables().add(jobPartSpecialPayable);
						jobPartSpecialPayable.setJobPart(jobPart);
					}
					
					jobPart.setExpeditionLocation(jobPostingPart.getExpeditionLocation());
					
					if (jobPostingPart.getExpeditionGeoLocation() != null)
					{
						GeoLocation geoLocation = new GeoLocation();
						
						geoLocation.setLatitude(jobPostingPart.getExpeditionGeoLocation().getLatitude());
						geoLocation.setLongtitude(jobPostingPart.getExpeditionGeoLocation().getLongtitude());
						
						jobPart.setExpeditionGeoLocation(geoLocation);
					}

					jobPart.setManagingCompanyPerson(jobPostingPart.getManagingCompanyPerson());
					
					Calendar startPartCalendar = Calendar.getInstance();
					
					startPartCalendar.setTime(startDate);
					removeHour(startPartCalendar);
					startPartCalendar.add(Calendar.DATE, jobPostingPart.getStartDay());

					jobPart.setStartDate(startPartCalendar.getTime());
					
					if (minStartCalendar != null)
					{
						if (minStartCalendar.compareTo(startPartCalendar) > 0) minStartCalendar = startPartCalendar;
					}
					else
					{
						minStartCalendar = startPartCalendar;
					}
					
					Calendar endPartCalendar = Calendar.getInstance();
					
					endPartCalendar.setTime(jobPart.getStartDate());
					removeHour(endPartCalendar);
					endPartCalendar.add(Calendar.DATE, jobPostingPart.getDurationDays());
					
					jobPart.setEndDate(endPartCalendar.getTime());
					
					if (maxEndCalendar != null)
					{
						if (maxEndCalendar.compareTo(endPartCalendar) < 0) maxEndCalendar = endPartCalendar;
					}
					else
					{
						maxEndCalendar = endPartCalendar;
					}
					
					// Copy jobPostingPart.description multilingual field.
					LiteralsManager.copyLiterals(jobPostingPart.getDescription(), jobPart.getDescription(), entityManager);
					
					markAsChanged(jobPart);
					
					entityManager.persist(jobPart);
					
				}
				
				if (minStartCalendar != null) job.setStartDate(minStartCalendar.getTime());
				if (maxEndCalendar != null) job.setEndDate(maxEndCalendar.getTime());
				
			}
			
			markAsChanged(job);
			
			entityManager.persist(job);
			
			transactionScope.commit();
			
			return job;
		}
		finally
		{
			transactionScope.dispose();
		}
	}
	
	private void removeHour(Calendar calendar)
	{
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	@Override
	public void deleteJob(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM Job j WHERE j.id = :id")
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
	public void changeState(Job job, JobStateType state)
	{
		if (job == null) 
			throw new IllegalArgumentException("Argument 'job' must not be null.");
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.persist(job);
			
			job.setState(state);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
		// Possible extension: Invoke event notification system. 
	}

	@Override
	public void persistJob(Job job) 
	{
		if (job == null) 
			throw new IllegalArgumentException("Argument 'job' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.markAsChanged(job);
			
			this.getSession().getEntityManager().persist(job);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteJob(Job job)
	{
		if (job == null) 
			throw new IllegalArgumentException("Argument 'job' must not be null.");

		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().remove(job);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

}
