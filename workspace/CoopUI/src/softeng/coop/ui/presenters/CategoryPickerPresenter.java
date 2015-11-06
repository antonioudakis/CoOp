package softeng.coop.ui.presenters;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.ui.vaadin.data.*;
import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.IHierarchicalModalView.IHierarchyProvider;

public class CategoryPickerPresenter
	extends Presenter<Category, ICoopContext, IHierarchicalModalView<Category>>
	implements IHierarchyProvider<Category>
{
	private CompaniesManager manager;

	public CategoryPickerPresenter(IHierarchicalModalView<Category> view)
	{
		super(view);
	}

	@Override
	public HierarchicalBeanItemContainer<Category> getHierarchy()
	{
		if (manager != null)
		{
			return new HierarchicalBeanItemContainer<Category>(
					new DataItemContainer<Category>(Category.class, manager.getRootCategories(), manager), 
					new CategoryHierarchyModel(getContext()));
		}
		else
		{
			return null;
		}
	}

	@Override
	public Category getDefault()
	{
		return null;
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		manager = session.getCompaniesManager();
		
		if (manager == null)
		{
			getContext().showAccessDeniedNotification();
			return;
		}
	}

	@Override
	protected void setupView()
	{
		getView().setHierarchyProvider(this);
	}

}
