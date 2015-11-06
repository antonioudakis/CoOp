package softeng.coop.business;

import java.util.*;

import javax.persistence.criteria.*;

import softeng.coop.business.jobpostings.*;
import softeng.coop.dataaccess.*;

public class JobPostingsManagerImpl extends ManagerBaseImpl implements
		JobPostingsManager {

	JobPostingsManagerImpl(Session session) 
	{
		super(session);
	}

	@Override
	public boolean isWriteable() 
	{
		return false;
	}

	@Override
	public JobPosting getJobPosting(int id) 
	{
		return this.getSession().getEntityManager().find(JobPosting.class, id);
	}

	private static class JobPostingSearchAssembler
		extends QueryHelper.Assembler<JobPosting, JobPostingsSearchCriteria>
	{
		private static <Q> Subquery<Long> getJobsCountQuery(
				CriteriaQuery<Q> query, Root<JobPosting> jobPostingRoot, CriteriaBuilder builder)
		{
			Subquery<Long> jobCountQuery = query.subquery(Long.class);
			
			Root<JobPosting> jobPostingCorrelationRoot = jobCountQuery.correlate(jobPostingRoot);
			
			SetJoin<JobPosting, Job> jobsJoin = jobPostingCorrelationRoot.join(JobPosting_.jobs, JoinType.LEFT);
			
			jobCountQuery.select(builder.count(jobsJoin));
			
			return jobCountQuery;
		}

		@Override
		public <Q> Root<JobPosting> build(JobPostingsSearchCriteria criteria,
				CriteriaQuery<Q> query, boolean eagerFetch,
				CriteriaBuilder builder) 
		{
			Root<JobPosting> jobPostingRoot = query.from(JobPosting.class);
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			Subquery<Long> jobsCountQuery = null;
			
			if (criteria.getCoop() != null)
			{
				predicates.add(builder.equal(jobPostingRoot.get(JobPosting_.coOp)
						, builder.literal(criteria.getCoop())));
			}
			
			if (criteria.getCompany() != null)
			{
				predicates.add(builder.equal(jobPostingRoot.get(JobPosting_.company)
						, builder.literal(criteria.getCompany())));
			}
			
			if (criteria.getCategory() != null)
			{
				String pathPrefix = criteria.getCategory().getPath() + "%";

				predicates.add(
						builder.like(
								jobPostingRoot.get(JobPosting_.company).get(Company_.category).get(Category_.path), 
								pathPrefix));
			}
			
			if (criteria.getLocation() != null)
			{
				Join<JobPosting, JobPostingPart> partJoin = 
					jobPostingRoot.join(JobPosting_.jobPostingParts, JoinType.LEFT);
				
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
			
			if (criteria.isEmpty() != null)
			{
				if (jobsCountQuery == null) 
					jobsCountQuery = getJobsCountQuery(query, jobPostingRoot, builder);
				
				Long zero = new Long(0);

				if (criteria.isEmpty())
				{
					predicates.add(
							builder.equal(
									jobsCountQuery, 
									zero));
				}
				else
				{
					predicates.add(
							builder.gt(
									jobsCountQuery, 
									zero));
				}
			}
			
			if (criteria.isFull() != null)
			{
				if (jobsCountQuery == null) 
					jobsCountQuery = getJobsCountQuery(query, jobPostingRoot, builder);

				if (criteria.isFull())
				{
					predicates.add(
							builder.ge(
									jobsCountQuery, 
									jobPostingRoot.get(JobPosting_.seatsNumber)));
				}
				else
				{
					predicates.add(
							builder.lt(
									jobsCountQuery, 
									jobPostingRoot.get(JobPosting_.seatsNumber)));
				}
			}
			
			query.distinct(true);
			
			query.where(QueryHelper.listToArray(predicates));
			
			if (eagerFetch)
			{
				jobPostingRoot.fetch(JobPosting_.jobs, JoinType.LEFT);
				jobPostingRoot.fetch(JobPosting_.jobPostingParts, JoinType.LEFT);
			}
			
			return jobPostingRoot;
		}
	
	}
	
	private static JobPostingSearchAssembler jobPostingSearchAssember =
		new JobPostingSearchAssembler();
	
	@Override
	public SearchResult<JobPosting> searchJobPostings(
			JobPostingsSearchCriteria criteria) 
	{
		return QueryHelper.builderSearch(criteria
				, jobPostingSearchAssember
				, this.getSession().getEntityManager());
	}

	
	@Override
	public JobPostingsWriterManager getWriterManager() 
	{
		return null;
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "JobPostingsManager";
	}

}
