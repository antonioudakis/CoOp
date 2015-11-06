package softeng.coop.business;

import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;

import javax.persistence.*;
import javax.persistence.criteria.*;

import java.util.*;

class CoOpsManagerImpl extends ManagerBaseImpl implements CoOpsManager
{
	private static class CoOpsSearchAssembler
		extends QueryHelper.Assembler<CoOp, CoOpSearchCriteria>
	{
		@Override
		public <Q> Root<CoOp> build(
				CoOpSearchCriteria criteria, 
				CriteriaQuery<Q> query, 
				boolean eagerFetch, 
				CriteriaBuilder builder)
		{
			Root<CoOp> coopRoot = query.from(CoOp.class);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (eagerFetch) 
			{
				coopRoot.fetch(CoOp_.name).fetch(Multilingual_.literals, JoinType.LEFT);
				Fetch<CoOp, Lesson> lessonFetch = coopRoot.fetch(CoOp_.lesson);
				lessonFetch.fetch(Lesson_.name, JoinType.LEFT).fetch(Multilingual_.literals, JoinType.LEFT);
				lessonFetch.fetch(Lesson_.department).fetch(Department_.name).fetch(Multilingual_.literals, JoinType.LEFT);
			}
			
			if (criteria.isActiveOnly())
			{
				restrictions.add(builder.isTrue(coopRoot.get(CoOp_.active)));
			}
			
			if (criteria.isInRegistrationOnly())
			{
				restrictions.add(builder.isTrue(coopRoot.get(CoOp_.inRegistration)));
			}
			
			if (criteria.isSetupOnly())
			{
				restrictions.add(builder.isTrue(coopRoot.get(CoOp_.setup)));
			}
			
			if (criteria.getLesson() != null)
			{
				restrictions.add(
						builder.equal(
								coopRoot.get(CoOp_.lesson), 
								builder.literal(criteria.getLesson())));
			}
			
			if (criteria.getYear() > 0)
			{
				restrictions.add(
						builder.equal(
								coopRoot.get(CoOp_.academicYear), 
								builder.literal(criteria.getYear())
						)
				);
			}
			
			if (criteria.getSemester() > 0)
			{
				restrictions.add(
						builder.equal(
								coopRoot.get(CoOp_.semester), 
								builder.literal(criteria.getSemester())
						)
				);
			}
			
			if (criteria.getDepartment() != null)
			{
				restrictions.add(
						builder.equal(
								coopRoot.get(CoOp_.lesson).get(Lesson_.department), 
								builder.literal(criteria.getDepartment())
						)
				);
			}
			
			if (criteria.getStartingAfter() != null)
			{
				restrictions.add(
						builder.greaterThanOrEqualTo(
								coopRoot.get(CoOp_.startDate), 
								builder.literal(criteria.getStartingAfter())
						)
				);
			}
			
			if (criteria.getEndingBefore() != null)
			{
				restrictions.add(
						builder.lessThanOrEqualTo(
								coopRoot.get(CoOp_.endDate), 
								builder.literal(criteria.getEndingBefore())
						)
				);
			}
			
			if (criteria.getRegisteredByStudent() != null)
			{
				restrictions.add(
						builder.equal(
								coopRoot.join(CoOp_.registrations).get(Registration_.student),
								builder.literal(criteria.getRegisteredByStudent())
						)
				);
			}
			
			if (criteria.getUniversity() != null)
			{
				restrictions.add(
						builder.equal(
								coopRoot.get(CoOp_.lesson).get(Lesson_.department).get(Department_.university), 
								criteria.getUniversity()
						)
				);
			}
			
			query.orderBy(
					builder.asc(coopRoot.get(CoOp_.academicYear)),
					builder.asc(coopRoot.get(CoOp_.startDate))
			);

			query.where(QueryHelper.listToArray(restrictions)).distinct(true);
			
			return coopRoot;
		}
	}
	
	private static CoOpsSearchAssembler coOpsSearchAssembler =
		new CoOpsSearchAssembler();
	
	public CoOpsManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public boolean isWriteable()
	{
		return false;
	}

	@Override
	public CoOp getCoOp(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		return entityManager.find(CoOp.class, id);
	}

	@Override
	public SearchResult<CoOp> searchCoOps(CoOpSearchCriteria criteria)
	{
		return QueryHelper.builderSearch(
				criteria, 
				coOpsSearchAssembler, 
				this.getSession().getEntityManager());
	}

	@Override
	public CoOpsWriterManager getWriterManager()
	{
		return null;
	}

	@Override
	public Lesson getLesson(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		return entityManager.find(Lesson.class, id);
	}

	private static class LessonsSearchAssembler
		extends QueryHelper.Assembler<Lesson, LessonSearchCriteria>
	{

		@Override
		public <Q> Root<Lesson> build(LessonSearchCriteria criteria,
				CriteriaQuery<Q> query, boolean eagerFetch,
				CriteriaBuilder builder) 
		{


			Root<Lesson> lessonRoot = query.from(Lesson.class);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (eagerFetch) lessonRoot.fetch(Lesson_.name).fetch(Multilingual_.literals);
			
			if (criteria.getDepartment() != null)
			{
				restrictions.add(
						builder.equal(
								lessonRoot.get(Lesson_.department), 
								builder.literal(criteria.getDepartment())
						)
				);
				
			}
			
			query.where(QueryHelper.listToArray(restrictions)).distinct(true);
			
			return lessonRoot;
		
		}
		
	}
	
	private static LessonsSearchAssembler lessonsSearchAssember = 
		new LessonsSearchAssembler();
	
	@Override
	public SearchResult<Lesson> searchLessons(LessonSearchCriteria criteria) 
	{
		return QueryHelper.builderSearch(criteria, 
				lessonsSearchAssember, 
				this.getSession().getEntityManager());
		
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "CoOpsManager";
	}

}
