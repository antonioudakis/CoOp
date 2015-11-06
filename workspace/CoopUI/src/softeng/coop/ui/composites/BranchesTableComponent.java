package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class BranchesTableComponent 
	extends TableComponent<Company, Branch> 
{
	private static final long serialVersionUID = 1L;

	public BranchesTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	public BranchesTableComponent(String caption)
	{
		super(caption, getDefaultColumnSpecifications());
	}
	
	public BranchesTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}
	
	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		//specifications.add(new ColumnSpecification("address", "ADDRESS_CAPTION"));
		specifications.add(new ColumnSpecification("telephone", "TELEPHONE_CAPTION"));
		specifications.add(new ColumnSpecification("fax", "FAX_CAPTION"));
		//specifications.add(new ColumnSpecification("persons", "PERSONS_CAPTION"));
		
		//Now the cunning trick. Add nested property.
		specifications.add(new ColumnSpecification("address.location.name", "LOCATION_NAME_CAPTION"));
		 
		return specifications;
	}
	
	@Override
	public Class<?> getType() 
	{
		return Branch.class;
	}

	@Override
	protected IModalView<BeanItem<Branch>> showAddForm(BeanItem<Branch> item) 
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Branch>> showEditForm(BeanItem<Branch> item) 
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<Branch>> showForm(BeanItem<Branch> item)
	{
		BranchDialog dialog = new BranchDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Branch createNewElement() 
	{
		Branch branch = new Branch();
		
		branch.setName(new Multilingual());
		branch.getName().setLiterals(new HashSet<Literal>());
		
		branch.setAddress(new EmbeddableAddress());
		branch.getAddress().setType(AddressType.Work);
		
		branch.setJobParts(new HashSet<JobPart>());
		
		branch.setJobPostingParts(new HashSet<JobPostingPart>());
		
		branch.setPersons(new HashSet<CompanyPerson>());
		
		branch.setCompany(getParentModel());
		
		return branch;
	}

	@Override
	protected Presenter<Collection<Branch>, ICoopContext, ? extends IView<Collection<Branch>, ICoopContext>> supplyPresenter()
	{
		return new BranchesTablePresenter(this);
	}

	@Override
	protected void addToParent(Branch addedItem) 
	{
		addedItem.setCompany(getParentModel());
	}

	@Override
	protected BeanItemContainer<Branch> createBeanItemContainer(
			Collection<Branch> collection) 
	{
		Session session = getContext().getSession();
		
		ManagerBase manager;
		
		if (session.getCompaniesManager() != null)
		{
			manager = session.getCompaniesManager();
		}
		else
		{
			manager = session.getBaseManager();
		}
		
		//TODO add other properties
		DataItemContainer<Branch> container = 
			new DataItemContainer<Branch>(
					Branch.class, 
					collection, 
					manager);
		
		container.addNullableNestedContainerProperty("address.location.name");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
		
	}

	@Override
	protected BeanItem<Branch> createItem(Branch obj)
	{
		if (obj == null) return null;
		
		Session session = getContext().getSession();
		
		ManagerBase manager;
		
		if (session.getCompaniesManager() != null)
		{
			manager = session.getCompaniesManager();
		}
		else
		{
			manager = session.getBaseManager();
		}
		
		DataItem<Branch> item = new DataItem<Branch>(obj, manager);
		
		item.addNullableNestedProperty("address.location.name");
		
		return item;
	}

	@Override
	protected void removeFromParent(Branch item)
	{
		item.setCompany(null);
	}

}
