package softeng.coop.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import javax.persistence.criteria.*;

import softeng.coop.business.jobs.*;
import softeng.coop.dataaccess.*;

public class JobsManagerImpl extends ManagerBaseImpl implements JobsManager
{
	private static class JobSearchAssembler
	extends QueryHelper.Assembler<Job, JobsSearchCriteria>
	{

		@Override
		public <Q> Root<Job> build(JobsSearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			Root<Job> jobRoot = query.from(Job.class);
			
			Join<Job, JobPosting> jobPostingJoin = jobRoot.join(Job_.jobPosting);
			
			if (criteria.getCoop() != null)
			{
				predicates.add(
						builder.equal(
								jobPostingJoin.get(JobPosting_.coOp), 
								builder.literal(criteria.getCoop())
						)
				);
			}
			
			if (criteria.getCategory() != null)
			{
				String pathExpression = criteria.getCategory().getPath() + "%";
				
				predicates.add(
						builder.like(
								jobPostingJoin.get(JobPosting_.company).get(Company_.category).get(Category_.path), 
								pathExpression
						)
				);
			}
			
			if (criteria.getCompany() != null)
			{
				predicates.add(
						builder.equal(
								jobPostingJoin.get(JobPosting_.company), 
								builder.literal(criteria.getCompany())
						)
				);
			}
			
			if (criteria.getLocation() != null)
			{
				Join<JobPosting, JobPostingPart> partJoin = 
					jobPostingJoin.join(JobPosting_.jobPostingParts, JoinType.LEFT);
				
				String pathPrefix = criteria.getLocation().getPath() + "%";
				
				predicates.add(
						builder.or(
							builder.like(
								partJoin
									.join(JobPostingPart_.branch, JoinType.LEFT)
									.get(Branch_.address)
									.get(EmbeddableAddress_.location)
									.get(Location_.path), 
								pathPrefix),
							builder.like(
								partJoin
									.join(JobPostingPart_.expeditionLocation, JoinType.LEFT)
									.get(Location_.path), 
								pathPrefix)
						)
				);
			}
			
			query.distinct(true);
			
			query.where(QueryHelper.listToArray(predicates));
			
			if (eagerFetch)
			{
				jobRoot.fetch(Job_.jobParts, JoinType.LEFT);
				jobRoot.fetch(Job_.jobPosting, JoinType.LEFT);
				jobRoot.fetch(Job_.group, JoinType.LEFT).fetch(Group_.registrations, JoinType.LEFT).fetch(Registration_.student, JoinType.LEFT);
			}
			
			return jobRoot;
		}
		
	}
	
	private static JobSearchAssembler jobSearchAssembler = new JobSearchAssembler();

	public JobsManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public boolean isWriteable()
	{
		return false;
	}

	@Override
	public Job getJob(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return entityManager.find(Job.class, id);
	}

	@Override
	public JobsWriterManager getWriterManager()
	{
		return null;
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "JobsManager";
	}

	@Override
	public SearchResult<Job> searchJobs(JobsSearchCriteria criteria)
	{
		return QueryHelper.builderSearch(criteria, jobSearchAssembler, getSession().getEntityManager());
	}

}
