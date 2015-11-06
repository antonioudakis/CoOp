package softeng.coop.business;

import javax.persistence.*;

import softeng.coop.business.companies.*;
import softeng.coop.dataaccess.*;

class CompaniesWriterManagerImpl extends CompaniesManagerImpl implements CompaniesWriterManager
{
	public CompaniesWriterManagerImpl(Session session)
	{
		super(session);
	}

	@Override
	public boolean isWriteable()
	{
		return true;
	}

	@Override
	public CompaniesWriterManager getWriterManager()
	{
		return this;
	}

	@Override
	public void deleteCompany(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM Company comp WHERE comp.id = :id")
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
	public void persistCompany(Company company)
	{
		if (company == null) 
			throw new IllegalArgumentException("Argument 'company' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(company);
			
			this.markAsChanged(company);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}

	}

	@Override
	public void cascadeDeleteCategory(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			Category category = entityManager.find(Category.class, id);
			
			if (category != null) deleteCategory(category);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public boolean moveCategory(Category category, Category newParent)
	{
		if (category == null) 
			throw new IllegalArgumentException("Argument 'category' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			if (!ensureNewParentIsNotChild(category, newParent)) return false;
			
			Category oldParent = category.getParentCategory();
			
			if (oldParent != null)
			{
				oldParent.getChildCategories().remove(category);
			}
	
			if (newParent != null)
			{
				newParent.getChildCategories().add(category);
			}
			
			category.setParentCategory(newParent);
			
			adjustDescendantsPaths(category);
			
			this.markAsChanged(category);

			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
		return true;
	}

	@Override
	public void persistCategory(Category category)
	{
		if (category == null) 
			throw new IllegalArgumentException("Argument 'category' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(category);
			
			this.markAsChanged(category);
			
			category.setPath(computePath(category));
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void persistBranch(Branch branch)
	{
		if (branch == null) 
			throw new IllegalArgumentException("Argument 'branch' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(branch);
			
			this.markAsChanged(branch);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void persistCompanyPerson(CompanyPerson person)
	{
		if (person == null) 
			throw new IllegalArgumentException("Argument 'person' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			this.getSession().getEntityManager().persist(person);
			
			this.markAsChanged(person);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteBranch(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM Branch br WHERE br.id = :id")
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
	public void deleteCompanyPerson(int id)
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager
				.createQuery("DELETE FROM CompanyPerson cp WHERE cp.id = :id")
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

	private boolean ensureNewParentIsNotChild(Category category, Category newParent)
	{
		if (newParent == null) return true;
		
		return !newParent.getPath().startsWith(category.getPath());
	}

	private String computePath(Category category)
	{
		StringBuilder stringBuilder = new StringBuilder(90);
		
		for (Category ancestor = category; ancestor != null; ancestor = ancestor.getParentCategory())
		{
			stringBuilder.insert(0, getPathFragment(ancestor));
		}
		
		return stringBuilder.toString();
	}
	
	private String getPathFragment(Category category)
	{
		return String.format("%08x#", category.getId());
	}
	
	private void deleteCategory(Category category)
	{
		for (Category child : category.getChildCategories())
		{
			deleteCategory(child);
		}
		
		this.getSession().getEntityManager().remove(category);
	}

	private void adjustDescendantsPaths(Category category)
	{
		String parentPath = null;
		
		Category parentCategory = category.getParentCategory();
		
		if (parentCategory != null)
			parentPath = parentCategory.getPath();
		else
			parentPath = "";
		
		category.setPath(parentPath + getPathFragment(category));
		
		for (Category childCategory : category.getChildCategories())
		{
			this.adjustDescendantsPaths(childCategory);
		}
	}

	@Override
	public void persistActivitySector(ActivitySector activitySector) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			entityManager.persist(activitySector);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void cascadeDeleteActivitySector(int id) 
	{
		EntityManager entityManager = this.getSession().getEntityManager();
		
		TransactionScope transactionScope = this.beginTransaction();
		try
		{
			ActivitySector sector = entityManager.find(ActivitySector.class, id);
			
			if (sector != null) deleteActivitySector(sector);
			
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
	public void deleteCompany(Company company)
	{
		if (company == null) 
			throw new IllegalArgumentException("Argument 'company' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			getSession().getEntityManager().remove(company);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void cascadeDeleteCategory(Category category)
	{
		if (category == null) 
			throw new IllegalArgumentException("Argument 'category' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			deleteCategory(category);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void deleteBranch(Branch branch)
	{
		if (branch == null) 
			throw new IllegalArgumentException("Argument 'branch' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			getSession().getEntityManager().remove(branch);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
		
	}

	@Override
	public void deleteCompanyPerson(CompanyPerson person)
	{
		if (person == null) 
			throw new IllegalArgumentException("Argument 'person' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			getSession().getEntityManager().remove(person);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}

	@Override
	public void cascadeDeleteActivitySector(ActivitySector activitySector)
	{
		if (activitySector == null) 
			throw new IllegalArgumentException("Argument 'activitySector' must not be null.");
		
		TransactionScope transactionScope = this.beginTransaction();
		
		try
		{
			deleteActivitySector(activitySector);
			
			transactionScope.commit();
		}
		finally
		{
			transactionScope.dispose();
		}
	}
	
	private void deleteActivitySector(ActivitySector sector)
	{
		for (ActivitySector subsector : sector.getChildActivitySectors())
		{
			deleteActivitySector(subsector);
		}
		
		EntityManager entityManager = getSession().getEntityManager();
		
		entityManager.remove(sector);
	}

}
