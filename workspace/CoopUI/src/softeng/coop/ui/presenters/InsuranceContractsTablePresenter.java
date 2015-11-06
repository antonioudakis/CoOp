package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.*;

import softeng.coop.dataaccess.*;

import softeng.coop.business.*;
import softeng.coop.business.coops.*;

import softeng.ui.vaadin.mvp.*;

public class InsuranceContractsTablePresenter
extends Presenter<Collection<InsuranceContract>, ICoopContext, ITableView<CoOp, InsuranceContract>>
{
	private CoOpsWriterManager manager;

	public InsuranceContractsTablePresenter(ITableView<CoOp, InsuranceContract> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getCoOpsManager() != null && session.getCoOpsManager().isWriteable())
			manager = session.getCoOpsManager().getWriterManager();
		
		getView().addAddingListener(new IListener<ModelEvent<InsuranceContract>>()
		{
			@Override
			public void onEvent(ModelEvent<InsuranceContract> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<InsuranceContract>>()
		{
			@Override
			public void onEvent(ModelEvent<InsuranceContract> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addPreAddListener(new IListener<CommandExecutionVote>()
		{
			@Override
			public void onEvent(CommandExecutionVote event)
			{
				accessCheck(event);
			}
		});
		
		getView().addCanEditListener(new IListener<ElementExecutionVote<InsuranceContract>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<InsuranceContract> event)
			{
				accessCheck(event);
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<InsuranceContract>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<InsuranceContract> event)
			{
				accessCheck(event);
			}
		});
	}

	protected void onEdit(InsuranceContract model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	protected void onAdd(InsuranceContract model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	@Override
	protected void setupView()
	{
	}

	private void accessCheck(CommandExecutionVote event)
	{
		if (manager == null) 
		{
			event.markAsFailed();
			
			getContext().showAccessDeniedNotification();
		}
	}

}
