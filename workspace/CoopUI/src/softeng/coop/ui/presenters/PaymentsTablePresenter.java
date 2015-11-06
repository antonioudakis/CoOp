package softeng.coop.ui.presenters;

import java.util.*;

import com.vaadin.ui.Window.Notification;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.payments.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.ui.ICoopContext;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

public class PaymentsTablePresenter
extends Presenter<Collection<Payment>, ICoopContext, ITableView<Registration, Payment>>
{
	private PaymentsWriterManager manager;
	
	public PaymentsTablePresenter(ITableView<Registration, Payment> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getPaymentsManager() != null && 
				session.getPaymentsManager().isWriteable())
		{
			manager = session.getPaymentsManager().getWriterManager();
		}
		
		getView().addCanAddListener(new IListener<ElementExecutionVote<Payment>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Payment> event)
			{
				accessCheck(event);
			}
		});
		
		getView().addCanEditListener(new IListener<ElementExecutionVote<Payment>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Payment> event)
			{
				accessCheck(event);
			}
		});
		
		getView().addAddingListener(new IListener<ModelEvent<Payment>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Payment> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<Payment>>()
		{
			@Override
			public void onEvent(ModelEvent<Payment> event)
			{
				onEdit(event.getModel());
			}
		});
	}

	protected void onEdit(Payment model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	protected void onAdd(Payment model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	protected void accessCheck(ElementExecutionVote<Payment> event)
	{
		if (manager == null)
		{
			event.markAsFailed();
			return;
		}
		
		Registration registration = getView().getParentModel();
		
		if (registration == null)
		{
			event.markAsFailed();
			return;
		}
		
		if (registration.getGroup() == null)
		{
			getContext().showNotification(
					getLocalizedString("NO_GROUP_CAPTION"), 
					getLocalizedString("NO_GROUP_DESCRIPTION"), 
					Notification.TYPE_WARNING_MESSAGE);
			
			event.markAsFailed();
			
			return;
		}
		
		if (registration.getGroup().getJob() == null)
		{
			getContext().showNotification(
					getLocalizedString("NO_JOB_CAPTION"), 
					getLocalizedString("NO_JOB_DESCRIPTION"), 
					Notification.TYPE_WARNING_MESSAGE);
			
			event.markAsFailed();
			
			return;
		}
	}

	@Override
	protected void setupView()
	{
	}

}
