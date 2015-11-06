package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;
import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.ITableView;

import softeng.ui.vaadin.mvp.*;

public class RequirementsTablePresenter
extends Presenter<Collection<Requirement>, ICoopContext, ITableView<CoOp, Requirement>> 
{
	private CoOpsWriterManager manager;

	public RequirementsTablePresenter(ITableView<CoOp, Requirement> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		CoOpsManager manager = getContext().getSession().getCoOpsManager();
		
		if (manager == null || !manager.isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		this.manager = manager.getWriterManager();
		
		getView().addAddingListener(new IListener<ModelEvent<Requirement>>()
		{
			@Override
			public void onEvent(ModelEvent<Requirement> event)
			{
				add(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<Requirement>>()
		{
			@Override
			public void onEvent(ModelEvent<Requirement> event)
			{
				edit(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Requirement>>()
		{
			@Override
			public void onEvent(ModelEvent<Requirement> event)
			{
				delete(event.getModel());
			}
		});
	}

	protected void edit(Requirement model)
	{
		manager.markAsChanged(model);
	}

	protected void delete(Requirement model)
	{
	}

	protected void add(Requirement model)
	{
		manager.markAsChanged(model);
	}

	@Override
	protected void setupView()
	{
		
	}
}
