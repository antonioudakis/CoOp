package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.coop.business.*;
import softeng.coop.business.companies.*;

import softeng.ui.vaadin.mvp.*;

public class BranchesTablePresenter
extends Presenter<Collection<Branch>, ICoopContext, ITableView<Company, Branch>>
{
	private CompaniesWriterManager manager;
	
	public BranchesTablePresenter(ITableView<Company, Branch> view)
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		getView().addAddingListener(new IListener<ModelEvent<Branch>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Branch> event)
			{
				onAdd(event.getModel());
			}
		});
		
		getView().addEditingListener(new IListener<ModelEvent<Branch>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Branch> event)
			{
				onEdit(event.getModel());
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<Branch>>()
		{
			
			@Override
			public void onEvent(ModelEvent<Branch> event)
			{
				onDelete(event.getModel());
			}
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<Branch>>()
		{
			
			@Override
			public void onEvent(ElementExecutionVote<Branch> event)
			{
				canDelete(event);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<Branch> event)
	{
		Branch branch = event.getElement();
		
		LocalizedMessageAppender appender = new LocalizedMessageAppender();
		
		if (!branch.getJobPostingParts().isEmpty())
		{
			appender.add("BRANCH_IS_USED_IN_JOB_POSTING_PARTS_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!branch.getJobParts().isEmpty())
		{
			appender.add("BRANCH_IS_USED_IN_JOB_PARTS_CAPTION");
			
			event.markAsFailed();
		}
		
		if (!appender.isEmpty())
		{
			getContext().showEntityInUseNotification(appender.toString());
		}
	}

	protected void onDelete(Branch model)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());
	}

	protected void onEdit(Branch model)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());

		manager.markAsChanged(model);
	}

	protected void onAdd(Branch model)
	{
		if (manager == null)
			throw new CoopUIAccessDeniedException(getContext());

		manager.markAsChanged(model);
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (session.getCompaniesManager() != null && 
				session.getCompaniesManager().isWriteable())
		{
			manager = session.getCompaniesManager().getWriterManager();
		}
	}

}
