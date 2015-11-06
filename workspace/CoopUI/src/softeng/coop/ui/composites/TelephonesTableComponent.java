package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.dialogs.*;

import softeng.ui.vaadin.mvp.*;

@SuppressWarnings("serial")
public class TelephonesTableComponent
extends TableComponent<Person, Telephone>
{
	private static List<ColumnSpecification> columnSpecifications = 
		createColumnSpecifications();

	public TelephonesTableComponent()
	{
		super(columnSpecifications);
	}

	private static List<TableComponent.ColumnSpecification> createColumnSpecifications()
	{
		ArrayList<ColumnSpecification> list = new ArrayList<ColumnSpecification>();
		
		list.add(new ColumnSpecification("type", "TYPE_CAPTION"));
		list.add(new ColumnSpecification("number", "NUMBER_CAPTION"));
		list.add(new ColumnSpecification("comment", "COMMENT_CAPTION"));
		
		return list;
	}

	@Override
	public Class<?> getType()
	{
		return Telephone.class;
	}

	@Override
	protected IModalView<BeanItem<Telephone>> showAddForm(BeanItem<Telephone> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Telephone>> showEditForm(BeanItem<Telephone> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<Telephone>> showForm(BeanItem<Telephone> item)
	{
		TelephoneDialog dialog = new TelephoneDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Telephone createNewElement()
	{
		return Constructor.createTelephone();
	}

	@Override
	protected Presenter<Collection<Telephone>, ICoopContext, ? extends IView<Collection<Telephone>, ICoopContext>> supplyPresenter()
	{
		return null;
	}

	@Override
	protected void addToParent(Telephone item)
	{
		item.setPerson(getParentModel());
	}

	@Override
	protected void removeFromParent(Telephone item)
	{
		item.setPerson(null);
	}

}
