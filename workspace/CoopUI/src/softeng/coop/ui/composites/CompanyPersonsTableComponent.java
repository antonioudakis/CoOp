package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.business.companies.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.PersonDialog;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class CompanyPersonsTableComponent
extends TableComponent<Company, CompanyPerson>
{
	private static final long serialVersionUID = 1L;

	private static List<ColumnSpecification> defaultColumnSpecifications =
		getDefaultColumnSpecifications();

	public CompanyPersonsTableComponent(List<ColumnSpecification> columnSpecifications)
	{
		super(columnSpecifications);
	}
	
	public CompanyPersonsTableComponent()
	{
		super(defaultColumnSpecifications);
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications =
			new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		specifications.add(new ColumnSpecification("surname", "SURNAME_CAPTION"));
		specifications.add(new ColumnSpecification("position", "POSITION_CAPTION"));
		specifications.add(new ColumnSpecification("active", "ACTIVE_CAPTION"));

		return specifications;
	}

	@Override
	protected String getResourceBaseName()
	{
		return CompanyPersonsTableComponent.class.getCanonicalName();
	}

	@Override
	public Class<?> getType()
	{
		return CompanyPerson.class;
	}

	@Override
	protected IModalView<BeanItem<CompanyPerson>> showAddForm(BeanItem<CompanyPerson> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<CompanyPerson>> showEditForm(BeanItem<CompanyPerson> item)
	{
		return showForm(item);
	}

	private IModalView<BeanItem<CompanyPerson>> showForm(BeanItem<CompanyPerson> item)
	{
		PersonDialog<CompanyPerson> dialog = new PersonDialog<CompanyPerson>();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected CompanyPerson createNewElement()
	{
		CompanyPerson person = Constructor.createCompanyPerson();
		
		return person;
	}

	@Override
	protected Presenter<Collection<CompanyPerson>, ICoopContext, ? extends IView<Collection<CompanyPerson>, ICoopContext>> supplyPresenter()
	{
		return new CompanyPersonsTablePresenter(this);
	}

	@Override
	protected BeanItemContainer<CompanyPerson> createBeanItemContainer(Collection<CompanyPerson> collection)
	{
		CompaniesManager manager = getContext().getSession().getCompaniesManager();
		
		if (manager != null)
		{
			return new DataItemContainer<CompanyPerson>(
					CompanyPerson.class, collection, manager, getSpecifiedPropertyIds());
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<CompanyPerson> createItem(CompanyPerson obj)
	{
		if (obj == null) return null;
		
		CompaniesManager manager = getContext().getSession().getCompaniesManager(); 
		
		if (manager != null)
		{
			return new DataItem<CompanyPerson>(obj, manager);
		}
		
		return super.createItem(obj);
	}

	@Override
	protected void addToParent(CompanyPerson addedItem)
	{
		addedItem.setCompany(getParentModel());
	}

	@Override
	protected void removeFromParent(CompanyPerson item)
	{
		item.setCompany(null);
	}
}
