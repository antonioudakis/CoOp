package softeng.coop.ui.presenters;

import java.util.*;

import softeng.coop.business.*;
import softeng.coop.business.jobpostings.*;

import softeng.coop.dataaccess.*;

import softeng.coop.ui.*;
import softeng.coop.ui.viewdefinitions.*;
import softeng.coop.ui.viewdefinitions.viewmodels.ElementExecutionVote;

import softeng.ui.vaadin.mvp.*;

public class JobPostingsTablePresenter 
	extends Presenter<Collection<JobPosting>, ICoopContext, ITableView<Company,JobPosting>>
{
	private JobPostingsWriterManager manager;
	
	public JobPostingsTablePresenter(ITableView<Company, JobPosting> view) 
	{
		super(view);
	}

	@Override
	protected void attachToView()
	{
		getView().addAddingListener(new IListener<ModelEvent<JobPosting>>() 
		{

			@Override
			public void onEvent(ModelEvent<JobPosting> event) 
			{
				onAdd(event.getModel());
				
			}
		});
		
		getView().addDeletingListener(new IListener<ModelEvent<JobPosting>>() 
		{

			@Override
			public void onEvent(ModelEvent<JobPosting> event) 
			{
				onDelete(event.getModel());
				
			}
			
		});

		getView().addEditingListener(new IListener<ModelEvent<JobPosting>>() 
		{

			@Override
			public void onEvent(ModelEvent<JobPosting> event) 
			{
				onEdit(event.getModel());
			}
			
		});
		
		getView().addCanDeleteListener(new IListener<ElementExecutionVote<JobPosting>>()
		{
			
			@Override
			public void onEvent(ElementExecutionVote<JobPosting> vote)
			{
				canDelete(vote);
			}
		});
	}

	protected void canDelete(ElementExecutionVote<JobPosting> vote)
	{
		JobPosting jobPosting = vote.getElement();
		
		if (!jobPosting.getJobs().isEmpty())
		{
			getContext().showEntityInUseNotification(
					getLocalizedString("JOB_POSTING_CONTAINS_JOBS_CAPTION"));
			
			vote.markAsFailed();
		}
		
	}

	@Override
	protected void setupView()
	{
		Session session = getContext().getSession();
		
		if (session.getJobPostingsManager() == null || 
				!session.getJobPostingsManager().isWriteable())
		{
			getContext().showAccessDeniedNotification();
			return;
		}
		
		manager = session.getJobPostingsManager().getWriterManager();
	}

	protected void onDelete(JobPosting jobPosting)
	{
		try
		{
			CoOp coop = getContext().getSelectedCoop();
			
			if (coop != null)
			{
				coop.getJobPostings().remove(jobPosting);
			}
			
			jobPosting.setCoOp(null);
			
			if (jobPosting.getId() > 0) manager.deleteJobPosting(jobPosting);
		}
		catch (RuntimeException ex)
		{
			getContext().getSession().revertOutstanding();
			throw ex;
		}
		
		getContext().showDataSavedNotification();
	}

	protected void onEdit(JobPosting jobPosting)
	{
		manager.markAsChanged(jobPosting);
	}

	protected void onAdd(JobPosting jobPosting)
	{
		manager.markAsChanged(jobPosting);
	}



}
