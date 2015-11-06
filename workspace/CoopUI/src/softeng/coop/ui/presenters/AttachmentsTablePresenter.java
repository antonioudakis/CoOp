package softeng.coop.ui.presenters;

import java.util.*;

import softeng.ui.vaadin.mvp.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.coop.business.*;

import softeng.coop.business.reporting.*;

public class AttachmentsTablePresenter<P>
extends Presenter<Collection<Attachment>, ICoopContext, ITableView<P, Attachment>>
{
	private ReportsWriterManager manager;

	public AttachmentsTablePresenter(ITableView<P, Attachment> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		Session session = getContext().getSession();
		
		if (session.getReportsManager() != null && session.getReportsManager().isWriteable())
		{
			manager = session.getReportsManager().getWriterManager();
		}
		
		getView().addCanAddListener(new IListener<ElementExecutionVote<Attachment>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Attachment> event)
			{
				accessCheck(event);
			}
		});
		
		getView().addCanEditListener(new IListener<ElementExecutionVote<Attachment>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Attachment> event)
			{
				accessCheck(event);
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<Attachment>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<Attachment> event)
			{
				accessCheck(event);
			}
		});
		
		getView().addAddingListener(new IListener<ModelEvent<Attachment>>()
		{
			@Override
			public void onEvent(ModelEvent<Attachment> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<Attachment>>()
		{
			@Override
			public void onEvent(ModelEvent<Attachment> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Attachment>>()
		{
			@Override
			public void onEvent(ModelEvent<Attachment> event)
			{
				onDelete();
			}
		});
		
	}

	protected void onAdd(Attachment model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	protected void onEdit(Attachment model)
	{
		if (manager != null) manager.markAsChanged(model);
	}

	protected void onDelete()
	{
	}

	protected void accessCheck(ElementExecutionVote<Attachment> event)
	{
		if (manager == null) 
		{
			event.markAsFailed();
			
			getContext().showAccessDeniedNotification();
		}
	}

	@Override
	protected void setupView()
	{
	}

}
