package softeng.coop.ui.composites;

import java.math.BigDecimal;
import java.util.*;

import com.vaadin.data.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.presenters.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.dialogs.*;

import softeng.coop.ui.data.*;

public class PaymentsTableComponent
extends TableComponent<Registration, Payment>
{
	private static final long serialVersionUID = 1L;
	
	private static List<ColumnSpecification> defaultColumnSpecifications = 
		createDefaultColumnSpecifications();

	public PaymentsTableComponent()
	{
		super(defaultColumnSpecifications);
	}

	private static List<ColumnSpecification> createDefaultColumnSpecifications()
	{
		ArrayList<ColumnSpecification> list = new ArrayList<ColumnSpecification>();
		
		list.add(new ColumnSpecification("amount", "AMOUNT_CAPTION"));
		list.add(new ColumnSpecification("type", "TYPE_CAPTION"));
		list.add(new ColumnSpecification("source.name", "SOURCE_CAPTION"));
		list.add(new ColumnSpecification("state", "STATE_CAPTION"));
		list.add(new ColumnSpecification("paymentDate", "PAYMENT_DATE_CAPTION"));
		
		return list;
	}

	@Override
	public Class<?> getType()
	{
		return Payment.class;
	}

	@Override
	protected IModalView<BeanItem<Payment>> showAddForm(BeanItem<Payment> item)
	{
		return showForm(item);
	}

	@Override
	protected IModalView<BeanItem<Payment>> showEditForm(BeanItem<Payment> item)
	{
		return showForm(item);
	}
	
	private IModalView<BeanItem<Payment>> showForm(BeanItem<Payment> item)
	{
		if (getParentModel().getGroup() == null) return null;
		if (getParentModel().getGroup().getJob() == null) return null;
		
		PaymentDialog dialog = new PaymentDialog(getParentModel());
		
		dialog.setModel(item);
		dialog.setModal(true);
		
		getApplication().getMainWindow().addWindow(dialog);
		
		dialog.dataBind();
		
		return dialog;
	}

	@Override
	protected Payment createNewElement()
	{
		Payment payment = Constructor.createPayment();
		
		payment.setAmount(new BigDecimal(0));
		payment.setPaymentDate(new Date());
		
		return payment;
	}

	@Override
	protected Presenter<Collection<Payment>, ICoopContext, ? extends IView<Collection<Payment>, ICoopContext>> supplyPresenter()
	{
		return new PaymentsTablePresenter(this);
	}

	@Override
	protected void addToParent(Payment item)
	{
		item.setRegistration(getParentModel());
	}

	@Override
	protected void removeFromParent(Payment item)
	{
		item.setRegistration(null);
	}

	@Override
	protected BeanItemContainer<Payment> createBeanItemContainer(Collection<Payment> collection)
	{
		DataItemContainer<Payment> container = 
			new DataItemContainer<Payment>(
					Payment.class, 
					collection, 
					getContext().getSession().getBaseManager());
		
		container.addNullableNestedContainerProperty("source.name");
		
		container.setContainerPropertyIds(getSpecifiedPropertyIds());

		return container;
	}

	@Override
	protected BeanItem<Payment> createItem(Payment obj)
	{
		DataItem<Payment> item = 
			new DataItem<Payment>(obj, getContext().getSession().getBaseManager());
		
		item.addNullableNestedProperty("source.name");
		
		return item;
	}

}
