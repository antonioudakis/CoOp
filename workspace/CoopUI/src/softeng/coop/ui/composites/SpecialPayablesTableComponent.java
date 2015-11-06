package softeng.coop.ui.composites;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;

import softeng.ui.vaadin.mvp.*;

import com.vaadin.data.util.*;

@SuppressWarnings("serial")
public abstract class SpecialPayablesTableComponent<P, M>
extends TableComponent<P, M>
{
	private static List<ColumnSpecification> defaultColumnSpecifications = 
		createDefaultColumnSpecifications();
	
	private Class<M> payableType;
	
	private CoOp coop;

	public SpecialPayablesTableComponent(Class<M> payableType, CoOp coop)
	{
		super(defaultColumnSpecifications);
		
		if (payableType == null) 
			throw new IllegalArgumentException("Argument 'payableType' must not be null.");
		
		if (coop == null) 
			throw new IllegalArgumentException("Argument 'coop' must not be null.");
		
		this.payableType = payableType;
		this.coop = coop;
	}

	private static List<ColumnSpecification> createDefaultColumnSpecifications()
	{
		List<ColumnSpecification> specifications = new ArrayList<ColumnSpecification>();
		
		specifications.add(new ColumnSpecification("financialSource.name", "FINANCIAL_SOURCE_CAPTION"));
		specifications.add(new ColumnSpecification("paidDays", "PAID_DAYS_CAPTION"));
		
		return specifications;
	}

	@Override
	public Class<?> getType()
	{
		return payableType;
	}

	@Override
	protected IModalView<BeanItem<M>> showAddForm(BeanItem<M> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<M>> showEditForm(BeanItem<M> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<M>> showForm(BeanItem<M> item)
	{
		SpecialPayableDialog<M> dialog = new SpecialPayableDialog<M>(coop, payableType);
		
		dialog.setModel(item);
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Presenter<Collection<M>, ICoopContext, ? extends IView<Collection<M>, ICoopContext>> supplyPresenter()
	{
		return null;
	}

	@Override
	protected BeanItemContainer<M> createBeanItemContainer(Collection<M> collection)
	{
		DataItemContainer<M> container = 
			new DataItemContainer<M>(
					payableType,
					collection,
					getContext().getSession().getBaseManager());
		
		container.addNullableNestedContainerProperty("financialSource.name");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());
		
		return container;
	}

	@Override
	protected BeanItem<M> createItem(M obj)
	{
		DataItem<M> item = 
			new DataItem<M>(
					obj, 
					getContext().getSession().getBaseManager());
		
		item.addNullableNestedProperty("financialSource.name");
		
		return item;
	}

	@Override
	protected String getResourceBaseName()
	{
		return SpecialPayablesTableComponent.class.getCanonicalName();
	}

}
