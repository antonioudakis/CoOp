package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.coops.*;
import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.ui.vaadin.mvp.*;

public class CoopsTablePresenter 
	extends Presenter<Collection<CoOp>, ICoopContext, ITableView<Lesson, CoOp>>
{
	private CoOpsWriterManager manager;
	
	public CoopsTablePresenter(ITableView<Lesson, CoOp> view)
	{
		super(view);
	}

	@Override
	protected void setupView()
	{
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

		getView().addAddingListener(new IListener<ModelEvent<CoOp>>()
		{
			@Override
			public void onEvent(ModelEvent<CoOp> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<CoOp>>()
		{
			
			@Override
			public void onEvent(ModelEvent<CoOp> event)
			{
				onDelete(event.getModel());
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<CoOp>>()
		{
			@Override
			public void onEvent(ElementExecutionVote<CoOp> vote)
			{
				canDelete(vote);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<CoOp> vote)
	{
		CoOp coop = vote.getElement();
		
		StringBuilder builder = new StringBuilder();
		
		if (!coop.getRegistrations().isEmpty())
		{
			if (builder.length() > 0) builder.append("<br />");
			builder.append(getLocalizedString("COOP_CONTAINS_REGISTRATIONS_CAPTION"));
			
			vote.markAsFailed();
		}
		
		if (!coop.getJobPostings().isEmpty())
		{
			if (builder.length() > 0) builder.append("<br />");
			builder.append(getLocalizedString("COOP_CONTAINS_JOB_POSTINGS_CAPTION"));
			
			vote.markAsFailed();
		}
		
		if (builder.length() > 0)
		{
			getContext().showEntityInUseNotification(builder.toString());
		}
		
	}

	protected void onDelete(CoOp model)
	{
		try
		{
			if (model.getId() > 0) 
			{
				for (AuthenticatedUser user : model.getAuthenticatedUsers())
				{
					user.setDefaultCoOp(null);
				}
				
				manager.deleteCoOp(model);
			}
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			throw ex;
		}
	}

	protected void onAdd(CoOp model)
	{
		model.setLesson(getView().getParentModel());
	}
	
}
