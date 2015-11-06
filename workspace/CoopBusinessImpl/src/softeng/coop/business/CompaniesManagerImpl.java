package softeng.coop.business;

import java.util.*;

import javax.persistence.*;
import javax.persistence.criteria.*;

import softeng.coop.business.companies.*;
import softeng.coop.dataaccess.*;

class CompaniesManagerImpl extends ManagerBaseImpl implements CompaniesManager
{
	private static class CompaniesSearchAssembler
		extends QueryHelper.Assembler<Company, CompanySearchCriteria>
	{
		@Override
		public <Q> Root<Company> build(CompanySearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<Company> companyRoot = query.from(Company.class);

			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (criteria.getLocation() != null)
			{
				String pathPattern = criteria.getLocation().getPath() + "%";

				restrictions.add(
						builder.like(
								companyRoot
									.get(Company_.centralBranch)
									.get(Branch_.address)
									.get(EmbeddableAddress_.location)
									.get(Location_.path), 
								builder.literal(pathPattern)
						)
				);
			}
			
			if (criteria.getCategory() != null)
			{
				String pathPattern = criteria.getCategory().getPath() + "%";

				restrictions.add(
						builder.like(
								companyRoot
									.get(Company_.category)
									.get(Category_.path), 
								builder.literal(pathPattern)
						)
				);
			}

			if (criteria.getCompanyName() != null)
			{
				String namePattern = QueryHelper.getPrefixPattern(criteria.getCompanyName());
				
				Join<Multilingual, Literal> nameJoin = 
					QueryHelper.joinLiterals(companyRoot, Company_.name);

				restrictions.add(
						builder.like(
								nameJoin.get(Literal_.text),
								builder.literal(namePattern)
						)
				);
			}
			
			if (eagerFetch)
			{
				QueryHelper.fetchLiterals(companyRoot, Company_.name);
			}
			
			query.where(QueryHelper.listToArray(restrictions));
			
			query.distinct(true);
			
			return companyRoot;
		}
		
	}
	
	private static CompaniesSearchAssembler companiesSearchAssembler =
		new CompaniesSearchAssembler();
	
	private static class BranchesSearchAssembler
		extends QueryHelper.Assembler<Branch, CompanySearchCriteria>
	{
		@Override
		public <Q> Root<Branch> build(CompanySearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<Branch> branchRoot = query.from(Branch.class);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (criteria.getLocation() != null)
			{
				String pathPattern = criteria.getLocation().getPath() + "%";

				restrictions.add(
						builder.like(
								branchRoot
									.get(Branch_.address)
									.get(EmbeddableAddress_.location)
									.get(Location_.path), 
								builder.literal(pathPattern)
						)
				);
			}
			
			if (criteria.getCategory() != null)
			{
				String pathPattern = criteria.getCategory().getPath() + "%";

				restrictions.add(
						builder.like(
								branchRoot
									.get(Branch_.company)
									.get(Company_.category)
									.get(Category_.path), 
								builder.literal(pathPattern)
						)
				);
			}
			
			if (criteria.getCompanyName() != null)
			{
				String namePattern = QueryHelper.getPrefixPattern(criteria.getCompanyName());
				
				Join<Multilingual, Literal> nameJoin = 
					QueryHelper.joinLiterals(branchRoot.join(Branch_.company), Company_.name);

				restrictions.add(
						builder.like(
								nameJoin.get(Literal_.text),
								builder.literal(namePattern)
						)
				);
			}
			
			if (eagerFetch)
			{
				QueryHelper.fetchLiterals(branchRoot, Branch_.name);
				
				branchRoot.fetch(Branch_.address).fetch(EmbeddableAddress_.location);
			}
			
			query.where(QueryHelper.listToArray(restrictions));
			
			query.distinct(true);
			
			return branchRoot;
		}
	}
	
	private static BranchesSearchAssembler branchesSearchAssembler = 
		new BranchesSearchAssembler();

	public CompaniesManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public boolean isWriteable()
	{
		return false;
	}

	@Override
	public List<Category> getRootCategories()
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TypedQuery<Category> query = 
			entityManager.createQuery(
					"SELECT cat from softeng.coop.dataaccess.Category cat WHERE cat.parentCategory IS NULL", 
					Category.class);
		
		return query.getResultList();
	}

	@Override
	public Category getCategory(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return entityManager.find(Category.class, id);
	}

	@Override
	public Company getCompany(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return entityManager.find(Company.class, id);
	}

	@Override
	public Company getCompany(String taxCode)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TypedQuery<Company> query = 
			entityManager.createQuery(
					"SELECT comp FROM Company comp WHERE comp.taxCode = :taxCode", 
					Company.class)
					.setParameter("taxCode", taxCode);
		
		return QueryHelper.getFirstOrDefault(query);
	}

	@Override
	public Branch getBranch(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return entityManager.find(Branch.class, id);
	}

	@Override
	public CompanyPerson getCompanyPerson(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();

		return entityManager.find(CompanyPerson.class, id);
	}

	@Override
	public SearchResult<Company> searchCompanies(CompanySearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();

		return QueryHelper.builderSearch(
				criteria, 
				companiesSearchAssembler, 
				entityManager);
	}

	@Override
	public SearchResult<Branch> searchBranches(CompanySearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		return QueryHelper.builderSearch(
				criteria, 
				branchesSearchAssembler, 
				entityManager);
	}

	@Override
	public CompaniesWriterManager getWriterManager()
	{
		return null;
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "CompaniesManager";
	}

	@Override
	public List<ActivitySector> getRootActivitySectors()
	{
		EntityManager entityManager = getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ActivitySector> query = builder.createQuery(ActivitySector.class);
		
		Root<ActivitySector> activitySectorRoot = query.from(ActivitySector.class);
		
		query.where(builder.isNull(activitySectorRoot.get(ActivitySector_.parentActivitySector)));
		
		query.orderBy(builder.asc(activitySectorRoot.get(ActivitySector_.code)));
		
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public ActivitySector getActivitySector(int id)
	{
		return getSession().getEntityManager().find(ActivitySector.class, id);
	}

	@Override
	public ActivitySector getActivitySector(String code)
	{
		if (code == null) 
			throw new IllegalArgumentException("Argument 'code' must not be null.");
		
		EntityManager entityManager = getSession().getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ActivitySector> query = builder.createQuery(ActivitySector.class);
		
		Root<ActivitySector> activitySectorRoot = query.from(ActivitySector.class);
		
		query.where(builder.equal(activitySectorRoot.get(ActivitySector_.code), code));
		
		return QueryHelper.getFirstOrDefault(entityManager.createQuery(query));
	}

}
