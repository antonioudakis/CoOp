package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.BeanItem;

import softeng.coop.dataaccess.*;
import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.dialogs.AddressDialog;
import softeng.coop.ui.viewdefinitions.IModalView;
import softeng.ui.vaadin.mvp.*;

public class AddressesTableComponent
	extends TableComponent<Person, Address>
{
	private static final long serialVersionUID = 1L;

	public AddressesTableComponent(List<TableComponent.ColumnSpecification> columnSpecifications)
	{
		super(columnSpecifications);
	}
	
	public AddressesTableComponent()
	{
		super(getDefaltColumnSpecifications());
	}
	
	public AddressesTableComponent(String caption, List<TableComponent.ColumnSpecification> columnSpecifications)
	{
		super(caption, columnSpecifications);
	}
	
	public AddressesTableComponent(String caption)
	{
		super(caption, getDefaltColumnSpecifications());
	}
	
	private static List<TableComponent.ColumnSpecification> getDefaltColumnSpecifications()
	{
		List<TableComponent.ColumnSpecification> specifications =
			new ArrayList<TableComponent.ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("type", "ADDRESS_TYPE_CAPTION"));
		
		specifications.add(new ColumnSpecification("city", "ADDRESS_CITY_CAPTION"));
		
		specifications.add(new ColumnSpecification("street", "ADDRESS_STREET_CAPTION"));
		
		specifications.add(new ColumnSpecification("number", "ADDRESS_NUMBER_CAPTION"));
		
		specifications.add(new ColumnSpecification("poBox", "POSTAL_CODE_CAPTION"));
		
		return specifications;
	}

	@Override
	protected IModalView<BeanItem<Address>> showAddForm(BeanItem<Address> item)
	{
		AddressDialog dialog = new AddressDialog();
		
		dialog.setModel(item);
		
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected IModalView<BeanItem<Address>> showEditForm(BeanItem<Address> item)
	{
		AddressDialog dialog = new AddressDialog();
		
		dialog.setModel(item);
		
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	public Class<?> getType()
	{
		return Address.class;
	}

	@Override
	protected Address createNewElement()
	{
		Address address = new Address();
		
		address.setType(AddressType.Home);
		
		return address;
	}

	@Override
	protected Presenter<Collection<Address>, ICoopContext, ? extends IView<Collection<Address>, ICoopContext>> supplyPresenter()
	{
		//return new AddressesTablePresenter(this);
		return null;
	}

	@Override
	protected void addToParent(Address item)
	{
		item.setPerson(getParentModel());
	}

	@Override
	protected void removeFromParent(Address item)
	{
		item.setPerson(null);
	}

}
