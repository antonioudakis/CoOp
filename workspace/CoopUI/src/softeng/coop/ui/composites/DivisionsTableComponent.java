package softeng.coop.ui.composites;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public class DivisionsTableComponent
extends TableComponent<Department, Division>
{
	public DivisionsTableComponent()
	{
		super(getDefaultColumnSpecifications());
		
		setAddVisible(false);
		setEditVisible(false);
		setDeleteVisible(false);
	}

	private static List<ColumnSpecification> getDefaultColumnSpecifications()
	{
		ArrayList<ColumnSpecification> list = new ArrayList<ColumnSpecification>();
		
		list.add(new ColumnSpecification("name", "NAME_CAPTION"));
		
		return list;
	}

	@Override
	public Class<?> getType()
	{
		return Division.class;
	}

	@Override
	protected IModalView<BeanItem<Division>> showAddForm(BeanItem<Division> item)
	{
		return null;
	}

	@Override
	protected IModalView<BeanItem<Division>> showEditForm(BeanItem<Division> item)
	{
		return null;
	}

	@Override
	protected Division createNewElement()
	{
		return null;
	}

	@Override
	protected Presenter<Collection<Division>, ICoopContext, ? extends IView<Collection<Division>, ICoopContext>> supplyPresenter()
	{
		return null;
	}
}
