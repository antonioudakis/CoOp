package softeng.coop.business;

import java.util.*;

import softeng.coop.business.faculties.*;
import softeng.coop.dataaccess.*;

import javax.persistence.*;
import javax.persistence.criteria.*;

class FacultyUsersManagerImpl extends ManagerBaseImpl implements FacultyUsersManager
{
	private static abstract class SearchAssembler<E extends FacultyUser, C extends FacultyUserSearchCriteria>
		extends QueryHelper.Assembler<E, C>
	{
		protected abstract <Q> Root<E> getRoot(CriteriaQuery<Q> query);
		
		@Override
		public <Q> Root<E> build(C criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<E> facultyUserRoot = getRoot(query);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			if (criteria.getSurname() != null)
			{
				String surnamePattern = QueryHelper.getPrefixPattern(criteria.getSurname());
				
				restrictions.add(
						builder.like(
								facultyUserRoot.get(FacultyUser_.surname),
								builder.literal(surnamePattern)
						)
				);
			}
			
			if (criteria.getName() != null)
			{
				String namePattern = QueryHelper.getPrefixPattern(criteria.getName());
				
				restrictions.add(
						builder.like(
								facultyUserRoot.get(FacultyUser_.name),
								builder.literal(namePattern)
						)
				);
			}
			
			if (criteria.getDivision() != null)
			{
				restrictions.add(
						builder.equal(
								facultyUserRoot.get(FacultyUser_.division),
								builder.literal(criteria.getDivision())
						)
				);
			}
			
			if (criteria.getDepartment() != null)
			{
				restrictions.add(
						builder.equal(
								facultyUserRoot.get(FacultyUser_.department),
								builder.literal(criteria.getDepartment())
						)
				);
			}
			
			if (criteria.getUniversity() != null)
			{
				restrictions.add(
						builder.equal(
								facultyUserRoot.get(FacultyUser_.department).get(Department_.university),
								builder.literal(criteria.getUniversity())
						)
				);
			}
			
			if (eagerFetch)
			{
				facultyUserRoot
				.fetch(FacultyUser_.department)
				.fetch(Department_.university);
			}
			
			query.distinct(true);
			
			query.where(QueryHelper.listToArray(restrictions));
			
			if (eagerFetch)
			{
				query.orderBy(
						builder.asc(facultyUserRoot.get(FacultyUser_.surname)),
						builder.asc(facultyUserRoot.get(FacultyUser_.name)));
			}
			
			return facultyUserRoot;
		}
		
	}
	
	private static class FacultyUserSearchAssembler
		extends SearchAssembler<FacultyUser, FacultyUserSearchCriteria>
	{
		@Override
		protected <Q> Root<FacultyUser> getRoot(CriteriaQuery<Q> query)
		{
			return query.from(FacultyUser.class);
		}
	}
	
	private static FacultyUserSearchAssembler facultyUserSearchAssembler =
		new FacultyUserSearchAssembler();
	
	private static class ProfessorSearchAssembler
		extends SearchAssembler<Professor, ProfessorSearchCriteria>
	{
		@Override
		protected <Q> Root<Professor> getRoot(CriteriaQuery<Q> query)
		{
			return query.from(Professor.class);
		}

		@Override
		public <Q> Root<Professor> build(ProfessorSearchCriteria criteria, CriteriaQuery<Q> query, boolean eagerFetch, CriteriaBuilder builder)
		{
			Root<Professor> root = super.build(criteria, query, eagerFetch, builder);
			
			List<Predicate> restrictions = new ArrayList<Predicate>();
			
			restrictions.add(query.getRestriction());
			
			if (criteria.getSupervisedCoOpYear() != null)
			{
				restrictions.add(
						builder.equal(
								root.get("supervisedCoOps").get("year"),
								criteria.getSupervisedCoOpYear()
						)
				);
			}
			
			query.where(QueryHelper.listToArray(restrictions));
			
			return root;
		}
	}
	
	private static ProfessorSearchAssembler professorSearchAssembler =
		new ProfessorSearchAssembler();

	public FacultyUsersManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public boolean isWriteable()
	{
		return false;
	}

	@Override
	public FacultyUser getFacultyUser(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();

		return entityManager.find(FacultyUser.class, id);
	}

	@Override
	public Professor getProfessor(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();

		return entityManager.find(Professor.class, id);
	}

	@Override
	public SearchResult<FacultyUser> searchFacultyUsers(FacultyUserSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();

		return QueryHelper.builderSearch(criteria, facultyUserSearchAssembler, entityManager);
	}

	@Override
	public SearchResult<Professor> searchProfessors(ProfessorSearchCriteria criteria)
	{
		EntityManager entityManager = this.getSession().getEntityManager();

		return QueryHelper.builderSearch(criteria, professorSearchAssembler, entityManager);
	}

	@Override
	public FacultyUsersWriterManager getWriterManager()
	{
		return null;
	}

	@Override
	protected String getResourceBundleBaseName()
	{
		return "FacultyUsersManager";
	}

}
