package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.presenters.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.ui.vaadin.mvp.*;

public class BranchPersonsTableComponent 
	extends TableComponent<Branch, CompanyPerson> 
{
	private static final long serialVersionUID = 1L;

	public BranchPersonsTableComponent()
	{
		super(getDefaultColumnSpecifications());
	}
	
	public BranchPersonsTableComponent(String caption)
	{
		super(getDefaultColumnSpecifications());
		
		this.setCaption(caption);
	}
	
	public BranchPersonsTableComponent(
			List<ColumnSpecification> columnSpecifications) 
	{
		super(columnSpecifications);
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("name", "NAME_CAPTION"));
		specifications.add(new ColumnSpecification("surname", "SURNAME_CAPTION"));
		specifications.add(new ColumnSpecification("email", "EMAIL_CAPTION"));
		 
		return specifications;
	}
	
	@Override
	public Class<?> getType() 
	{
		return CompanyPerson.class;
	}

	@Override
	protected IModalView<BeanItem<CompanyPerson>> showAddForm(
			BeanItem<CompanyPerson> item) 
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<CompanyPerson>> showEditForm(
			BeanItem<CompanyPerson> item) 
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<CompanyPerson>> showForm(BeanItem<CompanyPerson> item)
	{
		ChooseCompanyPersonDialog dialog = 
			new ChooseCompanyPersonDialog(getParentModel().getCompany());
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected CompanyPerson createNewElement() 
	{
		return new CompanyPerson();
	}

	@Override
	protected Presenter<Collection<CompanyPerson>, ICoopContext, ? extends IView<Collection<CompanyPerson>, ICoopContext>> supplyPresenter() 
	{
		return new BranchPersonsTablePresenter(this);
	}

	@Override
	protected void addToParent(CompanyPerson addedItem) 
	{
		addedItem.getBranches().add(getParentModel());
	}

	@Override
	protected void removeFromParent(CompanyPerson addedItem) 
	{
		addedItem.getBranches().remove(getParentModel());
	}

	@Override
	protected BeanItemContainer<CompanyPerson> createBeanItemContainer(
			Collection<CompanyPerson> collection) 
	{
		//TODO add other properties
		DataItemContainer<CompanyPerson> container = 
			new DataItemContainer<CompanyPerson>(CompanyPerson.class, collection, this.getContext().getSession().getBaseManager());
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
		
	}
	
}
