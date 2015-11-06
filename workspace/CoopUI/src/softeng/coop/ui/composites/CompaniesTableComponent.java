package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.companies.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class CompaniesTableComponent
extends TableComponent<Category, Company>
{
	private static final long serialVersionUID = 1L;

	public CompaniesTableComponent(String caption, List<ColumnSpecification> columnSpecifications)
	{
		super(caption, columnSpecifications);
	}

	public CompaniesTableComponent(String caption)
	{
		this(caption, getDefaultColumnSpecifications());
	}

	public CompaniesTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications =
			new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		
		specifications.add(new ColumnSpecification("taxCode", "TAX_CODE_CAPTION"));

		return specifications;
	}

	@Override
	public Class<?> getType()
	{
		return Company.class;
	}

	@Override
	protected IModalView<BeanItem<Company>> showAddForm(BeanItem<Company> item)
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<Company>> showEditForm(BeanItem<Company> item)
	{
		return null;
	}

	@Override
	protected Company createNewElement()
	{
		Company company = Constructor.createCompany();
		
		return company;
	}

	@Override
	protected Presenter<Collection<Company>, ICoopContext, ? extends IView<Collection<Company>, ICoopContext>> supplyPresenter()
	{
		return new CompaniesTablePresenter(this);
	}

	@Override
	protected BeanItemContainer<Company> createBeanItemContainer(Collection<Company> collection)
	{
		CompaniesManager manager = getContext().getSession().getCompaniesManager();
		
		if (manager != null)
		{
			return new DataItemContainer<Company>(
					Company.class, collection, manager, getSpecifiedPropertyIds());
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<Company> createItem(Company obj)
	{
		if (obj == null) return null;
		
		CompaniesManager manager = getContext().getSession().getCompaniesManager();
		
		if (manager != null)
		{
			return new DataItem<Company>(obj, manager);
		}
		
		return super.createItem(obj);
	}

	@Override
	protected void addToParent(Company addedItem)
	{
		addedItem.setCategory(getParentModel());
	}

	@Override
	protected void removeFromParent(Company item)
	{
		item.setCategory(null);
	}

}
