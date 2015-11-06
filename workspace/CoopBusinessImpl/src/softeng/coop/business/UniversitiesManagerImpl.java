package softeng.coop.business;

import javax.persistence.*;
import javax.persistence.criteria.*;

import softeng.coop.business.universities.*;
import softeng.coop.dataaccess.*;

import java.util.*;

public class UniversitiesManagerImpl extends ManagerBaseImpl implements
		UniversitiesManager 
{
	private static class UniversitiesSearchAssembler 
		extends QueryHelper.Assembler<University, UniversitiesSearchCriteria>
	{
		@Override
		public <Q> Root<University> build(
				UniversitiesSearchCriteria criteria, 
				CriteriaQuery<Q> query, 
				boolean eagerFetch, 
				CriteriaBuilder builder)
		{
			Root<University> universityRoot = query.from(University.class);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (criteria.getLocation() != null)
			{
				String pathPattern = criteria.getLocation().getPath() + "%";
				
				restrictions.add(
						builder.like(
								universityRoot
//									.<String>get("address")
//									.<String>get("location")
//									.<String>get("path"),
										.get(University_.address)
										.get(EmbeddableAddress_.location)
										.get(Location_.path),
								builder.literal(pathPattern)
						)
				);
			}
			
			if (criteria.getUniversitiesName() != null)
			{
				String namePattern = QueryHelper.getPrefixPattern(criteria.getUniversitiesName());
				
				Join<Multilingual, Literal> nameJoin = 
					// universityRoot.join(University_.name).join(Multilingual_.literals);
					QueryHelper.joinLiterals(universityRoot, University_.name);
				
				restrictions.add(
						builder.like(
								nameJoin.get(Literal_.text),
								builder.literal(namePattern)
						)
				);
			}
			
			query.where(builder.and(QueryHelper.listToArray(restrictions)));
			
			if (eagerFetch) 
				QueryHelper.fetchLiterals(universityRoot, University_.name);
			
			query.distinct(true);
			
			return universityRoot;
		}
	}
	
	private static UniversitiesSearchAssembler universitiesSearchAssembler =
		new UniversitiesSearchAssembler();
	
	private static class DepartmentsSearchAssembler
		extends QueryHelper.Assembler<Department, DepartmentsSearchCriteria>
	{
		@Override
		public <Q> Root<Department> build(DepartmentsSearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<Department> departmentRoot = query.from(Department.class);
			
			Join<Department, University> universityJoin =
				departmentRoot.join(Department_.university);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (criteria.getLocation() != null)
			{
				String pathPattern = criteria.getLocation().getPath() + "%";
				
				restrictions.add(
						builder.like(
								universityJoin
										.get(University_.address)
										.get(EmbeddableAddress_.location)
										.get(Location_.path),
								builder.literal(pathPattern))
						);
			}
			
			if (criteria.getUniversitiesName() != null)
			{
				Join<Multilingual, Literal> universityNameJoin = 
					QueryHelper.joinLiterals(universityJoin, University_.name);
				
				String universityNamePattern = QueryHelper.getPrefixPattern(criteria.getUniversitiesName());
				
				restrictions.add(
						builder.like(
								universityNameJoin.get(Literal_.text),
								builder.literal(universityNamePattern)
						)
				);

			}
			
			if (criteria.getUniversity() != null)
			{
				restrictions.add(
						builder.equal(
								departmentRoot.get(Department_.university), 
								builder.literal(criteria.getUniversity())));
			}
			
			if (criteria.getDepartmentsName() != null)
			{
				Join<Multilingual, Literal> departmentNameJoin = 
					QueryHelper.joinLiterals(departmentRoot, Department_.name);
				
				String departmentNamePattern = QueryHelper.getPrefixPattern(criteria.getDepartmentsName());
				
				restrictions.add(
						builder.like(
								departmentNameJoin.get(Literal_.text), 
								builder.literal(departmentNamePattern))
						);
			}
			
			query.where(builder.and(QueryHelper.listToArray(restrictions)));
			
			query.distinct(true);
			
			if (eagerFetch)
			{
				QueryHelper.fetchLiterals(departmentRoot, Department_.name);
			}
			
			return departmentRoot;
		}
		
	}
	
	private static DepartmentsSearchAssembler departmentsSearchAssembler = 
		new DepartmentsSearchAssembler();
	
	public UniversitiesManagerImpl(Session session) 
	{
		super(session);
	}

	@Override
	public boolean isWriteable() 
	{
		return false;
	}

	@Override
	public University getUniversity(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		University university = entityManager.find(University.class, id);
		
		return university;
	}

	@Override
	public Department getDepartment(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		Department department = entityManager.find(Department.class, id);
		
		return department;
	}

	@Override
	public Division getDivision(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		Division division = entityManager.find(Division.class, id);
		
		return division;
	}
	
	private SearchResult<University> programmaticSearchUniversities(
			UniversitiesSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return QueryHelper.builderSearch(
				criteria, 
				universitiesSearchAssembler, 
				entityManager);
	}
	
	@SuppressWarnings("unused")
	private SearchResult<University> jpqlSearchUniversities(
			UniversitiesSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		String filterExpression = "";
		
		if (criteria.getUniversitiesName() != null)
		{
			filterExpression = QueryHelper.appendFilter(filterExpression, 
					"lit.text LIKE :universityName");
		}
			
		if (criteria.getLocation() != null)
		{
			filterExpression = 
				QueryHelper.appendFilter(
						filterExpression, 
						"uni.location.name LIKE :locationName");
		}
		 
		TypedQuery<University> universitiesQuery =
			entityManager.createQuery(
					"SELECT DISTINCT uni FROM University uni" 
						+ QueryHelper.joinLiteralsForEntities("uni", "name", "lit") 
						+ filterExpression 
					, University.class);
			
		TypedQuery<Long> countQuery =
			entityManager.createQuery(
					"SELECT COUNT(DISTINCT uni) FROM University uni "
						+ QueryHelper.joinLiteralsForCount("uni", "name", "lit")
						+ filterExpression
					, Long.class);
		
		if (criteria.getLocation() != null)
		{
			universitiesQuery.setParameter("locationName", criteria.getLocation().getName().replace("%", "") + "%");
			countQuery.setParameter("locationName", criteria.getLocation().getName().replace("%", "") + "%");
		}
		
		if (criteria.getUniversitiesName() != null)
		{
			universitiesQuery.setParameter("universityName", criteria.getUniversitiesName().replace("%", "") + "%");
			countQuery.setParameter("universityName", criteria.getUniversitiesName().replace("%", "") + "%");
		}
		
		return QueryHelper.createSearchResult(universitiesQuery, countQuery, criteria);
	}
	
	@Override
	public SearchResult<University> searchUniversities(
			UniversitiesSearchCriteria criteria) 
	{
		return programmaticSearchUniversities(criteria);
	}
	
	private SearchResult<Department> programmaticSearchDepartments(
			DepartmentsSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return QueryHelper.builderSearch(
				criteria, 
				departmentsSearchAssembler, 
				entityManager);
	}

	@Override
	public SearchResult<Department> searchDepartments(
			DepartmentsSearchCriteria criteria)
	{
		return programmaticSearchDepartments(criteria);
	}

	@Override
	public UniversitiesWriterManager getWriterManager() {
		return null;
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "UniversitiesManager";
	}

}
