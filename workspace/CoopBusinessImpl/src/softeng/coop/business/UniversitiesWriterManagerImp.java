package softeng.coop.business;

import javax.persistence.EntityManager;

import softeng.coop.business.universities.UniversitiesWriterManager;
import softeng.coop.dataaccess.Department;
import softeng.coop.dataaccess.Division;
import softeng.coop.dataaccess.University;

public class UniversitiesWriterManagerImp extends UniversitiesManagerImpl implements
		UniversitiesWriterManager {

	public UniversitiesWriterManagerImp(Session session) 
	{
		super(session);
	}

	@Override
	public UniversitiesWriterManager getWriterManager() 
	{		
		return this;
	}

	@Override
	public boolean isWriteable() {
		return true;
	}

	@Override
	public void persistUniversity(University university) 
	{
		if (university == null) 
			throw new IllegalArgumentException("Argument 'university' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(university);
			
			this.markAsChanged(university);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteUniversity(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		University university = entityManager.find(University.class, id);
			
		if (university != null)
		{	
				deleteUniversity(university);
		}
	}

	@Override
	public void deleteUniversity(University university) 
	{	
		if (university == null) 
			throw new IllegalArgumentException("Argument 'university' must not be null.");			
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.remove(university);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void persistDepartment(Department department) 
	{
		if (department == null) 
			throw new IllegalArgumentException("Argument 'department' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try 
		{
			this.getSession().getEntityManager().persist(department);
			
			this.markAsChanged(department);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}

	}

	@Override
	public void deleteDepartment(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		Department department = entityManager.find(Department.class, id);
			
		if (department != null) deleteDepartment(department);
	}

	@Override
	public void deleteDepartment(Department department) 
	{
		if (department == null) 
			throw new IllegalArgumentException("Argument 'department' must not be null.");	
		
		EntityManager entityManager = this.getSession().getEntityManager();		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.remove(department);
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}		
	}

	@Override
	public void persistDivision(Division division) 
	{
		if (division == null) 
			throw new IllegalArgumentException("Argument 'division' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try 
		{
			this.getSession().getEntityManager().persist(division);
			
			this.markAsChanged(division);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteDivision(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		Division division = entityManager.find(Division.class, id);
			
		if (division != null) deleteDivision(division);
	}

	@Override
	public void deleteDivision(Division division) 
	{
		if (division == null) 
			throw new IllegalArgumentException("Argument 'division' must not be null.");	
		
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.remove(division);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}

	}

}
