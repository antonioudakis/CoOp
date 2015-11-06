package softeng.coop.ui.composites;

import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.payments.*;

import softeng.coop.ui.*;
import softeng.coop.ui.data.*;
import softeng.coop.ui.dialogs.*;
import softeng.coop.ui.presenters.FinancialSourcesTablePresenter;
import softeng.coop.ui.viewdefinitions.*;

import softeng.ui.vaadin.mvp.*;

public class FinancialSourcesTableComponent
extends TableComponent<CoOp, FinancialSource>
{
	private static final long serialVersionUID = 1L;
	
	private PaymentsManager manager;
	
	private boolean dialogControlled = false;

	public FinancialSourcesTableComponent()
	{
		super(createColumnSpecifications());
		
		manager = getContext().getSession().getPaymentsManager();
	}

	private static List<ColumnSpecification> createColumnSpecifications()
	{
		List<ColumnSpecification> list = new ArrayList<ColumnSpecification>();
		
		list.add(new ColumnSpecification("name", "NAME_CAPTION"));
		list.add(new ColumnSpecification("code", "CODE_CAPTION"));
		
		return list;
	}

	@Override
	public Class<?> getType()
	{
		return FinancialSource.class;
	}

	@Override
	protected IModalView<BeanItem<FinancialSource>> showAddForm(BeanItem<FinancialSource> item)
	{
		if (dialogControlled)
			return showForm(item);
		else
			return null;
	}

	@Override
	protected IModalView<BeanItem<FinancialSource>> showEditForm(BeanItem<FinancialSource> item)
	{
		if (dialogControlled)
			return showForm(item);
		else 
			return null;
	}
	
	private IModalView<BeanItem<FinancialSource>> showForm(BeanItem<FinancialSource> item)
	{
		FinancialSourceDialog dialog = new FinancialSourceDialog();
		
		dialog.setModal(true);
		dialog.setModel(item);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected FinancialSource createNewElement()
	{
		FinancialSource financialSource = new FinancialSource();
		
		financialSource.setPayments(new LinkedHashSet<Payment>());
		financialSource.setCoOps(new HashSet<CoOp>());
		
		financialSource.setName("-");
		
		return financialSource;
	}

	@Override
	protected Presenter<Collection<FinancialSource>, ICoopContext, ? extends IView<Collection<FinancialSource>, ICoopContext>> supplyPresenter()
	{
		return new FinancialSourcesTablePresenter(this);
	}

	@Override
	protected BeanItemContainer<FinancialSource> createBeanItemContainer(Collection<FinancialSource> collection)
	{
		if (manager != null)
		{
			return new DataItemContainer<FinancialSource>(
					FinancialSource.class, 
					collection, 
					manager, 
					getSpecifiedPropertyIds());
		}
		
		return super.createBeanItemContainer(collection);
	}

	@Override
	protected BeanItem<FinancialSource> createItem(FinancialSource obj)
	{
		if (manager != null)
		{
			return new DataItem<FinancialSource>(obj, manager);
		}
		
		return super.createItem(obj);
	}

	public boolean isDialogControlled()
	{
		return dialogControlled;
	}

	public void setDialogControlled(boolean dialogControlled)
	{
		this.dialogControlled = dialogControlled;
	}

}
