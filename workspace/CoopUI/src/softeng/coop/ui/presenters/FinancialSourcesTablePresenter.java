package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.payments.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.ui.vaadin.mvp.*;

public class FinancialSourcesTablePresenter
extends Presenter<Collection<FinancialSource>, ICoopContext, ITableView<CoOp, FinancialSource>>
{
	private PaymentsWriterManager manager;
	
	public FinancialSourcesTablePresenter(ITableView<CoOp, FinancialSource> view)
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
		
		getView().addCanAddListener(new IListener<ElementExecutionVote<FinancialSource>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<FinancialSource> event)
			{
				if (manager == null) event.markAsFailed();
			}
		});
		
		getView().addCanEditListener(new IListener<ElementExecutionVote<FinancialSource>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<FinancialSource> event)
			{
				if (manager == null) event.markAsFailed();
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<FinancialSource>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<FinancialSource> event)
			{
				canDelete(event);
			}
		});
		
		getView().addAddingListener(new IListener<ModelEvent<FinancialSource>>()
		{
			@Override
			public void onEvent(ModelEvent<FinancialSource> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<FinancialSource>>()
		{
			
			@Override
			public void onEvent(ModelEvent<FinancialSource> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<FinancialSource>>()
		{
			
			@Override
			public void onEvent(ModelEvent<FinancialSource> event)
			{
				onDelete(event.getModel());
			}
		});
		
	}

	protected void onAdd(FinancialSource model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	protected void onEdit(FinancialSource model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	protected void onDelete(FinancialSource model)
	{
		if (manager != null)
		{
			try
			{
				if (model.getId() > 0) manager.deleteFinancialSource(model);
			}
			catch (RuntimeException ex)
			{
				getContext().getSession().revertOutstanding();
				throw ex;
			}
		}
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (getView().getModel() == null && session.getPaymentsManager() != null)
		{
			if (getView().getParentModel() == null)
			{
				getView().setModel(session.getPaymentsManager().getFinancialSources());
			}
			else
			{
				getView().setModel(getView().getParentModel().getFinancialSources());
				getView().setReadOnly(true);
			}
			
			getView().dataBind();
		}
		
	}

	private void canDelete(ElementExecutionVote<FinancialSource> event)
	{
		if (manager == null) 
		{
			event.markAsFailed();
			
			return;
		}
		
		FinancialSource source = event.getElement();
		
		LocalizedMessageAppender appender = new LocalizedMessageAppender();
		
		if (!source.getCoOps().isEmpty())
		{
			appender.add("FINANCIAL_SOURCE_USED_IN_COOP_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!source.getPayments().isEmpty())
		{
			appender.add("PAYMENTS_BY_SOURCE_EXIST_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!appender.isEmpty())
			getContext().showEntityInUseNotification(appender.toString());
	}

}
