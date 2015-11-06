package softeng.coop.ui.data;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;

import softeng.ui.vaadin.data.*;

public class CategoryHierarchyModel
implements HierarchyModel<Category>
{
	private CompaniesWriterManager manager;
	private ICoopContext context;
	
	public CategoryHierarchyModel(ICoopContext context)
	{
		if (context == null) 
			throw new IllegalArgumentException("Argument 'context' must not be null.");
		
		this.context = context;
		
		Session session = context.getSession();
		
		if (session.getCompaniesManager() != null && 
				session.getCompaniesManager().isWriteable())
		{
			manager = session.getCompaniesManager().getWriterManager();
		}
	}
	
	@Override
	public Category getParent(Category item)
	{
		return item.getParentCategory();
	}

	@Override
	public boolean hasChildren(Category item)
	{
		return !item.getChildCategories().isEmpty();
	}

	@Override
	public List<Category> getChildren(Category item)
	{
		return new ArrayList<Category>(item.getChildCategories());
	}

	@Override
	public void addChild(Category parent, Category newChild)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(context);
		
		if (parent != null)
		{
			parent.getChildCategories().add(newChild);

			newChild.setParentCategory(parent);
		}
		
		manager.persistCategory(newChild);
	}

	@Override
	public boolean removeChild(Category child)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(context);
		
		if (child.getParentCategory() != null)
			child.getParentCategory().getChildCategories().remove(child);
		
		manager.cascadeDeleteCategory(child.getId());
		
		return true;
	}

	@Override
	public boolean moveChild(Category child, Category newParent)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(context);
		
		return manager.moveCategory(child, newParent);
	}

}
